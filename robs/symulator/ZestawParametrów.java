package zad1.symulator;

import java.util.Arrays;

import zad1.rob.Program;

public class ZestawParametrów {
    // private static final ArrayList<String> nazwy = new ArrayList<>(15);
    private char[] spis;
    private Program poczProg;
    private double prPow;
    private double ulEnergiiRodzica;
    private double prUsunięcia;
    private double prDodania;
    private double prZmiany;
    private int ileTur;
    private int poczIleRobów;
    private int poczEnergia;
    private int ileDajeJedzenie;
    private int ileRosnieJedzenie;
    private int limitPowielania;
    private int kosztTury;
    private int coIleWypisz;
    private int liczbaWierszy;//rozmiar y
    private int liczbaKolumn;//rozmiar x
    private int licznik = 0;

    public ZestawParametrów() {
        this.spis = new char[1];
        spis[0] = '-';
        this.poczProg = null;
        this.prPow = -1;
        this.ulEnergiiRodzica = -1;
        this.prUsunięcia = -1;
        this.prDodania = -1;
        this.prZmiany = -1;
        this.ileTur = -1;
        this.poczIleRobów = -1;
        this.poczEnergia = -1;
        this.ileDajeJedzenie = -1;
        this.ileRosnieJedzenie = -1;
        this.limitPowielania = -1;
        this.kosztTury = -1;
        this.coIleWypisz = -1;
        this.liczbaWierszy = -1;
        this.liczbaKolumn = -1;
        this.licznik = 0;
    }

    public char[] spis() {
        return spis;
    }

    public Program poczProg() {
        return poczProg;
    }

    public double prPow() {
        return prPow;
    }

    public double ułEnergiiRodzica() {
        return ulEnergiiRodzica;
    }

    public double prUsunięcia() {
        return prUsunięcia;
    }

    public double prDodania() {
        return prDodania;
    }

    public double prZmiany() {
        return prZmiany;
    }

    public int ileTur() {
        return ileTur;
    }

    public int poczIleRobów() {
        return poczIleRobów;
    }

    public int poczEnergia() {
        return poczEnergia;
    }

    public int ileDajeJedzenie() {
        return ileDajeJedzenie;
    }

    public int ileRośnieJedzenie() {
        return ileRosnieJedzenie;
    }

    public int limitPowielania() {
        return limitPowielania;
    }

    public int kosztTury() {
        return kosztTury;
    }

    public int coIleWypisz() {
        return coIleWypisz;
    }

    public int liczbaWierszy() {
        return liczbaWierszy;
    }

    public int liczbaKolumn() {
        return liczbaKolumn;
    }

    public void liczbaWierszy(int liczbaWierszy) {
        this.liczbaWierszy = liczbaWierszy;
    }

    public void liczbaKolumn(int liczbaKolumn) {
        this.liczbaKolumn = liczbaKolumn;
    }

    public int ileWszystkich() {
        return licznik;
    }

