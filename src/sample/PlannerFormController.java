package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class PlannerFormController {
    public ObservableList<Venue> venueIDData = FXCollections.observableArrayList();
    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public TextField plannerFirstNameInput;
    public TextField plannerLastNameInput;
    public TextField plannerPhoneInput;
    public TextField plannerEmailInput;
    public CheckBox isClientInput;

    public ComboBox<Venue> venueIDList;
    public ComboBox<Event> plannerEventList;

    public void initialize() {
        Connection c;
        try {
            c = DBClass.connect();
            String SQL = "SELECT * from VENUE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while (rs.next()) {
                Venue venue = new Venue();

                //assign an ID Name from the database
                venue.venue_id.set(rs.getInt("VENUE_ID"));
                venue.venue_name.set(rs.getString("VENUE_NAME"));

                venueIDData.add(venue); //add these to an observable list
            }
            venueIDList.setItems(venueIDData); //set the ComboBox values to the observable list
            c.close();
        } catch (Exception e) { //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Venue Planner Combobox Data");
        }

        try {
            c = DBClass.connect();
            String SQL = "SELECT * from EVENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while (rs.next()) {
                Event e = new Event();

                //assign an ID Name from the database
                e.event_id.set(rs.getInt("EVENT_ID"));
                e.event_name.set(rs.getString("EVENT_NAME"));

                eventData.add(e); //add these to an observable list
            }
            plannerEventList.setItems(eventData); //set the ComboBox values to the observable list
            c.close();
        } catch (Exception e) { //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Event Planner Combobox Data");
        }

    }

    public void addPlanner() throws SQLException, ParseException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if (plannerFirstNameInput.getText().trim().isEmpty() ||
                plannerLastNameInput.getText().trim().isEmpty() ||
                plannerEmailInput.getText().trim().isEmpty() ||
                plannerPhoneInput.getText().trim().isEmpty() ||
                venueIDList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else{
            String PlannerFirst = plannerFirstNameInput.getText();
            String PlannerLast = plannerLastNameInput.getText();

            String PlannerPhone = plannerPhoneInput.getText();
            String PlannerEmail = plannerEmailInput.getText();
            Integer venueID=venueIDList.getSelectionModel().getSelectedItem().getVenue_id();
            Integer eventID = plannerEventList.getSelectionModel().getSelectedItem().getEvent_id();

            Boolean isclient=false;
            if(isClientInput.isSelected()){
                isclient=true;
            }

            String SQL= "INSERT INTO PLANNER "+ "(PLANNER_FIRST_NAME, PLANNER_LAST_NAME,PLANNER_PHONE,PLANNER_EMAIL,IS_CLIENT,FK_VENUE_ID)" +"VALUES('"+PlannerFirst +"', '"+PlannerLast +"', '"+ PlannerPhone +"', '"+PlannerEmail +"', '"+isclient+"','"+venueID+"'); " +
                    "DECLARE @planners_id int" + " SET @planners_id = @@IDENTITY;";

            String SQL2 = " INSERT INTO EVENT_PLANNER(FK_PLANNER_ID, FK_EVENT_ID) " +
                    "VALUES(@planners_id, " + eventID + ");";

            stmt.addBatch(SQL); //execute the sql statement
            stmt.addBatch(SQL2);
            stmt.executeBatch();
            c.close(); //close the connection
        }
    }
}
