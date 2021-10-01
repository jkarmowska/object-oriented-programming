package instrukcje.logiczneDwuarg;

import instrukcje.WyrLogiczne;
import instrukcje.Zmienna;

public abstract class WyrLogiczneDwuagrumentowe extends WyrLogiczne {
    protected WyrLogiczne argument1;
    protected WyrLogiczne argument2;


    protected WyrLogiczneDwuagrumentowe(String typ) {
        super(typ);
    }
}
