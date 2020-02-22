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

public class ContController implements Initializable {

    @FXML
    private TableView<ModelTableApp> tabel;
    @FXML
    private TableColumn<ModelTableApp, String> col_ID;
    @FXML
    private TableColumn<ModelTableApp, Integer> col_Detalii;
    @FXML
    private Label Update;
    @FXML
    private Button butonDetalii;
    @FXML
    private Button schimba;
    @FXML
    private Button edit;
    @FXML
    private Button sterge;
    @FXML
    private Button insert;
    @FXML
    private TextField ceSeSchimba;
    @FXML
    private TextField cuCeSeSchimba;


    ObservableList<ModelTableApp> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Connection con = DbConnect.getInstance().getConnection();

            tabel.setItems(oblist);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Detalii.setCellValueFactory(new PropertyValueFactory<>("detalii"));

    }



    public void goToMenu(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/app.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);

    }

    public void showComenzi(MouseEvent mouseEvent) {
        oblist.clear();
        tabel.setItems(oblist);
        butonDetalii.setOpacity(100);
        Update.setOpacity(100);
        schimba.setOpacity(0);
        ceSeSchimba.setOpacity(0);
        cuCeSeSchimba.setOpacity(0);
        edit.setOpacity(0);
        sterge.setOpacity(0);
        insert.setOpacity(0);
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
        col_Detalii.setCellValueFactory(new PropertyValueFactory<>("detalii"));


    }


    public void showDetalii(ActionEvent actionEvent) {



        ModelTableApp modelTableApp = tabel.getSelectionModel().getSelectedItem();

        try{
            Connection con = DbConnect.getInstance().getConnection();

            int id = modelTableApp.getId();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.IstoricComenzi where Comanda_ID = '"+id+"'");
            String x = "";
            int contor = 0;
            while(rs.next()){
                contor++;
                int pizza = rs.getInt("Pizza_ID");
                int cantitate = rs.getInt("Pizza_Cantitate");
                ResultSet r = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Pizza where Pizza_ID = '"+pizza+"'");
                String numePizza = "";
                if(r.next()) {
                    numePizza = r.getString("Nume");
                }

                if(contor == 5){
                    x += "\n";
                }

                x += numePizza + " " + cantitate + "(cant)  ";

            }
            Update.setText(x);

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }


    }

    public void showLocatii(MouseEvent mouseEvent) {
        Update.setOpacity(0);
        butonDetalii.setOpacity(0);
        schimba.setOpacity(0);
        ceSeSchimba.setOpacity(100);
        cuCeSeSchimba.setOpacity(100);
        edit.setOpacity(100);
        sterge.setOpacity(100);
        insert.setOpacity(100);
        Update.setText("");
        oblist.clear();
        tabel.setItems(oblist);

        try {
            Connection con = DbConnect.getInstance().getConnection();

            Utilizator utilizator = new Utilizator();
            String utilizatorDB = utilizator.getNume();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+utilizatorDB+"'");

            while(rs.next()) {
                int id = rs.getInt("Locatie_ID");

                String Cartier = rs.getString("Cartier");
                String Strada = rs.getString("Strada");
                String Adresa = rs.getString("Adresa");

                String detalii = "Cartier: " + Cartier + " Strada: " + Strada + " Adresa: " + Adresa;
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
        col_Detalii.setCellValueFactory(new PropertyValueFactory<>("detalii"));


    }

    public void showDetaliiCont(MouseEvent mouseEvent) {

        Update.setOpacity(100);
        butonDetalii.setOpacity(0);
        schimba.setOpacity(100);
        ceSeSchimba.setOpacity(100);
        cuCeSeSchimba.setOpacity(100);
        edit.setOpacity(0);
        sterge.setOpacity(0);
        insert.setOpacity(0);
        Update.setText("");
        oblist.clear();
        tabel.setItems(oblist);

        try {
            Connection con = DbConnect.getInstance().getConnection();

            Utilizator utilizator = new Utilizator();
            String utilizatorDB = utilizator.getNume();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Utilizatori where Utilizator = '"+utilizatorDB+"'");

            while(rs.next()) {


                String parola = rs.getString("Parola");
                String nume = rs.getString("Nume");
                String prenume = rs.getString("Prenume");
                String email = rs.getString("Email");
                String telefon = rs.getString("Telefon");

                String detalii = "parola: " + parola + "\t nume: " + nume + "\t prenume: " + prenume + "\t email: " + email + "\t telefon: " + telefon;
                ModelTableApp modelTableApp = new ModelTableApp(utilizatorDB,detalii);
                oblist.add(modelTableApp);
            }
            tabel.setItems(oblist);

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        col_ID.setCellValueFactory(new PropertyValueFactory<>("numeUtilizator"));
        col_Detalii.setCellValueFactory(new PropertyValueFactory<>("detalii"));

    }


    public void schimba(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Utilizatori where Utilizator = '"+nume+"'");
            String x = "";
            int contor = 0;
            if(rs.next()) {

                String Veche = ceSeSchimba.getText();
                if (!Veche.isEmpty()) {

                    String Noua = cuCeSeSchimba.getText();

                    if(Noua.isEmpty()){
                        Noua = null;
                    }
                    Statement statement = con.createStatement();
                    statement.executeUpdate("UPDATE PIZZADANIEL.dbo.Utilizatori SET " + Veche + " = '" + Noua + "'" +
                            " WHERE Utilizator = '" + nume + "'");

                    x = "Ati schimbat: " + Veche + " cu " + Noua + "!";

                    Update.setText(x);

                } else {
                    x = "trebuie sa scrieti numele unei coloane!";
                    Update.setText(x);

                }

            }

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }

    }

    public void goToAdmin(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/Admin.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);


    }

    public void editeaza(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

            int idLocatie = tabel.getSelectionModel().getSelectedItem().getId();
            if(idLocatie != 0){
            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
            String x = "";
            int contor = 0;
            if(rs.next()) {

                String Veche = ceSeSchimba.getText();
                if (!Veche.isEmpty()) {

                    String Noua = cuCeSeSchimba.getText();

                    if (Noua.isEmpty()) {
                        Noua = null;
                    }
                    Statement statement = con.createStatement();
                    statement.executeUpdate("UPDATE PIZZADANIEL.dbo.Locatii SET " + Veche + " = '" + Noua + "'" +
                            " WHERE Utilizator = '" + nume + "' and Locatie_ID = " + idLocatie + "");

                    x = "Ati schimbat: " + Veche + " cu " + Noua + "!";

                    Update.setText(x);

                } else {
                    x = "trebuie sa scrieti numele unei coloane!";
                    Update.setText(x);

                }
            } else {
                x = "Selectati o locatie!";
                Update.setText(x);
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

            int idLocatie = tabel.getSelectionModel().getSelectedItem().getId();
            if(idLocatie != 0){
                ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
                String x = "";
                int contor = 0;
                if(rs.next()) {
                        Statement statement = con.createStatement();
                        statement.executeUpdate("DELETE FROM PIZZADANIEL.dbo.Locatii" +
                                " where Locatie_ID = "+idLocatie+"");

                        x = "Ati sters: " + idLocatie + "!";

                        Update.setText(x);

                } else {
                    x = "Selectati o locatie!";
                    Update.setText(x);
                }

            }

        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }

    }

    public void insereaza(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();


                ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
                String x = "";
                int contor = 0;

            Statement statement2 = con.createStatement();
            String locatie = "SELECT TOP 1 locatie_id FROM PIZZADANIEL.dbo.Locatii " +
                    "ORDER BY locatie_id DESC";
            ResultSet rs2 = statement2.executeQuery(locatie);
            int locatieID = 1;
            while(rs2.next()){
                locatieID = rs2.getInt("Locatie_ID") + 1;
            }
            System.out.println(locatieID);
            String def = "default";
                if(rs.next()) {
                    Statement statement = con.createStatement();
                    statement.executeUpdate("INSERT INTO PIZZADANIEL.dbo.Locatii(Locatie_ID,Cartier,Strada,Adresa, Utilizator)" +
                            " VALUES("+locatieID+", '"+def+"', '"+def+"', '"+null+"', '"+nume+"')");

                    x = "Ati introdus locatia: " + locatieID + "!";

                    Update.setText(x);

                }


        } catch (ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }

    }

    public void stergeCont(ActionEvent actionEvent) {

        try{
            Connection con = DbConnect.getInstance().getConnection();
            Utilizator utilizator = new Utilizator();
            String nume = utilizator.getNume();

                ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Locatii where Utilizator = '"+nume+"'");
                String x = "";
                int contor = 0;
                if(rs.next()) {

                    Statement statement2 = con.createStatement();
                    ResultSet r = con.createStatement().executeQuery("SELECT * from PIZZADANIEL.dbo.Comenzi " +
                            "where Utilizator = '"+nume+"'");



                    while(r.next()){
                        int user = r.getInt("Comanda_ID");
                        statement2.executeUpdate("DELETE FROM PIZZADANIEL.dbo.IstoricComenzi " +
                                "where Comanda_ID = '"+user+"'");

                    }
                    Statement statement = con.createStatement();
                    statement.executeUpdate("DELETE FROM PIZZADANIEL.dbo.Utilizatori" +
                            " where Utilizator = '"+nume+"'");

                    Parent root = FXMLLoader.load(getClass().getResource("/sample/views/login.fxml"));

                    Node node = (Node) actionEvent.getSource();

                    Stage stage  = (Stage) node.getScene().getWindow();

                    Scene scene = new Scene(root, 1540, 800);

                    stage.setScene(scene);

                } else {
                    x = "Selectati o locatie!";
                    Update.setText(x);
                }

        } catch (ClassNotFoundException | SQLException | IOException e ){
            e.printStackTrace();
        }

    }
}
