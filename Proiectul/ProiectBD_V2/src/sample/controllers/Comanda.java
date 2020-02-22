package sample.controllers;

public class Comanda {


    private static int locatie;
    private static String livrator;

    public Comanda(int nume) {
        this.locatie = nume;
    }
    public Comanda(String livrator) {
        this.livrator = livrator;
    }
    public Comanda(){}

    public  int getLocatie() {
        return locatie;
    }

    public  void setLocatie(int locatie) {
        Comanda.locatie = locatie;
    }

    public  String getLivrator() {
        return livrator;
    }

    public  void setLivrator(String livrator) {
        Comanda.livrator = livrator;
    }
}
