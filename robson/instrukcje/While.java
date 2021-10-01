package instrukcje;

import robson.BladWykonania;

public class While extends Instrukcja {
    private final WyrLogiczne warunek;
    private final Blok blok;


    public While(WyrLogiczne warunek, Blok blok) {
        super("While");
        this.warunek = warunek;
        this.blok = blok;
    }

    @Override
    public String toString() {
        String wyn = "while(";
        wyn += warunek.toString();
        wyn += ")  ";
        wyn += blok.toString();
        return wyn;
    }

    @Override
    public String wypisz(String wynik) {

        String wyn = "double " + wynik + "1 = 0;\n"; //deklaracja zmiennej pomocniczej dla atrybutu blok
        wyn += "while("+warunek.toString()+")  ";
        wyn += blok.wypisz(wynik + "1");
        return wyn + wynik + " = 0;\n"; //wynik operacji While to zawsze 0

    }

    @Override
    public double wynik() throws BladWykonania {
        while (warunek.wynikLogiczny()) {
            blok.wynik();
        }
        return 0;
    }
}
