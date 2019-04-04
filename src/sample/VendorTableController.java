package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//this controller is for the Vendor table
public class VendorTableController {

    //declare variables
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

    //initialize the vendor table
    public void initialize() throws SQLException, ClassNotFoundException
    {
        //Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        //assign columns to the property methods in the Vendor class
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

        vendorTable.setEditable(true);

        //this try/catch loads data from the database into the tableview
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from VENDOR";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Vendor v = new Vendor(); //make a new Vendor object

                //set the values for this new Vendor based on what's in the database
                v.vendor_id.set(rs.getInt("VENDOR_ID")); //columnLabel should match column name in database
                v.vendor_name.set(rs.getString("VENDOR_NAME"));
                v.vendor_acc_num.set((rs.getInt("VENDOR_ACC_NUM")));
                v.vendor_join_date.set(rs.getDate("VENDOR_JOIN_DATE"));

                //TODO make status the actual status name, not the foreign id
                v.vendor_status_id.set((rs.getInt("FK_VENDOR_STATUS_ID")));
                v.vendor_contact_first_name.set(rs.getString("VENDOR_CONTACT_FIRST_NAME"));
                v.vendor_contact_last_name.set(rs.getString("VENDOR_CONTACT_LAST_NAME"));
                v.vendor_company_phone.set(rs.getString("VENDOR_COMPANY_PHONE"));
                v.vendor_mobile_phone.set(rs.getString("VENDOR_MOBILE_PHONE"));
                v.vendor_email.set(rs.getString("VENDOR_EMAIL"));
                v.vendor_address.set(rs.getString("VENDOR_ADDRESS"));

                //TODO make region the region name, not the id
                v.vendor_region_id.set((rs.getInt("FK_VENDOR_REGION_ID")));
                v.payment_terms.set(rs.getString("PAYMENT_TERMS"));
                v.vendor_credit_limit.set((rs.getDouble("VENDOR_CREDIT_LIMIT")));

                vendorData.add(v); //add the new Vendor to an observable list
            }
            vendorTable.setItems(vendorData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    //open the vendor form (vendor form has its own separate controller, VendorFormController)
    public void openVendorForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Vendor Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Vendor");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVendor(){
        //turn the cells into editable text fields
        vendorNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_name(t.getNewValue())
        );

        //TODO need to change this since this won't work for Date or Number
        /*vendorAcctCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorAcctCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_acc_num(t.getNewValue())
        );*/

        /*vendorJoinDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorJoinDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_acc_num(t.getNewValue())
        );*/

        /*vendorStatusFKIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorStatusFKIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_acc_num(t.getNewValue())
        );*/

        vendorContactFirstCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorContactFirstCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_contact_first_name(t.getNewValue())
        );

        vendorContactLastCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorContactLastCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_contact_last_name(t.getNewValue())
        );
    }

    public void saveVendorChanges() throws SQLException, ParseException {
        /*String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        //Vendor v = vendorTable.getSelectionModel().getSelectedItem();

        //TODO Fix unparseable date error
        int row = vendorTable.getSelectionModel().getSelectedIndex();
        Integer currentID = (Integer) vendorIDCol.getCellObservableValue(row).getValue();
        String vendorNameCell = (String) vendorNameCol.getCellObservableValue(row).getValue();
        Integer vendorAcctCell = (Integer) vendorAcctCol.getCellObservableValue(row).getValue();
        java.util.Date vendor_join_dateCell = formatter.parse(String.valueOf(vendorJoinDateCol.getCellObservableValue(row).getValue()));
        java.sql.Date vendor_join_datesqlCell = new java.sql.Date(vendor_join_dateCell.getTime());
        Integer vendorStatusCell = (Integer) vendorStatusFKIDCol.getCellObservableValue(row).getValue();
        String vendorContactFirstCell = (String) vendorContactFirstCol.getCellObservableValue(row).getValue();
        String vendorContactLastCell = (String) vendorContactLastCol.getCellObservableValue(row).getValue();
        String vendorCompanyPhoneCell = (String) vendorPhoneCol.getCellObservableValue(row).getValue();
        String vendorMobilePhoneCell = (String) vendorPhoneCol.getCellObservableValue(row).getValue();
        String vendorEmailCell = (String) vendorEmailCol.getCellObservableValue(row).getValue();
        String vendorAddressCell = (String) vendorAddressCol.getCellObservableValue(row).getValue();
        Integer vendorRegionCell = (Integer) vendorRegionCol.getCellObservableValue(row).getValue();
        String vendorPayTermsCell = (String) vendorPayTermsCol.getCellObservableValue(row).getValue();
        Double vendorCredLimCell = (Double) vendorCreditCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE VENDOR SET VENDOR_NAME = ?, VENDOR_ACC_NUM = ?, VENDOR_JOIN_DATE = ?, " +
                    "FK_VENDOR_STATUS_ID = ?, VENDOR_CONTACT_FIRST_NAME = ?, VENDOR_CONTACT_LAST_NAME = ?, " +
                    "VENDOR_COMPANY_PHONE = ?, VENDOR_MOBILE_PHONE = ?, VENDOR_EMAIL = ?, VENDOR_ADDRESS = ?, " +
                    "FK_VENDOR_REGION_ID = ?, PAYMENT_TERMS = ?, VENDOR_CREDIT_LIMIT = ? " + "WHERE VENDOR_ID ="+currentID);

        statement.setString(1, vendorNameCell);
        statement.setInt(2, vendorAcctCell);
        statement.setDate(3, vendor_join_datesqlCell);
        statement.setInt(4, vendorStatusCell);
        statement.setString(5, vendorContactFirstCell);
        statement.setString(6, vendorContactLastCell);
        statement.setString(7, vendorCompanyPhoneCell);
        statement.setString(8, vendorMobilePhoneCell);
        statement.setString(9, vendorEmailCell);
        statement.setString(10, vendorAddressCell);
        statement.setInt(11, vendorRegionCell);
        statement.setString(12, vendorPayTermsCell);
        statement.setDouble(13, vendorCredLimCell);
        statement.execute();
        */
    }
}
