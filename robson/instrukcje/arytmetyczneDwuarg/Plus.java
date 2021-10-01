package instrukcje.arytmetyczneDwuarg;

import instrukcje.Instrukcja;
import robson.BladWykonania;

public class Plus extends WyrArytmetyczneDwuarg{

    public Plus(Instrukcja arg1, Instrukcja arg2)
    {
        super(arg1, arg2,"Plus");
    }
    public String toString() {
        return "("+argument1.toString()+") + ("+argument2.toString()+")";
    }
    @Override
    public double wynik() throws BladWykonania {
        return argument1.wynik()+argument2.wynik();
    }

    @Override
    public String wypisz(String wynik) {

            String wyn ="double "+ wynik +"1= 0;double "+ wynik +"2 =0"+";\n"; //deklaracja zmiennych pomocniczych dla atrybutów
            wyn+=argument1.wypisz(wynik +"1")+ argument2.wypisz(wynik +"2"); //wypisywanie atrybutów - nadanie wartości zmiennycm pomocniczym
            wyn += wynik + " = "+ wynik +"1 + "+ wynik +"2;\n"; //nadanie wartości zmiennej wynik
            return wyn;

    }
}
