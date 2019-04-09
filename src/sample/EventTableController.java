package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventTableController {

    public void initialize(){

    }

    public void editEvent(){

    }

    public void saveEventChanges(){

    }

    public void deleteEvent(){

    }

    public void openEventStatusTable(){

    }

    public void openEventOccuranceTable(){

    }

    public void openEventForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Event");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
