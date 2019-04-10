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

public class EmployeeTypeTableController {

    public ObservableList<EmployeeType> empTypeData = FXCollections.observableArrayList();

    public TableView<EmployeeType> empTypeTable;
    public TableColumn<EmployeeType, Number> empTypeIDCol;
    public TableColumn<EmployeeType, String> empTypeNameCol;
    public TableColumn<EmployeeType, String> empTypeDescCol;

    public void initialize(){
        Connection c;

        empTypeIDCol.setCellValueFactory(data -> data.getValue().employee_type_idProperty());
        empTypeNameCol.setCellValueFactory(data -> data.getValue().position_nameProperty());
        empTypeDescCol.setCellValueFactory(data -> data.getValue().position_descriptionProperty());


        empTypeTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from EMPLOYEE_TYPE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EmployeeType et = new EmployeeType();

                //set the values for this new Vendor based on what's in the database
                et.employee_type_id.set(rs.getInt("EMPLOYEE_TYPE_ID")); //columnLabel should match column name in database
                et.position_name.set(rs.getString("POSITION_NAME"));
                et.position_description.set(rs.getString("POSITION_DESCRIPTION"));

                empTypeData.add(et); //add the new Vendor to an observable list
            }
            empTypeTable.setItems(empTypeData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Employee Type Data");
        }
    }

    public void editEmpType(){
        empTypeNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empTypeNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeType, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPosition_name(t.getNewValue())
        );

        empTypeDescCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empTypeDescCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeType, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPosition_description(t.getNewValue())
        );

    }

    public void saveEmpTypeChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(empTypeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = empTypeTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) empTypeIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String empTypeNameCell = (String) empTypeNameCol.getCellObservableValue(row).getValue();
            String empTypeDescCell = (String) empTypeDescCol.getCellObservableValue(row).getValue();


            PreparedStatement statement = c.prepareStatement("UPDATE EMPLOYEE_TYPE SET POSITION_NAME = ?, POSITION_DESCRIPTION = ? "
                    + "WHERE EMPLOYEE_TYPE_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, empTypeNameCell);
            statement.setString(2, empTypeDescCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteEmpType() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(empTypeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = empTypeTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) empTypeIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EMPLOYEE_TYPE WHERE EMPLOYEE_TYPE_ID =" +currentID;

            stmt.executeUpdate(SQL);
            empTypeData.remove(empTypeTable.getSelectionModel().getSelectedIndex()); //update the observable list
            empTypeTable.setItems(empTypeData); //update the tableview so the deletion shows immediately
            c.close();
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

    public void openNewTypeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Type Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Type");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
