package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class LoginController {
    public TextField userInput;
    public PasswordField passInput;
    Connection c = null;

    public void login(){
        //TODO need to be able to input a user and password
        String user = userInput.getText();
        String pass = passInput.getText();
        Connection c = null;

        try {
            //String urlLocal = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=3365db";
            String url = "jdbc:sqlserver://172.26.54.41\\MSSQLSERVER;database=CapitalBeverage";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, user, pass);

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
            e.printStackTrace(); //print the error

            //output an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please check that username and password are correct or contact administrator.");
            alert.showAndWait();
        }

    }
}
