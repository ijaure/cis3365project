package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProductFormController {

    public TextField productNameInput;
    public TextField productSizeInput;
    public TextField productPriceInput;
    public TextField productPriceDateInput;

    public ObservableList<Product_Type> productTypeData = FXCollections.observableArrayList();
    public ComboBox<Product_Type> productTypeList;

    public ObservableList<Vendor> productVendorData = FXCollections.observableArrayList();
    public ComboBox<Vendor> productVendorList;

    public ObservableList<Product_Status> productStatusData = FXCollections.observableArrayList();
    public ComboBox<Product_Status> productStatusList;

    public void initialize(){
        Connection c;

        //load all the Comboboxes with data from the other tables
        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PRODUCT_TYPE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Product_Type pt = new Product_Type();

                //assign an ID Name from the database
                pt.product_type_id.set(rs.getInt("PRODUCT_TYPE_ID"));
                pt.product_type_name.set(rs.getString("PRODUCT_TYPE_NAME"));

                productTypeData.add(pt); //add these to an observable list
            }
            productTypeList.setItems(productTypeData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Product Type Combobox Data");
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

                productVendorData.add(v); //add these to an observable list
            }
            productVendorList.setItems(productVendorData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Product Vendor Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PRODUCT_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Product_Status ps = new Product_Status();

                //assign an ID Name from the database
                ps.product_status_id.set(rs.getInt("PRODUCT_STATUS_ID"));
                ps.product_status_name.set(rs.getString("PRODUCT_STATUS_NAME"));

                productStatusData.add(ps); //add these to an observable list
            }
            productStatusList.setItems(productStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Product Status Combobox Data");
        }

    }

    public void addProduct() throws SQLException, ParseException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy"); //used the parse the textfield input to a Date

        //output an error if any of the fields are empty
        if(productTypeList.getSelectionModel().isEmpty() ||
                productStatusList.getSelectionModel().isEmpty() ||
                productVendorList.getSelectionModel().isEmpty() ||
                productNameInput.getText().trim().isEmpty() ||
                productSizeInput.getText().trim().isEmpty() ||
                productPriceInput.getText().trim().isEmpty() ||
                productPriceDateInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer product_type = productTypeList.getSelectionModel().getSelectedItem().getProduct_type_id();
            Integer product_status = productStatusList.getSelectionModel().getSelectedItem().getProduct_status_id();
            Integer product_vendor = productVendorList.getSelectionModel().getSelectedItem().getVendor_id();
            String product_name = productNameInput.getText();
            Double product_size = Double.parseDouble(productSizeInput.getText());
            Double product_price = Double.parseDouble(productPriceInput.getText());
            java.util.Date product_price_date = formatter.parse(productPriceDateInput.getText());
            java.sql.Date product_price_datesql = new java.sql.Date(product_price_date.getTime());

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO PRODUCT " + "(PRODUCT_TYPE_ID, PRODUCT_STATUS_ID, VENDOR_ID, PRODUCT_NAME, PRODUCT_SIZE, PRODUCT_PRICE, PRODUCT_PRICE_DATE) "
                    + "VALUES ('" + product_type + "', '" + product_status + "', '" + product_vendor + "', '"
                    + product_name + "', '" + product_size + "', '" + product_price + "', '" + product_price_datesql + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }
    }

}
