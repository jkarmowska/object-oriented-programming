package zad1.rob;

import zad1.plansza.Plansza;

public class Rob {
    static final int GÓRA = 0;
    static final int PRAWO = 1;
    static final int DÓŁ = 2;
    static final int LEWO = 3;

    private int energia;
    private int kierunek;
    private int nrKolumny;
    private int nrWiersza;
    private int wiek;
    private final int id;
    private final char[] spis;
    private final Program program;
    private final Plansza plansza;

    public Rob(int id, int energia, Program program, int kierunek, int nrKolumny, int nrWiersza, Plansza plansza, char[] spis) {
        this.id = id;
        this.energia = energia;
        this.program = program;
        this.spis = spis;
        this.kierunek = kierunek;
        this.nrKolumny = nrKolumny;
        this.nrWiersza = nrWiersza;
        this.plansza = plansza;
        this.wiek = 0;
    }

    public int wiek() {
        return wiek;
    }

    public int energia() {
        return energia;
    }

    public int dłProgramu() {
        return program.długość();
    }

    public void wykonajProgram(int koszt) {
        energia -= koszt; //odejmujemy koszt tury
        if (czyPrzeżyje())
            program.wykonaj(this);
    }

    public void obrótLewo() {
        kierunek -= 1;
        if (kierunek == -1)
            kierunek = 3;
    }

    public void obrótPrawo() {
        kierunek += 1;
        if (kierunek == 4)
            kierunek = 0;
    }

    public void idź() {
        if (kierunek == GÓRA)
            nrWiersza = mod(nrWiersza - 1, plansza.liczbaWierszy());
        else if (kierunek == DÓŁ)
            nrWiersza = mod(nrWiersza + 1, plansza.liczbaWierszy());
        else if (kierunek == PRAWO)
            nrKolumny = mod(nrKolumny + 1, plansza.liczbaKolumn());
        else if (kierunek == LEWO)
            nrKolumny = mod(nrKolumny - 1, plansza.liczbaKolumn());
        energia += plansza.dajJedzenie(nrWiersza, nrKolumny);
    }

    public void wąchaj() {
        if (znajdźPożywienie(nrWiersza, mod(nrKolumny + 1, plansza.liczbaKolumn())))
            kierunek = PRAWO;
        else if (znajdźPożywienie(mod(nrWiersza - 1, plansza.liczbaWierszy()), nrKolumny))
            kierunek = GÓRA;
        else if (znajdźPożywienie(nrWiersza, mod(nrKolumny - 1, plansza.liczbaKolumn())))
            kierunek = LEWO;
        else if (znajdźPożywienie(mod(nrWiersza + 1, plansza.liczbaWierszy()), nrKolumny))
            kierunek = DÓŁ;
    }

    public void jedz() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i != 0 || j != 0) && znajdźPożywienie(mod(nrWiersza + i, plansza.liczbaWierszy()), mod(nrKolumny + j, plansza.liczbaKolumn()))) {
                    idźNaPole(mod(nrWiersza + i, plansza.liczbaWierszy()), mod(nrKolumny + j, plansza.liczbaKolumn()));
                    zmieńKierunek(i, j);
                    energia += plansza.dajJedzenie(nrWiersza, nrKolumny);
                    return;
                }
            }
        }
    }

    private void zmieńKierunek(int wiersz, int kol) {
        if (wiersz == 1)
            kierunek = GÓRA;
        else if (wiersz == -1)
            kierunek = DÓŁ;
        else if (kol == 1)
            kierunek = PRAWO;
        else if (kol == -1)
            kierunek = LEWO;
    }

    private boolean znajdźPożywienie(int wiersz, int kol) {
        return plansza.czyJedzenie(wiersz, kol);
    }

    private void idźNaPole(int wiersz, int kol) {
        nrWiersza = wiersz;
        nrKolumny = kol;
        energia += plansza.dajJedzenie(nrWiersza, nrKolumny);
    }

    private int mod(int a, int b) {
        if (a >= 0)
            return a % b;
        else return (a % b) + b;
    }

    public boolean czyPrzeżyje() {
        return energia >= 0;
    }

    public void zmniejszEnergię() {
        energia--;
    }

    public void starzej() {
        wiek++;
    }

    public Rob powielSię(int idNowe, double ulamek, double prUsuneicia, double prDodania, double prZmiany) {
        Program nowyProg = new Mutacja().zmutuj(program, prUsuneicia, prDodania, prZmiany, spis);
        int nowyKier = mod(kierunek + 2, 4);
        Rob nowy = new Rob(idNowe, (int) (energia * ulamek), nowyProg, nowyKier, nrKolumny, nrWiersza, plansza, spis);
        energia -= (int) (energia * ulamek);
        return nowy;
    }

    @Override
    public String toString() {
        return String.format("Rob numer %-6d, poziom energii: %-6d, wiek: %-6d, współrzędne (w,k): (%4d, %-4d),  program: ",
                id, energia, wiek, nrWiersza, nrKolumny) + program + ".";
    }
}
