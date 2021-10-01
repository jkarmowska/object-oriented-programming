package instrukcje;

import robson.BladWykonania;

public class If extends Instrukcja {
    private final WyrLogiczne warunek;
    private final Blok blok_prawda;
    private final Blok blok_falsz;



    public If(WyrLogiczne warunek, Blok blok_prawda, Blok blok_falsz) {
        super("If");
        this.warunek = warunek;
        this.blok_prawda = blok_prawda;
        this.blok_falsz = blok_falsz;
    }

    @Override
    public String toString() {
        return "if(" + warunek.toString() + ")\n" + blok_prawda.toString() + "else\n" + blok_falsz.toString();
    }

    @Override
    public String wypisz(String wynik) {

        String wyn = "double " + wynik + "1= 0;double " + wynik + "2 =0" + ";\n";
        wyn += "double " + wynik + "WAR= 0;";
        wyn += warunek.wypisz(wynik + "WAR"); //wypisanie warunku, zmienna pomocWYR staje się wynikem warunku,
        // czyli w kodzie javy wpisujemy zamiast całego warunku, tylko jego wynik: pomocWYR
        wyn += "if(" + wynik + "WAR==1)\n" + blok_prawda.wypisz(wynik + "1") + "else\n" + blok_falsz.wypisz(wynik + "2");
        wyn += "if(" + wynik + "WAR==1)\n" + wynik + "= " + wynik + "1;\nelse " + wynik + "=" + wynik + "2;\n";//nadanie wartości zmiennej pomoc -
        // wartość blok_prawda, jeśli warunek był spełniony (pomocWAR na pewno nie zmienia się w blokach, bo nie jest tam przekazywana)
        return wyn;
    }

    @Override
    public double wynik() throws BladWykonania {
        if (warunek.wynikLogiczny())
            return blok_prawda.wynik();
        else return blok_falsz.wynik();
    }
}
