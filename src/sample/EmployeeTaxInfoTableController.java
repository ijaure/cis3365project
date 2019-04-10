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

public class EmployeeTaxInfoTableController {

    public ObservableList<EmployeeTaxInfo> empTaxData = FXCollections.observableArrayList();
    public TableView<EmployeeTaxInfo> empTaxTable;
    public TableColumn<EmployeeTaxInfo, Number> empTaxIDCol;
    public TableColumn<EmployeeTaxInfo, String> empTaxFormCol;

    public void initialize(){
        Connection c;

        empTaxIDCol.setCellValueFactory(data -> data.getValue().employee_tax_idProperty());
        empTaxFormCol.setCellValueFactory(data -> data.getValue().tax_formProperty());

        empTaxTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from EMPLOYEE_TAX_INFORMATION";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EmployeeTaxInfo eti = new EmployeeTaxInfo();

                //set the values for this new Vendor based on what's in the database
                eti.employee_tax_id.set(rs.getInt("EMPLOYEE_TAX_ID")); //columnLabel should match column name in database
                eti.tax_form.set(rs.getString("TAX_FORM"));

                empTaxData.add(eti); //add the new Vendor to an observable list
            }
            empTaxTable.setItems(empTaxData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Employee Tax Info Data");
        }

    }

    public void editTaxInfo(){
        empTaxFormCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empTaxFormCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeTaxInfo, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTax_form(t.getNewValue())
        );

    }

    public void saveTaxInfoChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(empTaxTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = empTaxTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) empTaxIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String empTaxFormCell = (String) empTaxFormCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EMPLOYEE_TAX_INFORMATION SET TAX_FORM = ? "
                    + "WHERE EMPLOYEE_TAX_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, empTaxFormCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteTaxInfo() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(empTaxTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = empTaxTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) empTaxIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EMPLOYEE_TAX_INFORMATION WHERE EMPLOYEE_TAX_ID =" + currentID;

            stmt.executeUpdate(SQL);
            empTaxData.remove(empTaxTable.getSelectionModel().getSelectedIndex()); //update the observable list
            empTaxTable.setItems(empTaxData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openTaxInfoForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Tax Info Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Employee Tax Info");
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
