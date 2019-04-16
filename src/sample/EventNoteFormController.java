package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventNoteFormController {

    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public ObservableList<Note> noteData = FXCollections.observableArrayList();

    public ComboBox<Event> eventList;
    public ComboBox<Note> noteList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from NOTE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
               Note n = new Note();

                //assign an ID Name from the database
                n.note_id.set(rs.getInt("NOTE_ID"));
                n.note_description.set(rs.getString("NOTE_DESCRIPTION"));

                noteData.add(n); //add these to an observable list
            }
            noteList.setItems(noteData); //set the ComboBox values to the observable list
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


    public void addEventNote() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(eventList.getSelectionModel().isEmpty() ||
                noteList.getSelectionModel().isEmpty())
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
            Integer emp = noteList.getSelectionModel().getSelectedItem().getNote_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EVENT_NOTE " + "(EVENT_ID, NOTE_ID) "
                    + "VALUES ('" + event + "', '" + emp + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
