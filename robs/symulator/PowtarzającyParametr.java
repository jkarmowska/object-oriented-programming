package zad1.symulator;

public class PowtarzającyParametr extends Exception {
    private final String nazwa;

    public PowtarzającyParametr(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
