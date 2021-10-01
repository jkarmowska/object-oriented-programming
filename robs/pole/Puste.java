package zad1.pole;

public class Puste extends Pole {
    public Puste() {
        super(false);
    }

    @Override
    public int dajJedzenie() {
        return 0;
    }

    @Override
    public boolean regeneruj() {
        return false;
    }

    @Override
    public String toString() {
        return " ";
    }
}
