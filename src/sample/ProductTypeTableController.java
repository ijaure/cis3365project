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

public class ProductTypeTableController {

    public ObservableList<Product_Type> productTypeData = FXCollections.observableArrayList();
    public TableView<Product_Type> productTypeTable;
    public TableColumn<Product_Type, Number> productTypeIDCol;
    public TableColumn<Product_Type, String> productTypeNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        productTypeIDCol.setCellValueFactory(data -> data.getValue().product_type_idProperty());
        productTypeNameCol.setCellValueFactory(data -> data.getValue().product_type_nameProperty());

        productTypeTable.setEditable(true);

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from PRODUCT_TYPE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Product_Type pt = new Product_Type();

                //set the values for this new Vendor based on what's in the database
                pt.product_type_id.set(rs.getInt("PRODUCT_TYPE_ID")); //columnLabel should match column name in database
                pt.product_type_name.set(rs.getString("PRODUCT_TYPE_NAME"));

                productTypeData.add(pt); //add the new Vendor to an observable list
            }
            productTypeTable.setItems(productTypeData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Product Type Data");
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

    public void editProductType(){
        productTypeNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productTypeNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product_Type, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProduct_type_name(t.getNewValue())
        );

    }

    public void saveProductTypeChanges() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);

        if(productTypeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = productTypeTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) productTypeIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String productTypeNameCell = (String) productTypeNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE PRODUCT_TYPE SET PRODUCT_TYPE_NAME = ? "
                    + "WHERE PRODUCT_TYPE_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, productTypeNameCell);
            statement.execute();
        }

    }

    public void deleteProductType() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        if(productTypeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = productTypeTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) productTypeIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PRODUCT_TYPE WHERE PRODUCT_TYPE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            productTypeData.remove(productTypeTable.getSelectionModel().getSelectedIndex()); //update the observable list
            productTypeTable.setItems(productTypeData); //update the tableview so the deletion shows immediately
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

}
