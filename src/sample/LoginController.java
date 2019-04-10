package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class LoginController {
    public TextField userInput;
    public TextField passInput;
    Connection c = null;

    public void login(){
        //TODO need to be able to input a user and password
        String user = userInput.getText();
        String pass = passInput.getText();
        try {
            c = DBClass.connect();

            if(c != null) //if it connected successfully
            {
                System.out.println("Connection successful");
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/sample.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Home Page");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            c.close();
        }
        catch (Exception e) //catch any exceptions
        {
            e.printStackTrace();
        }

    }
}
