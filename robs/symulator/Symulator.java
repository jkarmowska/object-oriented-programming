package zad1.symulator;

import zad1.plansza.Plansza;
import zad1.rob.Rob;

import java.util.ArrayList;
import java.util.Random;

public class Symulator {
    ZestawParametrów parametry;
    private int numerTury;
    private int ileŻyje;
    private int ileUmarlo;
    private int ileUmarloTura;
    private int ileWszystkich;
    private int ileNowych;

    Plansza plansza;
    ArrayList<Rob> roby;
    ArrayList<Rob> noweRoby;

    public Symulator(ZestawParametrów parametry, char[][] tab) {
        this.parametry = parametry;
        this.plansza = new Plansza(tab, parametry.liczbaWierszy(), parametry.liczbaKolumn(), parametry.ileDajeJedzenie(), parametry.ileRośnieJedzenie());
        this.numerTury = 0;
        this.ileŻyje = parametry.poczIleRobów();
        this.ileUmarlo = 0;
        this.ileUmarloTura = 0;
        this.ileNowych = 0;
        this.ileWszystkich = parametry.poczIleRobów();
        roby = new ArrayList<>();
        for (int i = 0; i < parametry.poczIleRobów(); i++) {
            Random r = new Random();
            int kier = r.nextInt(4);
            int nrK = r.nextInt(parametry.liczbaKolumn());
            int nrW = r.nextInt(parametry.liczbaWierszy());
            Rob rob = new Rob(i + 1, parametry.poczEnergia(), parametry.poczProg(), kier, nrK, nrW, plansza, parametry.spis());

            roby.add(rob);
        }
    }

    public void symuluj() {
        wypiszPoczątek();
        for (int i = 0; i < parametry.ileTur(); i++) {
            numerTury++;
            noweRoby = new ArrayList<>();
            for (int j = 0; j < roby.size(); j++) {
                roby.get(j).starzej();
                roby.get(j).wykonajProgram(parametry.kosztTury());
                możePowiel(roby.get(j));
                if (!roby.get(j).czyPrzeżyje()) {
                    usuńRoba(j);
                    j--;//usuwamy roba z tablicy, więc indeksy się przesuwają
                }
            }
            aktualizuj(); //dodawanie nowych robów
            wypiszKrótko();
            if ((i + 1) % parametry.coIleWypisz() == 0 || i + 1 == parametry.ileTur())
                wypiszDługo();
            czyść(); //przygotowanie liczników do nowej tury
            plansza.regeneruj();
        }
    }

    private void usuńRoba(int index) {
        ileUmarlo++;
        ileUmarloTura++;
        ileŻyje--;
        roby.remove(index);
    }

    private void aktualizuj() {
        roby.addAll(noweRoby);
    }

    private void czyść() {
        noweRoby = new ArrayList<>(0);
        ileUmarloTura = 0;
        ileNowych = 0;
    }

    private void możePowiel(Rob rob) {
        if (rob.energia() >= parametry.limitPowielania()) {
            Random rand = new Random();
            if (rand.nextDouble() <= parametry.prPow()) {
                Rob nowyRob = rob.powielSię(ileWszystkich + 1, parametry.ułEnergiiRodzica(), parametry.prUsunięcia(), parametry.prDodania(), parametry.prZmiany());
                noweRoby.add(nowyRob);
                ileŻyje++;
                ileWszystkich++;
                ileNowych++;
            }
        }
    }

    public void wypiszPoczątek() {
        StringBuilder wynik = new StringBuilder("Początek symulacji. Na planszy jest ");
        wynik.append(roby.size()).append(" robów. Oto one: \n");
        for (Rob rob : roby) {
            wynik.append(rob).append("\n");
        }
        System.out.println(wynik);
    }

    public void wypiszDługo() {
        StringBuilder wynik = new StringBuilder("\nTura nr " + numerTury + "\n");
        wynik.append("W tej turze powstało ").append(ileNowych).append(" robów i umarło ").append(ileUmarloTura);
        wynik.append(" robów. We wszystkich turach żyło ").append(ileWszystkich);
        wynik.append(" robów, umarło już ").append(ileUmarlo).append(" robów.\n");
        wynik.append("Liczba żyjących robów: ").append(ileŻyje).append(", oto żyjące roby:\n");
        for (Rob rob : roby) {
            wynik.append(rob).append("\n");
        }
        System.out.println(wynik);
    }

    public void wypiszKrótko() {
        System.out.printf("%-6d, rob: %6d, żyw: %6d, prg:  %6d/%6.2f/%6d, energ: %6d/%6.2f/%6d, wiek: %6d/%6.2f/%6d \n",
                numerTury, roby.size(), plansza.ileZywnosci(), minDlugośćProgramu(), średniaDlugośćProgramu(), maxDlugośćProgramu(),
                minEnergia(), średniaEnergia(), maxEnergia(), minWiek(), średniWiek(), maxWiek());
    }

    private int minDlugośćProgramu() {
        if (roby.size() == 0)
            return 0;
        int min = Integer.MAX_VALUE;
        for (Rob rob : roby) {
            if (rob.dłProgramu() < min)
                min = rob.dłProgramu();
        }
        return min;
    }

    private int maxDlugośćProgramu() {
        if (roby.size() == 0)
            return 0;
        int max = Integer.MIN_VALUE;
        for (Rob rob : roby) {
            if (rob.dłProgramu() > max)
                max = rob.dłProgramu();
        }
        return max;
    }

    private double średniaDlugośćProgramu() {
        if (roby.size() == 0)
            return 0;
        double średnia = 0;
        for (Rob rob : roby) {
            średnia += rob.dłProgramu();
        }
        return średnia / roby.size();
    }

    private int minEnergia() {
        if (roby.size() == 0)
            return 0;
        int min = Integer.MAX_VALUE;
        for (Rob rob : roby) {
            if (rob.energia() < min)
                min = rob.energia();
        }
        return min;
    }

    private int maxEnergia() {
        if (roby.size() == 0)
            return 0;
        int max = Integer.MIN_VALUE;
        for (Rob rob : roby) {
            if (rob.energia() > max)
                max = rob.energia();
        }
        return max;
    }

    private double średniaEnergia() {
        if (roby.size() == 0)
            return 0;
        double średnia = 0;
        for (Rob rob : roby) {
            średnia += rob.energia();
        }
        return średnia / roby.size();
    }

    private int minWiek() {
        if (roby.size() == 0)
            return 0;
        int min = Integer.MAX_VALUE;
        for (Rob rob : roby) {
            if (rob.wiek() < min)
                min = rob.wiek();
        }
        return min;
    }

    private int maxWiek() {
        if (roby.size() == 0)
            return 0;
        int max = Integer.MIN_VALUE;
        for (Rob rob : roby) {
            if (rob.wiek() > max)
                max = rob.wiek();
        }
        return max;
    }

    private double średniWiek() {
        if (roby.size() == 0)
            return 0;
        double średnia = 0;
        for (Rob rob : roby) {
            średnia += rob.wiek();
        }
        return średnia / roby.size();

    }

}
