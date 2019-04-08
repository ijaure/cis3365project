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

import java.sql.*;

public class EmployeeStatusTableController {

    public ObservableList<EmployeeStatus> empStatusData = FXCollections.observableArrayList();
    public TableView<EmployeeStatus> empStatusTable;
    public TableColumn<EmployeeStatus, Number> empStatusIDCol;
    public TableColumn<EmployeeStatus, String> empStatusNameCol;

    public void initialize(){
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        empStatusIDCol.setCellValueFactory(data -> data.getValue().employee_status_idProperty());
        empStatusNameCol.setCellValueFactory(data -> data.getValue().employee_status_nameProperty());

        empStatusTable.setEditable(true);

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from EMPLOYEE_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EmployeeStatus es = new EmployeeStatus();

                //set the values for this new Vendor based on what's in the database
                es.employee_status_id.set(rs.getInt("EMPLOYEE_STATUS_ID")); //columnLabel should match column name in database
                es.employee_status_name.set(rs.getString("EMPLOYEE_STATUS_NAME"));

                empStatusData.add(es); //add the new Vendor to an observable list
            }
            empStatusTable.setItems(empStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Employee Status Data");
        }

    }

    public void editEmpStatus(){
        empStatusNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empStatusNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeStatus, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_status_name(t.getNewValue())
        );

    }

    public void saveEmpStatusChanges() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);

        if(empStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = empStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) empStatusIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String empStatusNameCell = (String) empStatusNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EMPLOYEE_STATUS SET EMPLOYEE_STATUS_NAME = ? "
                    + "WHERE EMPLOYEE_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, empStatusNameCell);
            statement.execute();
        }

    }

    public void deleteEmpStatus() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        if(empStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = empStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) empStatusIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EMPLOYEE_STATUS WHERE EMPLOYEE_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            empStatusData.remove(empStatusTable.getSelectionModel().getSelectedIndex()); //update the observable list
            empStatusTable.setItems(empStatusData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openNewStatusForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Status Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Status");
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
