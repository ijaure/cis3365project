package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventVenueFormController {

    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public ObservableList<Venue> venueData = FXCollections.observableArrayList();

    public ComboBox<Event> eventList;
    public ComboBox<Venue> venueList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from VENUE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Venue v= new Venue();

                //assign an ID Name from the database
                v.venue_id.set(rs.getInt("VENUE_ID"));
                v.venue_name.set(rs.getString("VENUE_NAME"));

                venueData.add(v); //add these to an observable list
            }
            venueList.setItems(venueData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from EVENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Event ev = new Event();

                //assign an ID Name from the database
                ev.event_id.set(rs.getInt("EVENT_ID"));
                ev.event_name.set(rs.getString("EVENT_NAME"));

                eventData.add(ev); //add these to an observable list
            }
            eventList.setItems(eventData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Event Employee Combobox Data");
        }
    }


    public void addEventVenue() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(eventList.getSelectionModel().isEmpty() ||
                venueList.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer event = eventList.getSelectionModel().getSelectedItem().getEvent_id();
            Integer venue = venueList.getSelectionModel().getSelectedItem().getVenue_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EVENT_VENUE " + "(EVENT_ID, VENUE_ID) "
                    + "VALUES ('" + event + "', '" + venue + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
