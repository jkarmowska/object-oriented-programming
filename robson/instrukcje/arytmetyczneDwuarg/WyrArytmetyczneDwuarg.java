package instrukcje.arytmetyczneDwuarg;

import instrukcje.Instrukcja;
import instrukcje.WyrArytmetyczne;

public abstract class WyrArytmetyczneDwuarg extends WyrArytmetyczne {
    protected Instrukcja argument1;
    protected Instrukcja argument2;

    public WyrArytmetyczneDwuarg(Instrukcja argument1, Instrukcja argument2, String typ){
        super(typ);
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    @Override
    public String wypisz(String wynik) {
        return null;
    }
}
