package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

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
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
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

    }

    public void saveProductChanges(){

    }

    public void deleteProduct(){

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

    public void addOrderToProduct(){

    }
}
