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

public class VendorStatusTableController {

    public ObservableList<Vendor_Status> vendorStatusData = FXCollections.observableArrayList();
    public TableView<Vendor_Status> vendorStatusTable;
    public TableColumn<Vendor_Status, Number> vendorStatusIDCol;
    public TableColumn<Vendor_Status, String> vendorStatusNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        vendorStatusIDCol.setCellValueFactory(data -> data.getValue().vendor_status_idProperty());
        vendorStatusNameCol.setCellValueFactory(data -> data.getValue().vendor_status_nameProperty());

        vendorStatusTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from VENDOR_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Vendor_Status vs = new Vendor_Status();

                //set the values for this new Vendor based on what's in the database
                vs.vendor_status_id.set(rs.getInt("VENDOR_STATUS_ID")); //columnLabel should match column name in database
                vs.vendor_status_name.set(rs.getString("VENDOR_STATUS_NAME"));

                vendorStatusData.add(vs); //add the new Vendor to an observable list
            }
            vendorStatusTable.setItems(vendorStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    public void openVendorStatus(){
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

    public void openVendorTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Vendor Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Vendor Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editVendorStatus(){
        vendorStatusNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorStatusNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor_Status, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_status_name(t.getNewValue())
        );
    }

    public void saveVendorStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(vendorStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = vendorStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) vendorStatusIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            String vendorStatusNameCell = (String) vendorStatusNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE VENDOR_STATUS SET VENDOR_STATUS_NAME = ? "
                    + "WHERE VENDOR_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, vendorStatusNameCell);
            statement.execute();
            c.close();
        }
    }

    public void deleteVendorStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(vendorStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = vendorStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) vendorStatusIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM VENDOR_STATUS WHERE VENDOR_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            vendorStatusData.remove(vendorStatusTable.getSelectionModel().getSelectedIndex()); //update the observable list
            vendorStatusTable.setItems(vendorStatusData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }


}
