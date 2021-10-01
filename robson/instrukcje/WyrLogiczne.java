package instrukcje;

import robson.BladWykonania;

public abstract class WyrLogiczne extends Instrukcja {

    protected WyrLogiczne(String typ) {
        super(typ);
    }

    /*
    Oblicza wynik logiczny instrukcji
     */
    public abstract boolean wynikLogiczny();


    /*
    Zamiana wartości logicznej na double
     */
    @Override
    public double wynik() throws BladWykonania {
        if (wynikLogiczny())
            return 1;
        return 0;
    }


}
