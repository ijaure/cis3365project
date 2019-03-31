package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //loads home page which is temporarily in sample.fxml
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // NOTE: Copy the sqljdbc_auth.dll from the jdbcjar folder in the project
        // and paste it to your JDK bin in C:\Program Files\Java\jdk-11.0.2\bin
        // this is to allow Windows authentication to work so you don't have to use a username & password
        // Also, make sure you add a path in Project Structure -> Libraries to the jdbcjar folder

        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);

            if(conn != null) //if it connected successfully
            {
                System.out.println("Connection successful");
            }

            conn.close();
        }
        catch (Exception e) //catch any exceptions
        {
            e.printStackTrace();
        }



        launch(args);
    }
}
