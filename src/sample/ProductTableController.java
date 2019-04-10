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
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.sql.*;
import java.util.Date;

public class ProductTableController {

    public ObservableList<Product> productData = FXCollections.observableArrayList();
    public TableView<Product> productTable;
    public TableColumn<Product, Number> productIDCol;
    public TableColumn<Product, Number> productTypeCol;
    public TableColumn<Product, Number> productStatusCol;
    public TableColumn<Product, Number> productVendorCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Number> productSizeCol;
    public TableColumn<Product, Number> productPriceCol;
    public TableColumn<Product, Date> productPriceDateCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        productIDCol.setCellValueFactory(data -> data.getValue().product_idProperty());
        productTypeCol.setCellValueFactory(data -> data.getValue().fk_product_type_idProperty());
        productStatusCol.setCellValueFactory(data -> data.getValue().fk_product_status_idProperty());
        productVendorCol.setCellValueFactory(data -> data.getValue().fk_vendor_idProperty());
        productNameCol.setCellValueFactory(data -> data.getValue().product_nameProperty());
        productSizeCol.setCellValueFactory(data -> data.getValue().product_sizeProperty());
        productPriceCol.setCellValueFactory(data -> data.getValue().product_priceProperty());
        productPriceDateCol.setCellValueFactory(data -> data.getValue().product_price_dateProperty());

        productTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from PRODUCT";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Product p = new Product();

                //set the values based on what's in the database
                p.product_id.set(rs.getInt("PRODUCT_ID")); //columnLabel should match column name in database
                p.fk_product_type_id.set(rs.getInt("FK_PRODUCT_TYPE_ID"));
                p.fk_product_status_id.set((rs.getInt("FK_PRODUCT_STATUS_ID")));
                p.fk_vendor_id.set(rs.getInt("FK_VENDOR_ID"));
                p.product_name.set((rs.getString("PRODUCT_NAME")));
                p.product_size.set(rs.getDouble("PRODUCT_SIZE"));
                p.product_price.set(rs.getDouble("PRODUCT_PRICE"));
                p.product_price_date.set(rs.getDate("PRODUCT_PRICE_DATE"));

                productData.add(p); //add to an observable list
            }
            productTable.setItems(productData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Product Data");
        }
    }

    public void openProductForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editProduct(){
        productTypeCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
        productTypeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_product_type_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        productStatusCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
        productStatusCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_product_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        productVendorCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
        productVendorCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_vendor_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        productNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProduct_name(t.getNewValue())
        );

        productSizeCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
        productSizeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProduct_size(Double.parseDouble(String.valueOf(t.getNewValue())))
        );

        productPriceDateCol.setCellFactory(TextFieldTableCell.<Product, Date>forTableColumn(new DateStringConverter()));
        productPriceDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProduct_price_date(t.getNewValue())
        );

    }

    public void saveProductChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(productTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = productTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) productIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer productTypeCell = (Integer) productTypeCol.getCellObservableValue(row).getValue();
            Integer productStatusCell = (Integer) productStatusCol.getCellObservableValue(row).getValue();
            Integer productVendorCell = (Integer) productVendorCol.getCellObservableValue(row).getValue();
            String productNameCell = (String) productNameCol.getCellObservableValue(row).getValue();
            Double productSizeCell = (Double) productSizeCol.getCellObservableValue(row).getValue();
            Double productPriceCell = (Double) productPriceCol.getCellObservableValue(row).getValue();
            java.util.Date productPriceDateCell = productPriceDateCol.getCellObservableValue(row).getValue();
            java.sql.Date productPriceDateSQLCell = new java.sql.Date(productPriceDateCell.getTime());

            PreparedStatement statement = c.prepareStatement("UPDATE PRODUCT SET FK_PRODUCT_TYPE_ID = ?, " +
                    "FK_PRODUCT_STATUS_ID = ?, FK_VENDOR_ID = ?, PRODUCT_NAME = ?, PRODUCT_SIZE = ?, PRODUCT_PRICE = ?, "
                    + "PRODUCT_PRICE_DATE = ? "
                    + "WHERE PRODUCT_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, productTypeCell);
            statement.setInt(2, productStatusCell);
            statement.setInt(3, productVendorCell);
            statement.setString(4, productNameCell);
            statement.setDouble(5, productSizeCell);
            statement.setDouble(6, productPriceCell);
            statement.setDate(7, productPriceDateSQLCell);
            statement.execute();
            c.close();
        }
    }

    public void deleteProduct() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(productTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = productTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) productIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PRODUCT WHERE PRODUCT_ID =" + currentID;

            stmt.executeUpdate(SQL);
            productData.remove(productTable.getSelectionModel().getSelectedIndex()); //update the observable list
            productTable.setItems(productData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openProductVendorTable(){
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Vendor Table.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Product Vendor Table");
        stage.setScene(new Scene(root));
        stage.show();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void openProductTypeTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Type Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Product Type Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openProductStatusTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Product Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Product Status");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
