package instrukcje;

import robson.BladWykonania;

public abstract class Instrukcja {
    public  final String typ;

    public  Instrukcja(String typ) {
        this.typ = typ;
    }

    /*
        Oblicza wartość instrukcji, zgodnie z treścią zadania
    */
    public abstract double wynik() throws BladWykonania;

    @Override
    public abstract String toString();

    /*
    Wypisuje instrukcję w formacie Javy tak, że zmienna o nazwie wynik
    (która została wcześniej zadeklarowana) przyjmuje wartość instrukcji.
    Jeśli jakiś atrybut jest wyrażeniem, to w wynikowym napisie znajduje się deklaracja zmiennej pomocnieczej tego atrybutu
    oraz wynik fukcji wypisz  dla tego atrybutu.
    W kodzie Javy tworzonych jest wiele zmiennych pomocniczych, zapewnia to możliwość kompilacji kodu w programie Robson
    typu "(If(x==0){ coś } + 8)" - operacje na złożonych instrukcjach
     */
    public abstract String wypisz(String wynik);
}
