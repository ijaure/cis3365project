package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventPlannerFormController {

    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public ObservableList<Planner> plannerData = FXCollections.observableArrayList();

    public ComboBox<Event> eventList;
    public ComboBox<Planner> plannerList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PLANNER";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Planner p = new Planner();

                //assign an ID Name from the database
                p.planner_id.set(rs.getInt("PLANNER_ID"));
                p.planner_first_name.set(rs.getString("PLANNER_FIRST_NAME"));
                p.planner_last_name.set(rs.getString("PLANNER_LAST_NAME"));

                plannerData.add(p); //add these to an observable list
            }
            plannerList.setItems(plannerData); //set the ComboBox values to the observable list
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
            System.out.println("Error on Building Combobox Data");
        }
    }


    public void addEventPlanner() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(eventList.getSelectionModel().isEmpty() ||
                plannerList.getSelectionModel().isEmpty())
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
            Integer planner = plannerList.getSelectionModel().getSelectedItem().getPlanner_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EVENT_PLANNER " + "(PLANNER_ID, EVENT_ID) "
                    + "VALUES ('" + planner + "', '" + event + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
