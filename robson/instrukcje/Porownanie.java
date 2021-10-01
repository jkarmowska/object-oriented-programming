package instrukcje;

import instrukcje.logiczneDwuarg.WyrLogiczneDwuagrumentowe;

public class Porownanie extends WyrLogiczne {
    private final Instrukcja argument1;
    private final Instrukcja argument2;

    @Override
    public String toString() {
        String wyn = "(" + argument1.toString() + ") " + typ + " (";
        return wyn + argument2.toString() + ")";
    }

    @Override
    public String wypisz(String wynik) {
        String wyn = "double " + wynik + "1= 0;double " + wynik + "2 =0" + ";\n"; //deklaracja zmiennych pomocniczych dla atrybutów
        wyn += argument1.wypisz(wynik + "1") + argument2.wypisz(wynik + "2"); //wypisywanie atrybutów - nadanie wartości zmiennycm pomocniczym
        //nadanie wartości zmiennej wynik - 1 jeśli porównanie jest prawdziwe, 0 wpp.
        //porównujemy wartości zmiennych pomocniczych, które są wynikami dwóch członów porównania
        wyn += "if(" + wynik + "1 ";
        wyn += typ + " ";
        wyn += wynik + "2 )\n" + wynik + " = 1;\n else " + wynik + " = 0;";
        return wyn;
    }


    public Porownanie(Instrukcja argument1, Instrukcja argument2, String typ) {
        super(typ);
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    @Override
    public boolean wynikLogiczny() {
        switch (typ) {
            case "==":
                try {
                    return argument1.wynik() == argument2.wynik();
                } catch (robson.BladWykonania bladWykonania) {
                    bladWykonania.printStackTrace();
                }
            case ">":
                try {
                    return argument1.wynik() > argument2.wynik();
                } catch (robson.BladWykonania bladWykonania) {
                    bladWykonania.printStackTrace();
                }
            case "<":
                try {
                    return argument1.wynik() < argument2.wynik();
                } catch (robson.BladWykonania bladWykonania) {
                    bladWykonania.printStackTrace();
                }
            case ">=":
                try {
                    return argument1.wynik() >= argument2.wynik();
                } catch (robson.BladWykonania bladWykonania) {
                    bladWykonania.printStackTrace();
                }
            case "<=":
                try {
                    return argument1.wynik() <= argument2.wynik();
                } catch (robson.BladWykonania bladWykonania) {
                    bladWykonania.printStackTrace();
                }
            default:
                return false;
        }
    }
}
