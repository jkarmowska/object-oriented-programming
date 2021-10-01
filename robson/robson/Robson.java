package robson;

import com.google.googlejavaformat.java.FormatterException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import instrukcje.*;
import instrukcje.arytmetyczneDwuarg.*;
import instrukcje.logiczneDwuarg.*;

import javax.lang.model.SourceVersion;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Robson {
    private final NavigableMap<String, Zmienna> zmienne;
    private Instrukcja instrukcja;

    public Robson() {
        zmienne = new TreeMap<>();
    }

    public static void main(String[] args) {
        Robson robson = new Robson();

        robson.sprawdźDane(args);

        try {
            robson.fromJSON(args[0]); //wczytanie programu z pliku (zapamiętanie go w atrybucie instrukcja, dodanie zmiennych do kolekcji)
        } catch (NieprawidlowyProgram nieprawidlowyProgram) {
            nieprawidlowyProgram.printStackTrace();
            System.exit(1);
        }
        robson.toJava(args[1]);
        robson.toJSON(args[2]);
        try {
            System.out.println(robson.wykonaj()); //wypisywanie wyniku programu
        } catch (BladWykonania b) {
            System.out.println("Nie udało się wykonać programu. " + b.getMessage());
        }
    }

    /*
    Sprawdza, czy podano trzy argumenty programu
     */
    public void sprawdźDane(String[] args) {
        assert (args.length == 3) : "Niepoprawna liczba parametrów (" + args.length + ")." +
                " Podaj trzy parametry: plik z kodem w formacie JSON, " +
                "nazwę pliku do zapisania kodu Javy, nazwę pliku do zapisania porogramu w formacie JSON.";
    }

    /*
       Wczytuje instrukvje z pliku JSON
     */
    public void fromJSON(String filename) throws NieprawidlowyProgram {
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(filename));
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject(); //tworzenie obiektu JSON z wczytanych danych
            this.instrukcja = nowaInstrukcja(jsonObject);
            reader.close();

        } catch (IOException e) {
            throw new NieprawidlowyProgram("Podano niepoprawny plik z programem w formacie JSON.");
        }
    }

    public Blok nowyBlok(JsonObject obj) {
        Blok blok = new Blok();
        if (obj == null) //blok moze byc pusty - np. w instrukcji If, jesli nie ma atrybutu blok_falsz
            return blok;
        JsonArray instrukcje = obj.get("instrukcje").getAsJsonArray();
        for (JsonElement i : instrukcje) {
            blok.dodajInstrukcje(nowaInstrukcja(i.getAsJsonObject()));
        }
        return blok;
    }

    /*
    Wczytuje nową instrukcję - rozpoznaje typ i wywołuje odpowiednią funkcję
     */
    public Instrukcja nowaInstrukcja(JsonObject jsonObject) {
        String typ = jsonObject.get("typ").getAsString();
        switch (typ) {
            case "Blok":
                return nowyBlok(jsonObject);
            case "If":
                return nowyIf(jsonObject);
            case "While":
                return nowyWhile(jsonObject);
            case "Przypisanie":
                return nowePrzypisanie(jsonObject);
            case "Plus":
                return nowyPlus(jsonObject);
            case "Minus":
                return nowyMinus(jsonObject);
            case "Razy":
                return noweRazy(jsonObject);
            case "Dzielenie":
                return noweDzielenie(jsonObject);
            case "And":
                return noweAnd(jsonObject);
            case "Or":
                return nowyOr(jsonObject);
            case "Not":
                return nowyNot(jsonObject);
            case "<":
                return nowePorownanie(jsonObject, "<");
            case "<=":
                return nowePorownanie(jsonObject, "<=");
            case ">":
                return nowePorownanie(jsonObject, ">");
            case ">=":
                return nowePorownanie(jsonObject, ">=");
            case "==":
                return nowePorownanie(jsonObject, "==");
            case "True":
                return new True();
            case "False":
                return new False();
            case "Zmienna":
                return nowaZmienna(jsonObject);
            case "Liczba":
                return nowaLiczba(jsonObject);
        }
        return null;
    }

    /*
        Wczytuje nowe wyrażenie logiczne - rozpoznaje typ i wywołuje odpowiednią funkcję
         */
    private WyrLogiczne noweWyrLogiczne(JsonObject jsonObject) {
        String typ = jsonObject.get("typ").getAsString();
        switch (typ) {
            case "And":
                return noweAnd(jsonObject);
            case "Or":
                return nowyOr(jsonObject);
            case "<":
                return nowePorownanie(jsonObject, "<");
            case "<=":
                return nowePorownanie(jsonObject, "<=");
            case ">":
                return nowePorownanie(jsonObject, ">");
            case ">=":
                return nowePorownanie(jsonObject, ">=");
            case "==":
                return nowePorownanie(jsonObject, "==");
            case "True":
                return new True();
            case "False":
                return new False();
            case "Not":
                return nowyNot(jsonObject);
        }
        return null;
    }



    /*
    Funkcje wczytujące określone typy instrukcji
        tworzą obiekt z obiektu typu JsonObject,
        jeśli atrybut obiektu JSON jest jakąś instrukcją, to tworzą z tego argumentu nowy obiekt typu JsonObject
        i wywołują funkcję nowaInstrukcja (lub noweWyrLogiczne, jeśli tylko takie są dozwolone - np. w warunku pętli While),
        atrybut tworzonego obiektu staje się wynikiem tej funkcji
     */

    private Przypisanie nowePrzypisanie(JsonObject jsonObject) {
        String nazwa = jsonObject.get("nazwa").getAsString();
        JsonObject wart = jsonObject.getAsJsonObject("wartosc");
        Instrukcja wartosc = nowaInstrukcja(wart);
        Zmienna z;
        if (!zmienne.containsKey(nazwa)) { //wszystkie zmienne użyte w programie zapisujemy w kolekcji
            z = new Zmienna(nazwa);
            zmienne.put(nazwa, z);
        } else {
            z = zmienne.get(nazwa);
        }
        return new Przypisanie(z, wartosc);
    }

    private Porownanie nowePorownanie(JsonObject jsonObject, String typ) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        Instrukcja argument1 = nowaInstrukcja(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        Instrukcja argument2 = nowaInstrukcja(arg2);
        return new Porownanie(argument1, argument2, typ);
    }

    /*
    Zakładam, że blok_prawda i blok_flasz są typu Blok
     */
    private If nowyIf(JsonObject jsonObject) {
        JsonObject war = jsonObject.getAsJsonObject("warunek");
        JsonObject blok_p = jsonObject.getAsJsonObject("blok_prawda");
        JsonObject blok_f = jsonObject.getAsJsonObject("blok_falsz"); //jeśli nie ma atrybutu blok_falsz, to blok_f = null
        WyrLogiczne warunek = noweWyrLogiczne(war);
        Blok blok_prawda = nowyBlok(blok_p);
        Blok blok_falsz = nowyBlok(blok_f);
        return new If(warunek, blok_prawda, blok_falsz);
    }

    private Not nowyNot(JsonObject jsonObject) {
        JsonObject arg = jsonObject.getAsJsonObject("argument");
        WyrLogiczne argument = noweWyrLogiczne(arg);
        return new Not(argument);
    }

    private And noweAnd(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        WyrLogiczne argument1 = noweWyrLogiczne(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        WyrLogiczne argument2 = noweWyrLogiczne(arg2);
        return new And(argument1, argument2);
    }

    private Or nowyOr(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        WyrLogiczne argument1 = noweWyrLogiczne(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        WyrLogiczne argument2 = noweWyrLogiczne(arg2);
        return new Or(argument1, argument2);
    }

    /*
    Zakładam, że blok jest typu Blok
     */
    private While nowyWhile(JsonObject jsonObject) {
        JsonObject war = jsonObject.getAsJsonObject("warunek");
        JsonObject bl = jsonObject.getAsJsonObject("blok");
        WyrLogiczne warunek = noweWyrLogiczne(war);
        Blok blok = nowyBlok(bl);
        return new While(warunek, blok);
    }

    public Plus nowyPlus(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        Instrukcja argument1 = nowaInstrukcja(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        Instrukcja argument2 = nowaInstrukcja(arg2);
        return new Plus(argument1, argument2);
    }

    public Liczba nowaLiczba(JsonObject jsonObject) {
        double wart = jsonObject.get("wartosc").getAsDouble();
        return new Liczba(wart);
    }

    public Zmienna nowaZmienna(JsonObject jsonObject) {
        String nazwa = jsonObject.get("nazwa").getAsString();

        if (zmienne.containsKey(nazwa)) {
            return zmienne.get(nazwa);
        }
        Zmienna z = new Zmienna(nazwa);
        zmienne.put(nazwa, z);
        return z;
    }

    public Minus nowyMinus(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        Instrukcja argument1 = nowaInstrukcja(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        Instrukcja argument2 = nowaInstrukcja(arg2);
        return new Minus(argument1, argument2);
    }

    public Razy noweRazy(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        Instrukcja argument1 = nowaInstrukcja(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        Instrukcja argument2 = nowaInstrukcja(arg2);
        return new Razy(argument1, argument2);
    }

    public Dzielenie noweDzielenie(JsonObject jsonObject) {
        JsonObject arg1 = jsonObject.getAsJsonObject("argument1");
        Instrukcja argument1 = nowaInstrukcja(arg1);
        JsonObject arg2 = jsonObject.getAsJsonObject("argument2");
        Instrukcja argument2 = nowaInstrukcja(arg2);
        return new Dzielenie(argument1, argument2);
    }



    /*
    Zpisuje wczytany program w pliku w formacie JSON
     */
    void toJSON(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //umożliwienie ładnego formatowania JSONa
        int i = filename.lastIndexOf('.'); //sprawdzanie poprawności nazwy pliku do zapisu
        assert filename.substring(i + 1).equals("json") : "Niepoprawne rozszerzenie pliku do zapisu kodu w formacie JSON";
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(gson.toJson(instrukcja)); //zapisywanie instrukcji w pliku
            myWriter.close();
        } catch (IOException io) {
            System.out.println("Nie udało się zapisać pliku w formacie JSON. Niepoprawna scieżka do pliku.");
        }
    }

    /*
    Zapisuje wczytany program w pliku Javy
     */
    void toJava(String filename) {

        Path ścieżka = Paths.get(filename);
        String plik = ścieżka.getFileName().toString();
        // Sprawdzanie poprawności nazwy pliku - musi mieć rozszerzenie .java i być poprawną nazwą klasy (nie może być np. while.java)
        int i = plik.lastIndexOf('.');
        assert i > 0 : "Niepoprawna nazwa pliku do zapisu kodu w Javie: '" + plik + "'";
        assert plik.substring(i + 1).equals("java") : "Niepoprawne rozszerzenie pliku do zapisu kodu w Javie '('" + plik.substring(i + 1) + "')'";
        String nazwa = plik.substring(0, i); // Nazwa pliku będzie nazwą klasy
        assert SourceVersion.isIdentifier(nazwa) && !SourceVersion.isKeyword(nazwa) : "Niepoprawna nazwa pliku: '('" + nazwa + "')', nie można stworzyć takiej klasy.";

        StringBuilder wyn = new StringBuilder("public class " + nazwa + " { public static void main(String[] args) { ");
        // zmienna pomocnicza służy do pamiętania wyniku instrukcji
        String zmiennaPomoc = nazwaZmiennejPomocniczej();

        //deklaracja wszystkich zmiennych używwanych w programie (oprócz pomocniczych - mogą być deklarowane później)
        for (Map.Entry<String, Zmienna> z : zmienne.entrySet()) {
            wyn.append("double ").append(z.getKey()).append(" = 0;\n");
        }
        wyn.append("double ").append(zmiennaPomoc).append(" = 0;\n");

        wyn.append(instrukcja.wypisz(zmiennaPomoc)); //w tej funkcji zmiennaPomoc staje się wartością wynikową programu
        wyn.append("System.out.println(").append(zmiennaPomoc).append(");}\n}\n");

        try { //zapisywanie napisu zawierającego kod w pliku
            String w = new com.google.googlejavaformat.java.Formatter().formatSource(String.valueOf(wyn)); //umożliwienie ładnego formatowania pliku
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(String.valueOf(w));
            myWriter.close();
        } catch (FormatterException e){
            System.out.println("Nie udało się zapisać pliku Javy. Program Robson nie jest kompilowalny w Javie");
        }
        catch( IOException e) {
            System.out.println("Nie udało się zapisać pliku Javy. Niepoprawna scieżka do pliku.");
        }
    }

    /*
    nazwa zmiennej pomocniczej to nazwa ostatniej zmiennej w programie z sufiksem 'Z'
    lub jeśli nie było żadnej zmiennej, 'x'
    */
    String nazwaZmiennejPomocniczej() {
        if (zmienne.isEmpty())
            return "x";
        else {
            return zmienne.lastEntry().getKey() + "Z";
        }
    }

    /*
    Wylicza wartość instrukcji
     */
    double wykonaj() throws BladWykonania {
        return instrukcja.wynik();
    }
}
