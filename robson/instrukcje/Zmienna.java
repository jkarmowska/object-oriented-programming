package instrukcje;

import robson.BladWykonania;

public class Zmienna extends WyrArytmetyczne {
    private final String nazwa;
    private transient double wartosc;


    public Zmienna(String nazwa) {
        super("Zmienna");
        this.nazwa = nazwa;
        this.wartosc = 0;
    }
    public String nazwa() {
        return nazwa;
    }


    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public String wypisz(String wynik) {
        return wynik +" = "+ nazwa+";";
    }

    public void wartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    @Override
    public double wynik() throws BladWykonania {
        return wartosc;
    }
}
