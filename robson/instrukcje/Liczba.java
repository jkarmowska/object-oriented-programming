package instrukcje;

import robson.BladWykonania;

public class Liczba extends WyrArytmetyczne {
    private final double wartosc;


    @Override
    public String toString() {
        return wartosc+"";
    }

    @Override
    public String wypisz(String wynik) {
        return wynik +" = "+ wartosc+";";
    }

    public Liczba(double wartosc){
        super("Liczba");
        this.wartosc = wartosc;
    }

    @Override
    public double wynik() throws BladWykonania {
        return wartosc;
    }
}
