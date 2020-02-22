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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    public void login(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {


        String username, password;

        username = tf_username.getText();
        password = tf_password.getText();

        Connection connection = DbConnect.getInstance().getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select Utilizator, Parola from PIZZADANIEL.dbo.Utilizatori " +
                " where Utilizator = '"+username+"' and  Parola = '"+password+"'");

        Utilizator utilizator = new Utilizator(username);

        if(resultSet.next()){
            Parent root = FXMLLoader.load(getClass().getResource("/sample/views/app.fxml"));

            Node node = (Node) mouseEvent.getSource();

            Stage stage  = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root,1540,800));
        }
    }
    @FXML
    public void signup(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/signup.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root,1540,800);
        stage.setScene(scene);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
