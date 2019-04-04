package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//this controller is for the Vendor Form
public class VendorFormController {

    //declare variables
    public ComboBox<Vendor_Status> vendorStatusList;
    public ObservableList<Vendor_Status> vendorStatusData = FXCollections.observableArrayList();

    public ComboBox<Region> vendorRegionList;
    public ObservableList<Region> vendorRegionData = FXCollections.observableArrayList();

    public TextField vendorNameInput = new TextField();
    public TextField vendorAddressInput = new TextField();
    public TextField vendorJoinInput = new TextField();
    public TextField vendorCreditInput = new TextField();
    public TextField vendorAcctInput = new TextField();
    public TextArea vendorPayTermsInput = new TextArea();
    public TextField vendorPhoneInput = new TextField();
    public TextField vendorEmailInput = new TextField();
    public TextField vendorContactFirstInput = new TextField();
    public TextField vendorContactLastInput = new TextField();
    public TextField vendorContactMobileInput = new TextField();

    VendorTableController vtc = new VendorTableController();

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
    public void addVendor() throws SQLException, ParseException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        String vendor_name = vendorNameInput.getText();
        Integer vendor_acc_num = Integer.parseInt(vendorAcctInput.getText());
        java.util.Date vendor_join_date = formatter.parse(vendorJoinInput.getText());
        java.sql.Date vendor_join_datesql = new java.sql.Date(vendor_join_date.getTime());
        Integer vendor_status = vendorStatusList.getSelectionModel().getSelectedItem().getVendor_status_id();
        String vendor_contact_first = vendorContactFirstInput.getText();
        String vendor_contact_last = vendorContactLastInput.getText();
        String vendor_company_phone = vendorPhoneInput.getText();
        String vendor_mobile_phone = vendorContactMobileInput.getText();
        String vendor_email = vendorEmailInput.getText();
        String vendor_address = vendorAddressInput.getText();
        Integer vendor_region = vendorRegionList.getSelectionModel().getSelectedItem().getRegion_id();
        String vendor_payterms = vendorPayTermsInput.getText();
        Double vendor_credit = Double.parseDouble(vendorCreditInput.getText());

        String SQL = "INSERT INTO VENDOR " + "(VENDOR_NAME, VENDOR_ACC_NUM, VENDOR_JOIN_DATE, FK_VENDOR_STATUS_ID, VENDOR_CONTACT_FIRST_NAME, VENDOR_CONTACT_LAST_NAME, VENDOR_COMPANY_PHONE, VENDOR_MOBILE_PHONE, VENDOR_EMAIL, VENDOR_ADDRESS, FK_VENDOR_REGION_ID, PAYMENT_TERMS, VENDOR_CREDIT_LIMIT) "
                + "VALUES ('" + vendor_name + "', '" + vendor_acc_num + "', '" + vendor_join_datesql + "', '"
                + vendor_status + "', '" + vendor_contact_first + "', '" + vendor_contact_last + "', '" + vendor_company_phone + "', '" +
                vendor_mobile_phone + "', '" + vendor_email + "', '" + vendor_address + "', '" + vendor_region + "', '" + vendor_payterms + "', '" + vendor_credit + "')";

        stmt.executeUpdate(SQL);
        c.close();
    }


}
