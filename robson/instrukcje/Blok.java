package instrukcje;

import robson.BladWykonania;

import java.util.ArrayList;
import java.util.List;

public class Blok extends Instrukcja {
    private final List<Instrukcja> instrukcje;

    public List<Instrukcja> instrukcje() {
        return instrukcje;
    }

    public Blok(){
        super("Blok");
        instrukcje = new ArrayList<>();
    }
    public void dodajInstrukcje(Instrukcja instrukcja){
        instrukcje.add(instrukcja);
    }

    @Override
    public String toString() {
        StringBuilder wyn = new StringBuilder("{\n");
        for (Instrukcja ins: instrukcje) {
            wyn.append(ins.toString()).append(";\n");
        }
        return wyn+"}\n";
    }

    @Override
    public String wypisz(String wynik) {

            StringBuilder wyn = new StringBuilder("{\n");
            for (int i=0; i<instrukcje.size();i++){ //delkaracja zmiennych pomocniczych dla każdej instrukcji w bloku
                // (ich nazwy to nazwa zmiennej pomocniczej bloku+numer instrukcji)
                wyn.append("double ").append(wynik).append(i).append(" = 0;\n");
            }
            for (int i=0; i<instrukcje.size();i++) {
                wyn.append(instrukcje.get(i).wypisz(wynik +i)).append(";\n");
            }
            if (instrukcje.isEmpty()) //jeśli blok pusty, to wynik jesß równy zero
                wyn.append(wynik).append(" = 0;\n");
            else  wyn.append(wynik).append(" = ").append(wynik).append(instrukcje.size() - 1).append(";\n"); //wynik bloku to wynik ostatniej instrukcji
            return wyn+"}\n";

    }

    @Override
    public double wynik() throws BladWykonania {
        if (instrukcje.isEmpty())
            return 0;
        double wyn = 0;
        for (Instrukcja i:instrukcje) {
            wyn = i.wynik();
        }
        return wyn;
    }
}
