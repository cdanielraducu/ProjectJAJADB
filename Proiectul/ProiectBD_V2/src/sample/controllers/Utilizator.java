package sample.controllers;

public class Utilizator {

    private static String nume;
    private static int totalPlata;
    private static String locatie;


    public Utilizator(String nume) {
        this.nume = nume;
    }
    public Utilizator(){}
    public Utilizator(int totalPlata){ this.totalPlata = totalPlata; }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getTotalPlata() {
        return totalPlata;
    }

    public static void setTotalPlata(int totalPlata) {
        Utilizator.totalPlata = totalPlata;
    }
}
