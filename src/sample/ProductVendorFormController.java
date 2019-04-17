package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductVendorFormController {

    public ObservableList<Product> productData = FXCollections.observableArrayList();
    public ObservableList<Vendor> vendorData = FXCollections.observableArrayList();

    public ComboBox<Product> productList;
    public ComboBox<Vendor> vendorList;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PRODUCT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Product p = new Product();

                //assign an ID Name from the database
                p.product_id.set(rs.getInt("PRODUCT_ID"));
                p.product_name.set(rs.getString("PRODUCT_NAME"));

                productData.add(p); //add these to an observable list
            }
            productList.setItems(productData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from VENDOR";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Vendor v = new Vendor();

                //assign an ID Name from the database
                v.vendor_id.set(rs.getInt("VENDOR_ID"));
                v.vendor_name.set(rs.getString("VENDOR_NAME"));

                vendorData.add(v); //add these to an observable list
            }
            vendorList.setItems(vendorData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Combobox Data");
        }
    }


    public void addProductVendor() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(productList.getSelectionModel().isEmpty() ||
                vendorList.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer vendor = vendorList.getSelectionModel().getSelectedItem().getVendor_id();
            Integer product = productList.getSelectionModel().getSelectedItem().getProduct_id();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO PRODUCT_VENDOR " + "(VENDOR_ID, PRODUCT_ID) "
                    + "VALUES ('" + vendor + "', '" + product + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }
}
