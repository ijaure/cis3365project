package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class VendorFormController {
    public ComboBox<Vendor_Status> vendorStatusList;
    public ObservableList<Vendor_Status> vendorStatusData = FXCollections.observableArrayList();

    public ComboBox<Region> vendorRegionList;
    public ObservableList<Region> vendorRegionData = FXCollections.observableArrayList();

    public void initialize(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from VENDOR_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Vendor_Status vStatus = new Vendor_Status();
                vStatus.vendor_status_id.set(rs.getInt("VENDOR_STATUS_ID"));
                vStatus.vendor_status_name.set(rs.getString("VENDOR_STATUS_NAME"));

                vendorStatusData.add(vStatus);
            }
            vendorStatusList.setItems(vendorStatusData);
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

    public void addVendor(){
        System.out.println("Add Vendor Button");
    }
}
