package instrukcje;

public class False extends WyrLogiczne{

    public False() {
        super("False");
    }

    @Override
    public boolean wynikLogiczny() {
        return false;
    }

    @Override
    public String toString() {
        return "false";
    }

    @Override
    public String wypisz(String wynik) {

         return wynik +" = 0;\n";
    }
}
