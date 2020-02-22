package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML
    private TableView<ModelTableApp> tabel;
    @FXML
    private TableColumn<ModelTableApp, String> col_ID;
    @FXML
    private TableColumn<ModelTableApp, String> col_default1;
    @FXML
    private TableColumn<ModelTableApp, String> col_default2;
    @FXML
    private TableColumn<ModelTableApp, String> col_default3;
    @FXML
    private TableColumn<ModelTableApp, String> col_default4;
    @FXML
    private Label Update2;

    @FXML
    private TextField ce;
    @FXML
    private TextField cu;
    @FXML
    private Button insert;
    @FXML
    private Button sterge;
    @FXML
    private Button edit;

    @FXML
    private TableColumn<ModelTableApp, String> col_default5;

    ObservableList<ModelTableApp> oblist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void goToMenu(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/app.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);

    }

    public void showIstoricComenzi(MouseEvent mouseEvent) {

        oblist.clear();
        tabel.setItems(oblist);

        try {
            Connection con = DbConnect.getInstance().getConnection();

            Utilizator utilizator = new Utilizator();
            String utilizatorDB = utilizator.getNume();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Comenzi where Utilizator = '"+utilizatorDB+"'");

            while(rs.next()) {
                int id = rs.getInt("Comanda_ID");
                int cost = rs.getInt("Cost");
                int livrator = rs.getInt("Livrator_ID");
                String data = rs.getString("Data_comenzii");

                ResultSet r = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Livratori where Livrator_ID = '"+livrator+"'");
                String numeLivrator = "";
                if(r.next()) {
                    numeLivrator = r.getString("Nume");
                }

                String detalii = "Comanda a avut pretul de: " + cost + " si a fost livrata de " + numeLivrator + " la data de: " + data;
                ModelTableApp modelTableApp = new ModelTableApp(id,detalii);

                oblist.add(modelTableApp);
            }
            tabel.setItems(oblist);

//            ResultSet r = con.createStatement().executeQuery("select COUNT(Comanda_ID) nrComenzi from PIZZADANIEL.dbo.Comenzi " +
//                    " GROUP BY '"+utilizatorDB+"'");

//            int nrCom = 0;
//            if(r.next()){
//                nrCom = r.getInt("nrComenzi");
//            }
//            String x = "Ai facut " + nrCom + " comenzi: \n";
//            Update.setText(x);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_default1.setCellValueFactory(new PropertyValueFactory<>("detalii"));



    }

    public void showUtilizatori(MouseEvent mouseEvent) {

        oblist.clear();
        tabel.setItems(oblist);

//        try {
//            Connection con = DbConnect.getInstance().getConnection();
//
//            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Comenzi");
//
//            while(rs.next()) {
//                String utilizatorID = rs.getString("Utilizator");
//                String parola = rs.getString("Parola");
//                String nume = rs.getString("Nume");
//                String prenume = rs.getString("Prenume");
//
//                ResultSet r = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Livratori where Livrator_ID = '"+livrator+"'");
//                String numeLivrator = "";
//                if(r.next()) {
//                    numeLivrator = r.getString("Nume");
//                }
//
//                String detalii = "Comanda a avut pretul de: " + cost + " si a fost livrata de " + numeLivrator + " la data de: " + data;
//                ModelTableApp modelTableApp = new ModelTableApp(id,detalii);
//
//                oblist.add(modelTableApp);
//            }
//            tabel.setItems(oblist);
//
////            ResultSet r = con.createStatement().executeQuery("select COUNT(Comanda_ID) nrComenzi from PIZZADANIEL.dbo.Comenzi " +
////                    " GROUP BY '"+utilizatorDB+"'");
//
////            int nrCom = 0;
////            if(r.next()){
////                nrCom = r.getInt("nrComenzi");
////            }
////            String x = "Ai facut " + nrCom + " comenzi: \n";
////            Update.setText(x);
//        }catch (ClassNotFoundException | SQLException e){
//            e.printStackTrace();
//        }
//        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        col_default1.setCellValueFactory(new PropertyValueFactory<>("detalii"));
//
//    }
    }
    public void showLivratori(MouseEvent mouseEvent) {

    }

    public void showPizza(MouseEvent mouseEvent) {

        oblist.clear();
        tabel.setItems(oblist);
        ce.setOpacity(100);
        Update2.setOpacity(100);
        insert.setOpacity(100);
        edit.setOpacity(100);
        sterge.setOpacity(100);

        try {
            Connection con = DbConnect.getInstance().getConnection();

            Utilizator utilizator = new Utilizator();
            String utilizatorDB = utilizator.getNume();

                ResultSet r = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT");
                while(r.next()) {

                    String numePizza = r.getString("numePizza");
                    int ingredientID = r.getInt("Ingredient_ID");
                    String numeIngredient = r.getString("Nume");

                    ModelTableApp modelTableApp = new ModelTableApp(numePizza, ingredientID, numeIngredient);

                    oblist.add(modelTableApp);
                }

            tabel.setItems(oblist);

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_default1.setCellValueFactory(new PropertyValueFactory<>("numePizza"));
        col_default2.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
    }

    public void editeaza(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

            String idPizza = tabel.getSelectionModel().getSelectedItem().getNumePizza();
            if(!idPizza.isEmpty()){
                ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
                String x = "";
                int contor = 0;
                if(rs.next()) {

                    String Veche = ce.getText();
                    if (!Veche.isEmpty()) {

                        String Noua = cu.getText();

                        if (Noua.isEmpty()) {
                            Noua = null;
                        }
                        Statement statement = con.createStatement();
                        statement.executeUpdate("UPDATE PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT SET " + Veche + " = '" + Noua + "'" +
                                " WHERE numePizza = " + idPizza + "");

                        x = "Ati schimbat: " + Veche + " cu " + Noua + "!";

                        Update2.setText(x);

                    } else {
                        x = "trebuie sa scrieti numele unei coloane!";
                        Update2.setText(x);

                    }
                } else {
                    x = "Selectati o locatie!";
                    Update2.setText(x);
                }

            }

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }
    }

    public void stergere(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

            int idIngredient = tabel.getSelectionModel().getSelectedItem().getId();
            String x = "";
            if(idIngredient != 0){
                //ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT where numePizza = '"+nume+"'");

                    Statement statement = con.createStatement();
                    statement.executeUpdate("DELETE FROM PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT" +
                            " where Ingredient_ID = "+idIngredient+"");

                    x = "Ati sters: " + idIngredient + "!";

                    Update2.setText(x);

                }else {
                    x = "Selectati o locatie!";
                    Update2.setText(x);
                }


        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }

    }

    public void insereaza(ActionEvent actionEvent) {


    }

    public void afisare(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

                ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
                String x = "";
                int contor = 0;
                if(rs.next()) {

                    String Veche = ce.getText();
                    if (!Veche.isEmpty()) {


                        Statement statement = con.createStatement();
                        ResultSet resultSet = statement.executeQuery("select COUNT(Ingredient_ID) nr, numePizza from PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT " +
                                " where numePizza = '"+Veche+"' GROUP BY numePizza");
                        int y = 0;
                        if(resultSet.next()) {
                            y =resultSet.getInt("nr");
                        }
                        x = "Sunt " + y + " ingrediente pe pizza " + Veche;
                        Update2.setText(x);

                    } else {
                        x = "trebuie sa scrieti numele unei coloane!";
                        Update2.setText(x);

                    }

                }

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }
    }


    public void afiseaza2(ActionEvent actionEvent) {
        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
            String x = "";
            int contor = 0;
            if(rs.next()) {

                    Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("select COUNT(Ingredient_ID) nr, numePizza from PIZZADANIEL.dbo.VIZ_PIZZA_INGREDIENT " +
                            " GROUP BY numePizza" +
                            " HAVING COUNT(Ingredient_ID) > 6");
                    //int y = 0;
                    while(resultSet.next()) {
                        x += resultSet.getString("numePizza");
                        x += " ";
                    }
                    x += " sunt pizele cu 6 ingrediente cel putin";
                    Update2.setText(x);
            }

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }
    }
}

