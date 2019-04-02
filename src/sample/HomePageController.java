package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {

    //load the employees table
    public void openEmployees() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //load the clients table
    public void openClients() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ClientTable Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //open vendors table
    public void openVendors() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Vendor Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Vendor Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
