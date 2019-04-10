package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventFormController {

    public ObservableList<EventOccurance> eventOccurData = FXCollections.observableArrayList();
    public ObservableList<Venue> eventVenueData = FXCollections.observableArrayList();
    public ObservableList<Client> eventClientData = FXCollections.observableArrayList();
    public ObservableList<Note> eventNoteData = FXCollections.observableArrayList();
    public ObservableList<EventStatus> eventStatusData = FXCollections.observableArrayList();

    public ComboBox<EventOccurance> eventOccurList;
    public ComboBox<Venue> eventVenueList;
    public ComboBox<Client> eventClientList;
    public ComboBox<Note> eventNoteList;
    public ComboBox<EventStatus> eventStatusList;

    public TextField eventNameInput;
    public TextField startDateInput;
    public TextField projEndDateInput;
    public TextField actEndDateInput;
    public TextField contactFNameInput;
    public TextField contactLNameInput;
    public TextField phoneInput;
    public TextField emailInput;
    public TextField billAddrInput;

    public void initialize(){
        Connection c;

        //these try/catch statements load data into the appropriate drop-down lists
        try{
            c = DBClass.connect();
            String SQL = "SELECT * from EVENT_OCCURANCE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                EventOccurance eo = new EventOccurance();

                //assign an ID and Status Name from the database
                eo.event_occurance_id.set(rs.getInt("EVENT_OCCURANCE_ID"));
                eo.event_occurance_name.set(rs.getString("EVENT_OCCURANCE_NAME"));

                eventOccurData.add(eo); //add these to an observable list
            }
            eventOccurList.setItems(eventOccurData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Event Occurance Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from VENUE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Venue v = new Venue();

                //assign an ID and Status Name from the database
                v.venue_id.set(rs.getInt("VENUE_ID"));
                v.venue_name.set(rs.getString("VENUE_NAME"));

                eventVenueData.add(v); //add these to an observable list
            }
            eventVenueList.setItems(eventVenueData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building (Event) Venue Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Client ec = new Client();

                //assign an ID and Status Name from the database
                ec.client_id.set(rs.getInt("CLIENT_ID"));
                ec.client_fname.set(rs.getString("CLIENT_FNAME"));
                ec.client_lname.set(rs.getString("CLIENT_LNAME"));

                eventClientData.add(ec); //add these to an observable list
            }
            eventClientList.setItems(eventClientData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building (Event) Client Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from NOTE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Note n = new Note();

                //assign an ID and Status Name from the database
                n.note_id.set(rs.getInt("NOTE_ID"));
                n.note_description.set(rs.getString("NOTE_DESCRIPTION"));

                eventNoteData.add(n); //add these to an observable list
            }
            eventNoteList.setItems(eventNoteData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building (Event) Client Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from EVENT_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                EventStatus es = new EventStatus();

                //assign an ID and Status Name from the database
                es.event_status_id.set(rs.getInt("EVENT_STATUS_ID"));
                es.event_status_name.set(rs.getString("EVENT_STATUS_NAME"));

                eventStatusData.add(es); //add these to an observable list
            }
            eventStatusList.setItems(eventStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building (Event) Status Data");
        }

    }

    public void addEvent() throws SQLException, ParseException {
        Connection conn = DBClass.connect();
        Statement stmt = conn.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy"); //used the parse the textfield input to a Date


        //TODO don't check for fields that are optional in form controllers
        if(eventNameInput.getText().trim().isEmpty() ||
                projEndDateInput.getText().trim().isEmpty() ||
                actEndDateInput.getText().trim().isEmpty() ||
                startDateInput.getText().trim().isEmpty() ||
                billAddrInput.getText().trim().isEmpty() ||
                contactFNameInput.getText().trim().isEmpty() ||
                contactLNameInput.getText().trim().isEmpty() ||
                emailInput.getText().trim().isEmpty() ||
                phoneInput.getText().trim().isEmpty() ||
                eventStatusList.getSelectionModel().isEmpty() ||
                eventClientList.getSelectionModel().isEmpty() ||
                eventOccurList.getSelectionModel().isEmpty() ||
                eventNoteList.getSelectionModel().isEmpty() ||
                eventVenueList.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String eventName = eventNameInput.getText();
            Integer eventVenue = eventVenueList.getSelectionModel().getSelectedItem().getVenue_id();
            Integer eventClient = eventClientList.getSelectionModel().getSelectedItem().getClient_id();
            String billAddr = billAddrInput.getText();
            String fName = contactFNameInput.getText();
            String lName = contactLNameInput.getText();
            String phone = phoneInput.getText();
            String email = emailInput.getText();

            java.util.Date startDate = formatter.parse(startDateInput.getText());
            java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());

            java.util.Date projDate = formatter.parse(projEndDateInput.getText());
            java.sql.Date projDateSQL = new java.sql.Date(projDate.getTime());

            java.util.Date actEndDate = formatter.parse(actEndDateInput.getText());
            java.sql.Date actEndDateSQL = new java.sql.Date(actEndDate.getTime());

            Integer eventStatus = eventStatusList.getSelectionModel().getSelectedItem().getEvent_status_id();
            Integer eventOccur = eventOccurList.getSelectionModel().getSelectedItem().getEvent_occurance_id();
            Integer eventNote = eventNoteList.getSelectionModel().getSelectedItem().getNote_id();

            String sqlStatement = "INSERT INTO EVENT" + "(EVENT_NAME, FK_VENUE_ID, FK_CLIENT_ID, BILLING_ADDRESS, EVENT_CONTACT_FIRST, EVENT_CONTACT_LAST, EVENT_PHONE, EVENT_EMAIL, START_DATE, PROJ_END_DATE, ACT_END_DATE, FK_EVENT_STATUS, FK_EVENT_OCCURANCE_ID, FK_EVENT_NOTE_ID)" +
                    " VALUES ('" + eventName + "', '" + eventVenue + "', '" + eventClient + "', '" + billAddr + "', '" + fName + "', '" + lName + "', '" + phone + "', '" + email + "', '" + startDateSQL + "', '" + projDateSQL + "', '" + actEndDateSQL + "', '" + eventStatus + "', '" + eventOccur + "', '" + eventNote + "')";

            //If statement for validations before submission
            stmt.executeUpdate(sqlStatement);

            conn.close();
        }

    }
}
