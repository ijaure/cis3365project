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

public class ProductVendorTableController {

    //no need for this table if products and orders can already be seen & edited in order line
    //also products and vendors can be seen & edited in product table
    //keeping controller and fxml in case we decide to use it later

    //if used, link in the menu of Product or Vendor tables

    public ObservableList<ProductVendor> pvData = FXCollections.observableArrayList();
    public TableView<ProductVendor> pvTable;
    public TableColumn<ProductVendor, Number> pvIDCol;
    public TableColumn<ProductVendor, Number> pIDCol;
    public TableColumn<ProductVendor, Number> vIDCol;

    public void initialize(){
//Connect to Database
        Connection c;

        pvIDCol.setCellValueFactory(data -> data.getValue().product_vendor_idProperty());
        pIDCol.setCellValueFactory(data -> data.getValue().fk_product_idProperty());
        vIDCol.setCellValueFactory(data -> data.getValue().fk_vendor_idProperty());

        pvTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from PRODUCT_VENDOR";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                ProductVendor pv = new ProductVendor();

                //set the values based on what's in the database
                pv.product_vendor_id.set(rs.getInt("PRODUCT_VENDOR_ID")); //columnLabel should match column name in database
                pv.fk_vendor_id.set(rs.getInt("VENDOR_ID"));
                pv.fk_product_id.set((rs.getInt("PRODUCT_ID")));

                pvData.add(pv); //add to an observable list
            }
            pvTable.setItems(pvData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Order Line Data");
        }

    }

    public void editProductVendor(){
        vIDCol.setCellFactory(TextFieldTableCell.<ProductVendor, Number>forTableColumn(new NumberStringConverter()));
        vIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductVendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_vendor_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        pIDCol.setCellFactory(TextFieldTableCell.<ProductVendor, Number>forTableColumn(new NumberStringConverter()));
        pIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductVendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_product_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
    }

    public void saveProductVendorChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if (pvTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = pvTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) pvIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer vendor = (Integer) vIDCol.getCellObservableValue(row).getValue();
            Integer product = (Integer) pIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE PRODUCT_VENDOR SET VENDOR_ID = ?, " +
                    "PRODUCT_ID = ? "
                    + "WHERE PRODUCT_VENDOR_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, vendor);
            statement.setInt(2, product);
            statement.execute();
            c.close();

        }
    }

    //if a product vendor entry is deleted, is can't be restored
    // since it was created when the Order Line was made
    public void deleteProductVendor() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(pvTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = pvTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) pvIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PRODUCT_VENDOR WHERE PRODUCT_VENDOR_ID =" + currentID;

            stmt.executeUpdate(SQL);
            pvData.remove(pvTable.getSelectionModel().getSelectedIndex()); //update the observable list
            pvTable.setItems(pvData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openProductVendorForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Vendor Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Product Vendor");
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



}
