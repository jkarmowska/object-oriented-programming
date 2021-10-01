package zad1.pole;

public abstract class Pole {
    protected boolean czyJedzenie;

    public abstract int dajJedzenie();

    public abstract boolean regeneruj();//zwraca true jesli pojawilo się nowe jedzenie

    public Pole(boolean czyJedzenie) {
        this.czyJedzenie = czyJedzenie;
    }

    public boolean czyMaJedzenie() {
        return czyJedzenie;
    }

    @Override
    public abstract String toString();
}
