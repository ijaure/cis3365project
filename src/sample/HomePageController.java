package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {

    //load the employees table
    public void openEmployees() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Employee Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //load the clients table
    public void openClients() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientTable Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
