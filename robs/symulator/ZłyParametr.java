package zad1.symulator;

public class ZłyParametr extends Exception{
    private final String nazwa;

    @Override
    public String toString() {
        return nazwa;
    }

    public ZłyParametr(String nazwa) {
        this.nazwa = nazwa;
    }
}
