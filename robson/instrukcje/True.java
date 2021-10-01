package instrukcje;

public class True extends WyrLogiczne{

    public True() {
        super("True");
    }

    @Override
    public boolean wynikLogiczny() {
        return true;
    }

    @Override
    public String toString() {
        return "true";
    }

    @Override
    public String wypisz(String wynik) {
        return wynik +" = 1;\n";
    }
}
