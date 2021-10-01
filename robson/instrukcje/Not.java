package instrukcje;

public class Not extends WyrLogiczne {
    private final WyrLogiczne argument;


    public Not(WyrLogiczne argument) {
        super("Not");
        this.argument = argument;
    }

    @Override
    public boolean wynikLogiczny() {
        return !argument.wynikLogiczny();
    }

    @Override
    public String toString() {
        return "!(" + argument.toString() + ")";
    }

    @Override
    public String wypisz(String wynik) {
        String wyn = "double " + wynik + "1=0;\n";
        wyn += argument.wypisz(wynik + "1");

        wyn += "if(!(" + wynik + "1 ==1)) ";
        wyn += wynik + " = 1;\n else " + wynik + " = 0;";
        return wyn;

    }
}
