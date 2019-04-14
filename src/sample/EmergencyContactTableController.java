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

public class EmergencyContactTableController {

    public ObservableList<EmployeeEmergencyContact> empContactData = FXCollections.observableArrayList();
    public TableView<EmployeeEmergencyContact> empContactTable;
    public TableColumn<EmployeeEmergencyContact, Number> empContactIDCol;
    public TableColumn<EmployeeEmergencyContact, Number> empFKIDCol;
    public TableColumn<EmployeeEmergencyContact, String> empContactFNameCol;
    public TableColumn<EmployeeEmergencyContact, String> empContactLNameCol;
    public TableColumn<EmployeeEmergencyContact, String> empContactPhoneCol;
    public TableColumn<EmployeeEmergencyContact, String> empContactEmailCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        //assign columns to the property methods in the Vendor class
        empContactIDCol.setCellValueFactory(data -> data.getValue().emergency_contact_idProperty());
        empFKIDCol.setCellValueFactory(data -> data.getValue().fk_employee_idProperty());
        empContactFNameCol.setCellValueFactory(data -> data.getValue().emergency_contact_first_nameProperty());
        empContactLNameCol.setCellValueFactory(data -> data.getValue().emergency_contact_last_nameProperty());
        empContactPhoneCol.setCellValueFactory(data -> data.getValue().emergency_contact_phoneProperty());
        empContactEmailCol.setCellValueFactory(data -> data.getValue().emergency_contact_emailProperty());

        empContactTable.setEditable(true); //so we can edit rows later

        //this try/catch loads data from the database into the tableview
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DBClass.connect();
            String SQL = "Select * from EMPLOYEE_EMERGENCY_CONTACT";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EmployeeEmergencyContact eec = new EmployeeEmergencyContact();

                //set the values for this new Vendor based on what's in the database
                eec.emergency_contact_id.set(rs.getInt("EMERGENCY_CONTACT_ID")); //columnLabel should match column name in database
                eec.fk_employee_id.set(rs.getInt("EMPLOYEE_ID"));
                eec.emergency_contact_first_name.set((rs.getString("EMERGENCY_CONTACT_FIRST_NAME")));
                eec.emergency_contact_last_name.set(rs.getString("EMERGENCY_CONTACT_LAST_NAME"));
                eec.emergency_contact_phone.set((rs.getString("EMERGENCY_CONTACT_PHONE")));
                eec.emergency_contact_email.set(rs.getString("EMERGENCY_CONTACT_EMAIL"));

                empContactData.add(eec); //add the new Vendor to an observable list
            }
            empContactTable.setItems(empContactData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Emergency Contact Data");
        }

    }

    public void editEmpContact(){
        empFKIDCol.setCellFactory(TextFieldTableCell.<EmployeeEmergencyContact, Number>forTableColumn(new NumberStringConverter()));
        empFKIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeEmergencyContact, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_employee_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        empContactFNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empContactFNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeEmergencyContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmergency_contact_first_name(t.getNewValue())
        );

        empContactLNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empContactLNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeEmergencyContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmergency_contact_last_name(t.getNewValue())
        );

        empContactPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empContactPhoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeEmergencyContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmergency_contact_phone(t.getNewValue())
        );

        empContactEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empContactEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeEmergencyContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmergency_contact_email(t.getNewValue())
        );
    }

    public void saveEmpContactChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(empContactTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = empContactTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) empContactIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            Integer empFKIDCell = (Integer) empFKIDCol.getCellObservableValue(row).getValue();
            String contactFNameCell = (String) empContactFNameCol.getCellObservableValue(row).getValue();
            String contactLNameCell = (String) empContactLNameCol.getCellObservableValue(row).getValue();
            String contactPhoneCell = (String) empContactPhoneCol.getCellObservableValue(row).getValue();
            String contactEmailCell = (String) empContactEmailCol.getCellObservableValue(row).getValue();


            PreparedStatement statement = c.prepareStatement("UPDATE EMPLOYEE_EMERGENCY_CONTACT SET EMPLOYEE_ID = ?, " +
                    "EMERGENCY_CONTACT_FIRST_NAME = ?, EMERGENCY_CONTACT_LAST_NAME = ?, EMERGENCY_CONTACT_PHONE = ?, EMERGENCY_CONTACT_EMAIL = ? "
                    + "WHERE EMERGENCY_CONTACT_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, empFKIDCell);
            statement.setString(2, contactFNameCell);
            statement.setString(3, contactLNameCell);
            statement.setString(4, contactPhoneCell);
            statement.setString(5, contactEmailCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteEmpContact() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(empContactTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = empContactTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) empContactIDCol.getCellObservableValue(row).getValue(); //get the id of the selected vendor

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EMPLOYEE_EMERGENCY_CONTACT WHERE EMERGENCY_CONTACT_ID =" + currentID;

            stmt.executeUpdate(SQL);
            empContactData.remove(empContactTable.getSelectionModel().getSelectedIndex()); //update the observable list
            empContactTable.setItems(empContactData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openEmpContactForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Emergency Contact Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Employee Emergency Contact");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEmpTable(){
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
