package instrukcje.logiczneDwuarg;

import instrukcje.WyrLogiczne;

public class And extends WyrLogiczneDwuagrumentowe{

    public And(WyrLogiczne argument1, WyrLogiczne argument2) {
        super("And");
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    @Override
    public String toString() {
        return "("+argument1.toString()+") && ("+argument2.toString()+")";
    }
    @Override
    public boolean wynikLogiczny() {
        return argument1.wynikLogiczny()&& argument2.wynikLogiczny();
    }

    @Override
    public String wypisz(String wynik) {

            String wyn ="double "+ wynik +"1= 0;double "+ wynik +"2 =0"+";\n"; //deklaracja zmiennych pomocniczych dla atrybutów
            wyn+=argument1.wypisz(wynik +"1")+ argument2.wypisz(wynik +"2"); //wypisywanie atrybutów - nadanie wartości zmiennycm pomocniczym
            wyn += "if("+ wynik +"1 ==0|| "+ wynik +"2==0) "+ wynik + "= 0;\nelse "+ wynik +"=1;\n";
            return wyn;

    }
}
