package zad1.pole;

public class ZJedzeniem extends Pole {
    private final int ileDajeJedzenie;
    private final int ileRosnieJedzenie;
    private int ileJuzUroslo;

    public ZJedzeniem(int ileDajeJedzenie, int ileRosnieJedzenie) {
        super(true);
        this.ileDajeJedzenie = ileDajeJedzenie;
        this.ileRosnieJedzenie = ileRosnieJedzenie;
        this.ileJuzUroslo = ileRosnieJedzenie;
    }

    @Override
    public int dajJedzenie() {
        if (czyJedzenie) {
            czyJedzenie = false;
            ileJuzUroslo = 0;
            return ileDajeJedzenie;
        } else return 0;
    }

    @Override
    public boolean regeneruj() {
        if (!czyJedzenie) {
            ileJuzUroslo++;
            if (ileJuzUroslo == ileRosnieJedzenie) {
                czyJedzenie = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "x";
    }
}
