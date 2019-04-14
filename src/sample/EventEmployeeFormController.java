package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventEmployeeFormController {

    public ObservableList<Event> eventsData = FXCollections.observableArrayList();
    public ObservableList<Employee> empData = FXCollections.observableArrayList();

    public ComboBox<Event> eventList;
    public ComboBox<Employee> empList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from EMPLOYEE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Employee e = new Employee();

                //assign an ID Name from the database
                e.employee_id.set(rs.getInt("EMPLOYEE_ID"));
                e.employee_first_name.set(rs.getString("EMPLOYEE_FIRST_NAME"));
                e.employee_last_name.set(rs.getString("EMPLOYEE_LAST_NAME"));

                empData.add(e); //add these to an observable list
            }
            empList.setItems(empData); //set the ComboBox values to the observable list
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

                eventsData.add(ev); //add these to an observable list
            }
            eventList.setItems(eventsData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Event Employee Combobox Data");
        }
    }


    public void addEventEmployee() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(eventList.getSelectionModel().isEmpty() ||
                empList.getSelectionModel().isEmpty())
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
            Integer emp = empList.getSelectionModel().getSelectedItem().getEmployee_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EVENT_EMPLOYEE " + "(EVENT_ID, EMPLOYEE_ID) "
                    + "VALUES ('" + event + "', '" + emp + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
