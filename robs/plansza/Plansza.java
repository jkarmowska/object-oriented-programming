package zad1.plansza;

import zad1.pole.Pole;
import zad1.pole.Puste;
import zad1.pole.ZJedzeniem;

import java.util.Arrays;

public class Plansza {
    private final Pole[][] pola;
    private final int liczbaWierszy;
    private final int liczbaKolumn;
    private int ileZywnosci;

    public Plansza(char[][] tab, int liczbaWierszy, int liczbaKolumn, int ileDajeJedzenie, int ileRosnieJedzenie) {
        this.liczbaWierszy = liczbaWierszy;
        this.liczbaKolumn = liczbaKolumn;
        this.ileZywnosci = 0;
        this.pola = new Pole[this.liczbaWierszy][this.liczbaKolumn];
        for (int i = 0; i < this.liczbaWierszy; i++) {
            for (int j = 0; j < this.liczbaKolumn; j++) {
                if (tab[i][j] == 'x') {
                    this.pola[i][j] = new ZJedzeniem(ileDajeJedzenie, ileRosnieJedzenie);
                    ileZywnosci++;
                } else
                    this.pola[i][j] = new Puste();
            }
        }
    }

    public int liczbaWierszy() {
        return liczbaWierszy;
    }

    public int liczbaKolumn() {
        return liczbaKolumn;
    }

    public int ileZywnosci() {
        return ileZywnosci;
    }

    public boolean czyJedzenie(int wiersz, int kol) {
        return pola[wiersz][kol].czyMaJedzenie();
    }

    public int dajJedzenie(int wier, int kol) {
        if (pola[wier][kol].czyMaJedzenie()) {
            ileZywnosci--;
        }
        return pola[wier][kol].dajJedzenie();
    }

    public void regeneruj() {
        for (int i = 0; i < liczbaWierszy; i++) {
            for (int j = 0; j < liczbaKolumn; j++) {
                if (pola[i][j].regeneruj())
                    ileZywnosci++; //regeneracja jedzenia
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(pola);
    }
}
