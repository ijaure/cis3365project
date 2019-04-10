package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBClass {

    public static Connection connect(){
        Connection c = null;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS";
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url,"rache","1234");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return c;
    }
}
