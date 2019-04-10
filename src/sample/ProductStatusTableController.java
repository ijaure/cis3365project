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

public class ProductStatusTableController {

    public ObservableList<Product_Status> productStatusData = FXCollections.observableArrayList();
    public TableView<Product_Status> productStatusTable;
    public TableColumn<Product_Status, Number> productStatusIDCol;
    public TableColumn<Product_Status, String> productStatusNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        productStatusIDCol.setCellValueFactory(data -> data.getValue().product_status_idProperty());
        productStatusNameCol.setCellValueFactory(data -> data.getValue().product_status_nameProperty());

        productStatusTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from PRODUCT_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Product_Status ps = new Product_Status();

                //set the values for this new Vendor based on what's in the database
                ps.product_status_id.set(rs.getInt("PRODUCT_STATUS_ID")); //columnLabel should match column name in database
                ps.product_status_name.set(rs.getString("PRODUCT_STATUS_NAME"));

                productStatusData.add(ps); //add the new Vendor to an observable list
            }
            productStatusTable.setItems(productStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Product Status Data");
        }

    }

    public void editProductStatus(){
        productStatusNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productStatusNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product_Status, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProduct_status_name(t.getNewValue())
        );

    }

    public void saveProductStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(productStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = productStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) productStatusIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String productStatusNameCell = (String) productStatusNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE PRODUCT_STATUS SET PRODUCT_STATUS_NAME = ? "
                    + "WHERE PRODUCT_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, productStatusNameCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteProductStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(productStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = productStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) productStatusIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PRODUCT_STATUS WHERE PRODUCT_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            productStatusData.remove(productStatusTable.getSelectionModel().getSelectedIndex()); //update the observable list
            productStatusTable.setItems(productStatusData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openProductTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Product Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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

}
