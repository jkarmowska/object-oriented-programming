package instrukcje.arytmetyczneDwuarg;

import instrukcje.Instrukcja;
import robson.BladWykonania;

public class Dzielenie extends WyrArytmetyczneDwuarg {

    public Dzielenie(Instrukcja argument1, Instrukcja argument2) {

        super(argument1, argument2,"Dzielenie");
    }

    public String toString() {
        return "(" + argument1.toString() + ") / (" + argument2.toString() + ")";
    }

    @Override
    public double wynik() throws BladWykonania {
        if (argument2.wynik() == 0) //dzielenie przez zero niedozwolone
            throw new BladWykonania("Dzielnik '" + argument2.toString() + "' jest równy zero!");
        return argument1.wynik() / argument2.wynik();
    }

    @Override
    public String wypisz(String wynik) {

            String wyn = "double " + wynik + "1= 0;double " + wynik + "2 =0" + ";\n";//deklaracja zmiennych pomocniczych dla atrybutów
            wyn += argument1.wypisz(wynik + "1") + argument2.wypisz(wynik + "2"); //wypisywanie atrybutów - nadanie wartości zmiennycm pomocniczym
            wyn += wynik + " = " + wynik + "1 / " + wynik + "2;\n"; //nadanie wartości zmiennej wynik
            return wyn;

    }
}
