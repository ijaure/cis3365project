package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //loads home page which is temporarily in sample.fxml
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        // receiving Class Not Found Exception,
        // but this works in the Database tools window w/ this URL: jdbc:sqlserver://localhost\\SQLEXPRESS
        // connect via Data Source -> Microsoft SQL Server
        // make sure to select "Use Windows domain authentication"

        /*String url = "jdbc:sqlserver://localhost\\SQLEXPRESS";

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }*/

        launch(args);
    }
}
