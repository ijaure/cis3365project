package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.sql.*;

public class EventEmployeeTableController {

    public ObservableList<EventEmployee> eeData = FXCollections.observableArrayList();
    public TableView<EventEmployee> eeTable;
    public TableColumn<EventEmployee, Number> eeIDCol;
    public TableColumn<EventEmployee, Number> eventIDCol;
    public TableColumn<EventEmployee, Number> empIDCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        eeIDCol.setCellValueFactory(data -> data.getValue().event_employee_idProperty());
        eventIDCol.setCellValueFactory(data -> data.getValue().fk_event_idProperty());
        empIDCol.setCellValueFactory(data -> data.getValue().fk_employee_idProperty());

        eeTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_EMPLOYEE";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventEmployee ee = new EventEmployee();

                //set the values based on what's in the database
                ee.event_employee_id.set(rs.getInt("EVENT_EMPLOYEE_ID")); //columnLabel should match column name in database
                ee.fk_event_id.set(rs.getInt("FK_EVENT_ID"));
                ee.fk_employee_id.set((rs.getInt("FK_EMPLOYEE_ID")));

                eeData.add(ee); //add to an observable list
            }
            eeTable.setItems(eeData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Employee Data");
        }

    }

    public void editEventEmployee(){
        eventIDCol.setCellFactory(TextFieldTableCell.<EventEmployee, Number>forTableColumn(new NumberStringConverter()));
        eventIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventEmployee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        empIDCol.setCellFactory(TextFieldTableCell.<EventEmployee, Number>forTableColumn(new NumberStringConverter()));
        empIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventEmployee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_employee_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );


    }

    public void saveEventEmployeeChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(eeTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = eeTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) eeIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer eventCell = (Integer) eventIDCol.getCellObservableValue(row).getValue();
            Integer empCell = (Integer) empIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_EMPLOYEE SET FK_EVENT_ID = ?, " +
                    "FK_EMPLOYEE_ID = ? "
                    + "WHERE EVENT_EMPLOYEE_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, eventCell);
            statement.setInt(2, empCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteEventEmployee() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(eeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = eeTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) eeIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EVENT_EMPLOYEE WHERE EVENT_EMPLOYEE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            eeData.remove(eeTable.getSelectionModel().getSelectedIndex()); //update the observable list
            eeTable.setItems(eeData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openEventEmployeeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Employee Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Employee to Event");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEventTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEmployeeTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Table Updated.fxml"));
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