    public void dodaj(String nazwa, String wartosc) throws ZłyParametr, PowtarzającyParametr {
        switch (nazwa) {
            case "spis_instr": {
                if (spis.length == 1 && spis[0] == '-') {
                    assert sprawdźInstr(wartosc) : "Nieprawidłowy symbol ciągu instrukcji w spisie: " + wartosc;
                    wczytajSpis(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pocz_progr": {
                if (poczProg == null) {
                    assert sprawdźInstr(wartosc) : "Nieprawidłowy symbol ciągu instrukcji w programie początkowym: " + wartosc;
                    poczProg = new Program(wartosc.toCharArray());
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pr_powielenia": {
                if (prPow == -1) {
                    assert sprawdzDouble(wartosc) : "Nieprawidłowa wartość pr. powielenia (" + wartosc + "). Musi być liczbą rzeczywistą z zakresu [0,1]";
                    prPow = Double.parseDouble(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "ułamek_energii_rodzica": {
                if (ulEnergiiRodzica == -1) {
                    assert sprawdzDouble(wartosc) : "Nieprawidłowa wartość uł. energii rodzica (" + wartosc + "). Musi być liczbą rzeczywistą z zakresu [0,1]";
                    ulEnergiiRodzica = Double.parseDouble(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pr_usunięcia_instr": {
                if (prUsunięcia == -1) {
                    assert sprawdzDouble(wartosc) : "Nieprawidłowa wartość pr. usunięcia instrukcji (" + wartosc + "). Musi być liczbą rzeczywistą z zakresu [0,1]";
                    prUsunięcia = Double.parseDouble(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pr_dodania_instr": {
                if (prDodania == -1) {
                    assert sprawdzDouble(wartosc) : "Nieprawidłowa wartość pr. dodania instrukcji (" + wartosc + "). Musi być liczbą rzeczywistą z zakresu [0,1]";
                    prDodania = Double.parseDouble(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pr_zmiany_instr": {
                if (prZmiany == -1) {
                    assert sprawdzDouble(wartosc) : "Nieprawidłowa wartość pr. zmiany (" + wartosc + "). Musi być liczbą rzeczywistą z zakresu [0,1]";
                    prZmiany = Double.parseDouble(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "ile_tur": {
                if (ileTur == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość liczby tur (" + wartosc + ") . Musi być liczbą całkowitą >=0";
                    ileTur = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pocz_ile_robów": {
                if (poczIleRobów == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość liczby robów (" + wartosc + ") . Musi być liczbą całkowitą >=0";
                    poczIleRobów = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "pocz_energia": {
                if (poczEnergia == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość energii początkowej (" + wartosc + "). Musi być liczbą całkowitą >=0";
                    poczEnergia = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "ile_daje_jedzenie": {
                if (ileDajeJedzenie == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość ile daje jedzenie (" + wartosc + "). Musi być liczbą całkowitą >=0";
                    ileDajeJedzenie = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "ile_rośnie_jedzenie": {
                if (ileRosnieJedzenie == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość ile rośnie jedzenie (" + wartosc + "). Musi być liczbą całkowitą >=0";
                    ileRosnieJedzenie = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "limit_powielania": {
                if (limitPowielania == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość limitu powielania (" + wartosc + ") . Musi być liczbą całkowitą >=0";
                    limitPowielania = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "koszt_tury": {
                if (kosztTury == -1) {
                    assert sprawdźInt(wartosc, 0) : "Nieprawidłowa wartość kosztu tury (" + wartosc + ") . Musi być liczbą całkowitą >=0";
                    kosztTury = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            case "co_ile_wypisz": {
                if (coIleWypisz == -1) {
                    assert sprawdźInt(wartosc, 1) : "Nieprawidłowa wartość co ile wypisz (" + wartosc + ") . Musi być liczbą całkowitą >=1";
                    coIleWypisz = Integer.parseInt(wartosc);
                } else throw new PowtarzającyParametr(nazwa);
                break;
            }
            default:
                throw new ZłyParametr(nazwa);
        }
        licznik++;
    }

    private void wczytajSpis(String wartość) {
        char[] tab = new char[5];
        int ind = 0;
        boolean czyBył;
        for (int i = 0; i < wartość.length() && ind < 5; i++) { //wczytywanie spisu dostęych poleceń tak, aby każde wystąpiło tylko raz
            czyBył = false;
            for (int j = 0; j < i && j < 5; j++) {
                if (wartość.charAt(i) == tab[j]) {
                    czyBył = true;
                    break;
                }
            }
            if (!czyBył) {
                tab[ind] = wartość.charAt(i);
                ind++;
            }
        }
        spis = Arrays.copyOf(tab, ind);
    }

    //sprawdza, czy wszystkie znaki w 'wartość' są dozwolone
    private boolean sprawdźInstr(String wartość) {
        return wartość.equals("") || wartość.matches("[lpiwj]+");
    }

    private boolean sprawdźInt(String wartość, int min) {
        int a;
        try {
            a = Integer.parseInt(wartość);
            return a >= min;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean sprawdzDouble(String wartosc) {
        double d;
        try {
            d = Double.parseDouble(wartosc);
            return d >= 0 && d <= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //sprawdza, czy program początkowy jest zgodny ze spisem dozwolonych instrukcji
    public void sprawdźZgodnośćProgramu() {
        for (int i = 0; i < poczProg.długość(); i++) {
            boolean czyJest = false;
            for (char spi : spis) {
                if (poczProg.instrukcja(i) == spi) {
                    czyJest = true;
                    break;
                }
            }
            assert czyJest : "Program początkowy niezgodny ze spisem";
        }
    }

    @Override
    public String toString() {
        return "ZestawParametrow{" +
                "\n spis=" + Arrays.toString(spis) +
                "\n poczProg=" + poczProg +
                "\n, prPow=" + prPow +
                "\n, ulEnergiiRodzica=" + ulEnergiiRodzica +
                "\n, prUsuniecia=" + prUsunięcia +
                "\n, prDodania=" + prDodania +
                "\n, prZmiany=" + prZmiany +
                "\n, ileTur=" + ileTur +
                "\n, poczIleRobow=" + poczIleRobów +
                "\n, poczEnergia=" + poczEnergia +
                "\n, ileDajeJedzenie=" + ileDajeJedzenie +
                "\n, ileRosnieJedzenie=" + ileRosnieJedzenie +
                "\n, limitPowielania=" + limitPowielania +
                "\n, kosztTury=" + kosztTury +
                "\n, coIleWypisz=" + coIleWypisz +
                "\n, liczbaWierszy=" + liczbaWierszy +
                "\n, liczbaKolumn=" + liczbaKolumn +
                '}';
    }
}
