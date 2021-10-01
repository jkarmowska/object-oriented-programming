package instrukcje;

import robson.BladWykonania;

public class Przypisanie extends Instrukcja {
    private transient final Zmienna zmienna; //transient - nie będzie wypisany w jsonie
    private final String nazwa; //atrybut potrzebny do wypisywania w formacie json
    private final Instrukcja wartosc;

    @Override
    public String toString() {
        return zmienna.toString()+" = "+ wartosc.toString();
    }

    @Override
    public String wypisz(String wynik) {

            String wyn ="double "+ wynik +"1=0;\n";
            wyn+= wartosc.wypisz(wynik +"1");
            wyn+=zmienna.toString()+" = "+ wynik +"1;\n "+ wynik +" = "+ wynik +"1";
            return wyn;
    }

    public Przypisanie(Zmienna zmienna, Instrukcja wartosc) {
        super("Przypisanie");
        this.zmienna=zmienna;
        this.wartosc = wartosc;
        nazwa = zmienna.nazwa();
    }

    @Override
    public double wynik() throws BladWykonania {
        zmienna.wartosc(wartosc.wynik());
        return wartosc.wynik();
    }
}
