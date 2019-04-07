package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeTableController {

    public void openEmployeeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
