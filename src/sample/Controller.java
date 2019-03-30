package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TODO Initialize the all tables data, maybe use a local database to test everything
//TODO Open Forms or other tables from the Menu buttons in each table, such as with openEmployeeForm()
//this controller is for the tables
public class Controller {

    //initialize the tables
    public void initialize()
    {

    }

    //open the employees form from the "New Employee" button in employees table
    public void openEmployeeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Employee Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Employee");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //open client from from Clients table
    public void openClientForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Client Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Client");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
