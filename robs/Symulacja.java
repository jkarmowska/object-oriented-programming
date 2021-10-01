package zad1;

import zad1.symulator.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Symulacja {

    public static void main(String[] args) throws FileNotFoundException {
        Symulacja symulacja = new Symulacja();
        ZestawParametrów zestParametrow = new ZestawParametrów();
        symulacja.sprawdźDane(args, zestParametrow);
        char[][] plansza = symulacja.wczytajPlanszę(new File(args[0]), zestParametrow.liczbaWierszy(), zestParametrow.liczbaKolumn());
        try {
            symulacja.wczytajParametry(new File(args[1]), zestParametrow);
            Symulator symulator = new Symulator(zestParametrow, plansza);
            symulator.symuluj();
        } catch (ZłyParametr złyParametr) {
            System.out.println("Podano złą nazwę parametru: " + złyParametr);
        } catch (PowtarzającyParametr parametr) {
            System.out.println("Podano parametr " + parametr + "więcej niż raz");
        }
    }

    public void sprawdźDane(String[] args, ZestawParametrów param) throws FileNotFoundException {
        assert (args.length == 2) : "Niepoprawna liczba parametrów (" + args.length + "). Podaj dwa parametry.";
        File planszaPlik = new File(args[0]);
        sprawdźPlanszę(planszaPlik, param);
    }

    public void sprawdźPlanszę(File plansza, ZestawParametrów param) throws FileNotFoundException {
        assert (plansza.length() > 0) : "Pusta plansza";
        Scanner skPlanszy = new Scanner(plansza);
        String linia;
        int dl_x = 0, dl_y = 0;
        while (skPlanszy.hasNextLine()) {
            linia = skPlanszy.nextLine();
            if (dl_y == 0)
                dl_x = linia.length(); //ustawiamy długość wiersza
            else //dl_y > 0
                assert linia.length() == dl_x : "Złe wymiary plaszy"; //sprawdzamy, czy wiersze mają tę samą długość
            for (int i = 0; i < linia.length(); i++) {
                assert linia.charAt(i) == 'x' || linia.charAt(i) == ' ' : "Zły znak w planszy: " + linia.charAt(i);
            }
            dl_y++;
        }
        param.liczbaWierszy(dl_y);
        param.liczbaKolumn(dl_x);
    }

    public char[][] wczytajPlanszę(File plik, int wiersze, int kolumny) throws FileNotFoundException {
        Scanner scanner = new Scanner(plik);
        char[][] pola = new char[wiersze][kolumny];
        scanner.useDelimiter("");
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                pola[i][j] = scanner.next().charAt(0);
            }
            if (scanner.hasNext())
                scanner.next(); //ostatni znak wiersza - '\n'
        }
        return pola;
    }

    public void wczytajParametry(File param, ZestawParametrów zestParametrow) throws FileNotFoundException, PowtarzającyParametr, ZłyParametr {
        Scanner scanner = new Scanner(param);
        int ile = 0; //musi byc 15 parametrow (rozmiar_x i rozmiar_y nie pojawiają się - są wczytywane z planszy)
        String linia;
        while (scanner.hasNextLine()) {
            ile++;
            linia = scanner.nextLine();
            wczytajParametr(linia, zestParametrow, ile);
        }
        assert zestParametrow.ileWszystkich() == 15 : "Podano za mało parametrów";
        zestParametrow.sprawdźZgodnośćProgramu();
    }


    public void wczytajParametr(String linia, ZestawParametrów param, int nrLinii) throws PowtarzającyParametr, ZłyParametr {
        assert linia.contains(" ") : "Błędny parametr. Linia " + nrLinii + " nie zawiera znaku spacji";
        String nazwa = linia.substring(0, linia.indexOf(" "));
        String wartosc = linia.substring(linia.indexOf(" ") + 1);
        param.dodaj(nazwa, wartosc);
    }
}
