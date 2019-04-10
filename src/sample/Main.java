package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login ver 1.fxml")); //loads home page which is temporarily in sample.fxml
        primaryStage.setTitle("Login");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // NOTE: Copy the sqljdbc_auth.dll from the jdbcjar folder in the project
        // and paste it to your JDK bin in C:\Program Files\Java\jdk-11.0.2\bin
        // this is to allow Windows authentication to work so you don't have to use a username & password
        // Also, make sure you add a path in Project Structure -> Libraries to the jdbcjar folder



        launch(args);
    }
}
