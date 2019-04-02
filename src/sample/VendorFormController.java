package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

//this controller is for the Vendor Form
public class VendorFormController {

    //declare variables
    public ComboBox<Vendor_Status> vendorStatusList;
    public ObservableList<Vendor_Status> vendorStatusData = FXCollections.observableArrayList();

    public ComboBox<Region> vendorRegionList;
    public ObservableList<Region> vendorRegionData = FXCollections.observableArrayList();

    //initialize the Vendor Form
    public void initialize(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c;

        //these try/catch statements load Vendor Status and Regions into the appropriate drop-down lists
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from VENDOR_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Vendor_Status vStatus = new Vendor_Status(); //make a new Vendor_status object

                //assign an ID and Status Name from the database
                vStatus.vendor_status_id.set(rs.getInt("VENDOR_STATUS_ID"));
                vStatus.vendor_status_name.set(rs.getString("VENDOR_STATUS_NAME"));

                vendorStatusData.add(vStatus); //add these to an observable list
            }
            vendorStatusList.setItems(vendorStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Vendor Status Data");
        }

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from REGION";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Region vRegion = new Region();
                vRegion.region_id.set(rs.getInt("REGION_ID"));
                vRegion.region_name.set(rs.getString("REGION_NAME"));

                vendorRegionData.add(vRegion);
            }
            vendorRegionList.setItems(vendorRegionData);
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Vendor Region Data");
        }
    }

    //add a new vendor to the database
    public void addVendor(){
        System.out.println("Add Vendor Button");
    }
}
