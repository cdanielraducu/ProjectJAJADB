package sample.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private DbConnect(){
    }

    public static DbConnect getInstance() {return new DbConnect();}

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String userName = "sa";
        String password ="sa";

        String url ="jdbc:sqlserver://DESKTOP-4N9OJGM\\PIZZASQLSERVER;databaseName=PIZZADANIEL";


        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url,userName,password);


        return conn;
    }
}
