package zad1.rob;

import java.util.Random;

public class Mutacja {
    private final Random random;

    public Mutacja() {
        random = new Random();
    }

    public Program zmutuj(Program progRodzica, double prUsuneicia, double prDodania, double prZmiany, char[] spis) {
        Program wynik = new Program(progRodzica);
        usuń(wynik, prUsuneicia);
        dodaj(wynik, prDodania, spis);
        zmień(wynik, prZmiany, spis);
        return wynik;
    }

    private void usuń(Program program, double prUsuneicia) {
        if (program.długość() == 0)
            return;
        double x = random.nextDouble();
        if (x <= prUsuneicia)
            program.usuńOstatnią();

    }

    private void dodaj(Program program, double prDodania, char[] spis) {
        double x = random.nextDouble();
        if (x <= prDodania) {
            int i = random.nextInt(spis.length);
            char instr = spis[i];
            program.dodajOstatnią(instr);
        }
    }

    private void zmień(Program program, double prZmiany, char[] spis) {
        if (program.długość() == 0)
            return;
        double x = random.nextDouble();
        if (x <= prZmiany) {
            int i = random.nextInt(spis.length);
            char instr = spis[i];
            i = random.nextInt(program.długość());
            program.zmieńInstrukcję(i, instr);
        }
    }

}
