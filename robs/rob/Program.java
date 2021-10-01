package zad1.rob;

public class Program {
    private char[] program;
    private int długość;

    public Program(char[] program) {
        this.program = program;
        this.długość = program.length;
    }

    public Program(Program inny) {
        this.długość = inny.długość();
        this.program = new char[długość];
        System.arraycopy(inny.program, 0, this.program, 0, długość);
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder("[ ");
        for (int i = 0; i < długość; i++) {
            wynik.append(program[i]).append(" ");
        }
        return wynik + "]";
    }

    public int długość() {
        return długość;
    }

    public void wykonaj(Rob rob) {
        for (int i = 0; i < długość; i++) {
            wykonajInstrukcję(rob, i);
            if (!rob.czyPrzeżyje())
                break;
        }
    }

    private void wykonajInstrukcję(Rob rob, int i) {
        rob.zmniejszEnergię();
        if (rob.czyPrzeżyje()) {
            if (program[i] == 'l') {
                rob.obrótLewo();
            } else if (program[i] == 'p') {
                rob.obrótPrawo();
            } else if (program[i] == 'i') {
                rob.idź();
            } else if (program[i] == 'w') {
                rob.wąchaj();
            } else if (program[i] == 'j') {
                rob.jedz();
            }
        }
    }

    public char instrukcja(int index) {
        assert index < program.length;
        return program[index];
    }

    public void usuńOstatnią() {
        assert długość > 0;
        długość--;
    }

    public void dodajOstatnią(char instr) {
        if (program.length == długość) {
            char[] nowy = new char[długość * 2 + 1];
            System.arraycopy(program, 0, nowy, 0, długość);
            program = nowy;
        }
        program[długość] = instr;
        długość++;
    }

    public void zmieńInstrukcję(int i, char instr) {
        assert długość > i;
        program[i] = instr;
    }

}
