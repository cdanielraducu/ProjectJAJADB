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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CosController implements Initializable {

    @FXML
    private TableView<ModelTableApp> tabel;
    @FXML
    private TableColumn<ModelTableApp, Integer> col_ID;
    @FXML
    private TableColumn<ModelTableApp, String> col_NumePizza;
    @FXML
    private TableColumn<ModelTableApp, String> col_Pret;
    @FXML
    private TableColumn<ModelTableApp, Integer> col_Cantitate;
    @FXML
    private TableColumn<ModelTableApp, String> col_Imagine;

    @FXML
    private Label PretTotal;

    @FXML
    private TableView<ModelTableApp> tabelLocatii;
    @FXML
    private TableColumn<ModelTableApp, Integer> loc_ID;
    @FXML
    private TableColumn<ModelTableApp, String> cartier;
    @FXML
    private TableColumn<ModelTableApp, String> strada;
    @FXML
    private TableColumn<ModelTableApp, String> adresa;
    @FXML
    private Label Update;
    ObservableList<ModelTableApp> oblist = FXCollections.observableArrayList();
    ObservableList<ModelTableApp> locatiiList = FXCollections.observableArrayList();
    public void goToMenu(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/app.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cos cos = new Cos();

        try {
            Connection con = DbConnect.getInstance().getConnection();

            Utilizator utilizator = new Utilizator();
            String username = utilizator.getNume();

            Statement statement = con.createStatement();
            ResultSet locatiiResult = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii " +
                    "where Utilizator = '"+username+"'" );
            while(locatiiResult.next()){
                System.out.println("a");
                int id = locatiiResult.getInt("Locatie_ID");
                String cartier = locatiiResult.getString("Cartier");
                String strada = locatiiResult.getString("Strada");
                String adresa = locatiiResult.getString("Adresa");

                ModelTableApp modelTableApp = new ModelTableApp(id, cartier,strada,adresa);
                locatiiList.add(modelTableApp);
                tabelLocatii.setItems(locatiiList);
            }

            tabelLocatii.setItems(locatiiList);


            oblist.addAll(cos.getCoslist());
            for(int i = 0; i< oblist.size(); i++){
                if(oblist.get(i).getCantitate() == 0){
                oblist.get(i).setCantitate(1);
                }
            }
            tabel.setItems(oblist);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        loc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cartier.setCellValueFactory(new PropertyValueFactory<>("cartier"));
        adresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        strada.setCellValueFactory(new PropertyValueFactory<>("strada"));

        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_NumePizza.setCellValueFactory(new PropertyValueFactory<>("numePizza"));
        col_Pret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        col_Imagine.setPrefWidth(200);
        col_Imagine.setCellValueFactory(new PropertyValueFactory<>("imagine"));

        List<Integer> pos = new ArrayList<>();
        for(int i = 0; i< oblist.size(); i++){

            if(i != 0){
                int ok = 0;
                for(int z = 0; z < pos.size(); z++){
                    if(i == pos.get(z)) {
                        ok = 1;
                        break;
                    }
                }
                if(ok == 1){
                    continue;
                }
            }
            for(int j = i+1; j < oblist.size(); j++){
                if(oblist.get(i).getNumePizza().equals(oblist.get(j).getNumePizza())){
                    oblist.get(i).setCantitate(oblist.get(i).getCantitate() + 1);
                    pos.add(j);
                }
            }
        }
        for(int i = 0; i<pos.size(); i++){
            System.out.println(pos.get(i));
            oblist.remove((int)pos.get(i));
            tabel.setItems(oblist);
            cos.setCos(oblist);
        }
        col_Cantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));


        Integer sum = 0;
        String suma = "Suma totala de platit este: ";

        for(int i = 0; i<oblist.size(); i++){

            sum += oblist.get(i).getPret() * oblist.get(i).getCantitate();
        }

        PretTotal.setText(sum.toString());
        Utilizator utilizator = new Utilizator(sum);

    }

    public void finalizareaComanda(ActionEvent actionEvent) {

        Comanda comandaFinala = new Comanda();

        if(comandaFinala.getLocatie() != 0) {

            try {
                Connection con = DbConnect.getInstance().getConnection();


                Utilizator utilizator = new Utilizator();


                String utilizatorDB = utilizator.getNume();
                int costDB = utilizator.getTotalPlata();

                String livratorRandom = "";


                int x = (int) getRandomIntegerBetweenRange(1, 5);
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from PIZZADANIEL.dbo.Livratori " +
                        " where Livrator_ID = '" + x + "'");

                if (resultSet.next()) {
                    livratorRandom = resultSet.getString("Nume");
                }

                comandaFinala.setLivrator(livratorRandom);

                Statement statement2 = con.createStatement();
                String comanda = "SELECT TOP 1 comanda_id FROM PIZZADANIEL.dbo.Comenzi " +
                        "ORDER BY comanda_id DESC";
                ResultSet rs = statement2.executeQuery(comanda);
                int comandaID = 1;
                while (rs.next()) {
                    comandaID = rs.getInt("Comanda_ID") + 1;
                }
                System.out.println(comandaID);

                Statement statement1 = con.createStatement();

                int status = statement1.executeUpdate("insert into PIZZADANIEL.dbo.Comenzi " +
                        "(Comanda_ID,Utilizator,Cost,Livrator_ID)" +
                        " VALUES('" + comandaID + "','" + utilizatorDB + "','" + costDB + "','" + x + "'" + ")");

                for (int i = 0; i < oblist.size(); i++) {

                    int PizzaID = oblist.get(i).getId();
                    int cantitate = oblist.get(i).getCantitate();
                    Statement statement3 = con.createStatement();
                    int status2 = statement3.executeUpdate("insert into PIZZADANIEL.dbo.IstoricComenzi " +
                            "(Comanda_ID, Pizza_ID, Pizza_Cantitate) " + " VALUES('" + comandaID + "','" + PizzaID + "','" + cantitate + "')");

                }
                if (status > 0) {
                    System.out.println("comanda registered");
                }

                con.commit();

                tabel.setItems(oblist);

                Update.setText("");
                Cos cosStergere = new Cos();
                cosStergere.getCoslist().clear();
                PretTotal.setText("");
                oblist.clear();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        } else {
            Update.setText("Nu ati ales o locatie la care sa va livram comanda!");
        }

    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    public void alegeLocatia(ActionEvent actionEvent) {

        int locatie_id = (tabelLocatii.getSelectionModel().getSelectedItem().getId());
        Comanda comanda = new Comanda(locatie_id);

        Update.setText(locatie_id + " e aleasa!");

    }
}
