package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

//TODO Initialize the all tables data, maybe use a local database to test everything
//TODO Open Forms or other tables from the Menu buttons in each table, such as with openEmployeeForm()
//this controller is for the tables
public class VendorTableController {

    public ObservableList<Vendor> vendorData = FXCollections.observableArrayList();
    public TableView<Vendor> vendorTable;
    public TableColumn<Vendor, Number> vendorIDCol;
    public TableColumn<Vendor, String> vendorNameCol;
    public TableColumn<Vendor, String> vendorContactFirstCol;
    public TableColumn<Vendor, String> vendorContactLastCol;
    public TableColumn<Vendor, Number> vendorStatusFKIDCol;
    public TableColumn<Vendor, Date> vendorJoinDateCol;
    public TableColumn<Vendor, String> vendorPhoneCol;
    public TableColumn<Vendor, String> vendorMobileCol;
    public TableColumn<Vendor, String> vendorEmailCol;
    public TableColumn<Vendor, String> vendorAddressCol;
    public TableColumn<Vendor, Number> vendorRegionCol;
    public TableColumn<Vendor, Number> vendorCreditCol;
    public TableColumn<Vendor, Number> vendorAcctCol;
    public TableColumn<Vendor, String> vendorPayTermsCol;

    //initialize the vendor tables
    public void initialize() throws SQLException, ClassNotFoundException
    {
        //Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        vendorIDCol.setCellValueFactory(data -> data.getValue().vendor_idProperty());
        vendorNameCol.setCellValueFactory(data -> data.getValue().vendor_nameProperty());
        vendorContactFirstCol.setCellValueFactory(data -> data.getValue().vendor_contact_first_nameProperty());
        vendorContactLastCol.setCellValueFactory(data -> data.getValue().vendor_contact_last_nameProperty());
        vendorStatusFKIDCol.setCellValueFactory(data -> data.getValue().vendor_status_idProperty());
        vendorJoinDateCol.setCellValueFactory(data -> data.getValue().vendor_join_dateProperty());
        vendorPhoneCol.setCellValueFactory(data -> data.getValue().vendor_company_phoneProperty());
        vendorMobileCol.setCellValueFactory(data -> data.getValue().vendor_mobile_phoneProperty());
        vendorEmailCol.setCellValueFactory(data -> data.getValue().vendor_emailProperty());
        vendorAddressCol.setCellValueFactory(data -> data.getValue().vendor_addressProperty());
        vendorRegionCol.setCellValueFactory(data -> data.getValue().vendor_region_idProperty());
        vendorCreditCol.setCellValueFactory(data -> data.getValue().vendor_credit_limitProperty());
        vendorAcctCol.setCellValueFactory(data -> data.getValue().vendor_acc_numProperty());
        vendorPayTermsCol.setCellValueFactory(data -> data.getValue().payment_termsProperty());

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from VENDOR";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Vendor v = new Vendor();
                v.vendor_id.set(rs.getInt("VENDOR_ID"));
                v.vendor_name.set(rs.getString("VENDOR_NAME"));
                v.vendor_acc_num.set((rs.getInt("VENDOR_ACC_NUM")));
                v.vendor_join_date.set(rs.getDate("VENDOR_JOIN_DATE"));
                v.vendor_status_id.set((rs.getInt("FK_VENDOR_STATUS_ID")));
                v.vendor_contact_first_name.set(rs.getString("VENDOR_CONTACT_FIRST_NAME"));
                v.vendor_contact_last_name.set(rs.getString("VENDOR_CONTACT_LAST_NAME"));
                v.vendor_company_phone.set(rs.getString("VENDOR_COMPANY_PHONE"));
                v.vendor_mobile_phone.set(rs.getString("VENDOR_MOBILE_PHONE"));
                v.vendor_email.set(rs.getString("VENDOR_EMAIL"));
                v.vendor_address.set(rs.getString("VENDOR_ADDRESS"));
                v.vendor_region_id.set((rs.getInt("FK_VENDOR_REGION_ID")));
                v.payment_terms.set(rs.getString("PAYMENT_TERMS"));
                v.vendor_credit_limit.set((rs.getDouble("VENDOR_CREDIT_LIMIT")));

                vendorData.add(v);
            }
            vendorTable.setItems(vendorData);
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    //open the vendor form
    public void openVendorForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vendor Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Vendor");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
