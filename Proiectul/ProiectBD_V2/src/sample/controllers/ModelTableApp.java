package sample.controllers;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ModelTableApp {

    private String numePizza, ingrediente, origine;
    private ImageView imagine;
    private int pret;
    private int id;
    private int cantitate;
    private String ingredient;
    private String detalii;
    private String numeUtilizator;

    public ModelTableApp(String numePizza, int idIngredient, String numeIngredient){
        this.numePizza = numePizza;
        this.id = idIngredient;
        this.ingredient = numeIngredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public ModelTableApp(String nume, String detalii){
        numeUtilizator = nume;
        this.detalii = detalii;
    }

    private String cartier, strada,adresa;

    public ModelTableApp(int id, String cartier, String strada, String adresa){
        this.id = id;
        this.cartier = cartier;
        this.strada = strada;
        this.adresa = adresa;
    }



    public ModelTableApp(int id, String detalii){
        this.id= id;
        this.detalii = detalii;
    }

    public ModelTableApp(int id, String numePizza,String Ingrediente, String origine, int pret, ImageView imagine){
        this.id = id;
        this.numePizza = numePizza;
        this.ingrediente = Ingrediente;
        this.origine = origine;
        this.pret = pret;
        this.imagine = imagine;
    }

    public ModelTableApp(int id, String numePizza, int pret, int cantitate, ImageView imagine){
        this.id = id;
        this.numePizza = numePizza;
        this.pret = pret;
        this.cantitate = cantitate;
    }


    @Override
    public String toString() {
        return "numePizza='" + numePizza + '\'' +
                ", ingrediente='" + ingrediente + '\'' +
                ", origine='" + origine + '\'' +
                ", pret='" + pret + '\'';
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getCartier() {
        return cartier;
    }

    public void setCartier(String cartier) {
        this.cartier = cartier;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public ImageView getImagine() {
        return imagine;
    }

    public void setImagine(ImageView imagine) {
        this.imagine = imagine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumePizza() {
        return numePizza;
    }

    public void setNumePizza(String numePizza) {
        this.numePizza = numePizza;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
