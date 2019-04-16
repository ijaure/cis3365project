package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientEventFormController {

    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public ObservableList<Client> clientData = FXCollections.observableArrayList();

    public ComboBox<Event> eventList;
    public ComboBox<Client> clientList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Client cl = new Client();

                //assign an ID Name from the database
                cl.client_id.set(rs.getInt("CLIENT_ID"));
                cl.client_fname.set(rs.getString("CLIENT_FNAME"));
                cl.client_lname.set(rs.getString("CLIENT_LNAME"));

                clientData.add(cl); //add these to an observable list
            }
            clientList.setItems(clientData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Event Employee Combobox Data");
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


    public void addClientEvent() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(eventList.getSelectionModel().isEmpty() ||
                clientList.getSelectionModel().isEmpty())
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
            Integer emp = clientList.getSelectionModel().getSelectedItem().getClient_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO CLIENT_EVENT " + "(CLIENT_ID, EVENT_ID) "
                    + "VALUES ('" + emp + "', '" + event + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
