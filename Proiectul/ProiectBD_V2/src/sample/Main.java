package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));

        Scene scene = new Scene(root, 1540, 800);

        //scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);

        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();

    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//
//        String userName = "guest";
//        String password ="guest";
//
//        String url ="jdbc:sqlserver://DESKTOP-4N9OJGM\\PIZZASQLSERVER;databaseName=PIZZADANIEL";
//
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        Connection conn = null;
//        conn = DriverManager.getConnection(url,userName,password);
//
//        Connection connection = conn;
//
//        Statement statement = connection.createStatement();
//        statement.execute("insert into PIZZADANIEL.dbo.Utilizatori (username,password,nume_id,locatie_id,telefon)" +
//                " VALUES('dani','dani','dani',2,'dani')");
//
//

        launch(args);
    }
}
