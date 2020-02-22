package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class SignUpController implements Initializable {


    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_telefon;
    @FXML
    private TextField tf_nume;
    @FXML
    private TextField tf_prenume;
    @FXML
    private TextField tf_adresa;
    @FXML
    private TextField tf_cartier;
    @FXML
    private TextField tf_strada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void login(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/login.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, 1540, 800));


    }
    @FXML
    public void signup(MouseEvent mouseEvent) throws ClassNotFoundException, SQLException {
           Connection conn = DbConnect.getInstance().getConnection();


        try {

            String username = tf_username.getText();
            String password = tf_password.getText();
            String email = tf_email.getText();
            if(email.isEmpty()){
                email = null;
            }
            String telefon = tf_telefon.getText();
            String nume = tf_nume.getText();
            String prenume = tf_prenume.getText();
            if(prenume.isEmpty()){
                prenume = null;
            }

            String cartier = tf_cartier.getText();
            String strada = tf_strada.getText();
            String adresa = tf_adresa.getText();
            if(adresa.isEmpty()){
                adresa = null;
            }

            Statement statement2 = conn.createStatement();
            String locatie = "SELECT TOP 1 locatie_id FROM PIZZADANIEL.dbo.Locatii " +
                    "ORDER BY locatie_id DESC";
            ResultSet rs = statement2.executeQuery(locatie);
            int locatieID = 1;
            while(rs.next()){
                locatieID = rs.getInt("Locatie_ID") + 1;
            }
            System.out.println(locatieID);

            Statement statement1 = conn.createStatement();
            Statement statement3= conn.createStatement();

            int status = statement1.executeUpdate("insert into PIZZADANIEL.dbo.Utilizatori " +
                    "(Utilizator,Parola,Email,Telefon,Nume,Prenume)" +
                    " VALUES('"+username+"','"+password+"','"+email+"','"+telefon+"'" +
                    ", '"+nume+"', '"+prenume+"')");

            int status_2 = statement3.executeUpdate("insert into PIZZADANIEL.dbo.Locatii " +
                    "(Locatie_ID, Cartier, Strada, Adresa, Utilizator)" +
                    " VALUES('"+locatieID+"', '"+cartier+"', '"+strada+"', '"+adresa+"', '"+username+"')");

            if(status > 0 ) {
                System.out.println("user registered");
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
