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
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=3365db";
        //URL FOR ACTUAL SERVER: jdbc:sqlserver://172.26.54.41\MSSQLSERVER;database=CapitalBeverage
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, "sa", "1234");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return c;
    }
}
