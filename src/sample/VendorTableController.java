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

        vendorTable.setEditable(true); //so we can edit rows later

        //this try/catch loads data from the database into the tableview
        try{
            c = DBClass.connect();
            String SQL = "Select * from VENDOR";

            //Not working, doesn't recognize column VENDOR_STATUS.VENDOR_STATUS_NAME when trying to set values below
            //String SQLStatus = "SELECT VENDOR_STATUS.VENDOR_STATUS_NAME
            //FROM VENDOR JOIN VENDOR_STATUS
            //ON VENDOR.FK_VENDOR_STATUS_ID = VENDOR_STATUS.VENDOR_STATUS_ID";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Vendor v = new Vendor(); //make a new Vendor object

                //set the values for this new Vendor based on what's in the database
                v.vendor_id.set(rs.getInt("VENDOR_ID")); //columnLabel should match column name in database
                v.vendor_name.set(rs.getString("VENDOR_NAME"));
                v.vendor_acc_num.set((rs.getInt("VENDOR_ACC_NUM")));
                v.vendor_join_date.set(rs.getDate("VENDOR_JOIN_DATE"));

                //TODO make status the actual status name, not the foreign id, but don't do if this takes too much time
                v.vendor_status_id.set((rs.getInt("VENDOR_STATUS_ID")));
                v.vendor_contact_first_name.set(rs.getString("VENDOR_CONTACT_FIRST_NAME"));
                v.vendor_contact_last_name.set(rs.getString("VENDOR_CONTACT_LAST_NAME"));
                v.vendor_company_phone.set(rs.getString("VENDOR_COMPANY_PHONE"));
                v.vendor_mobile_phone.set(rs.getString("VENDOR_MOBILE_PHONE"));
                v.vendor_email.set(rs.getString("VENDOR_EMAIL"));
                v.vendor_address.set(rs.getString("VENDOR_ADDRESS"));

                //TODO make region the region name, not the id, but don't do if this takes too much time
                v.vendor_region_id.set((rs.getInt("VENDOR_REGION_ID")));
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

        vendorAcctCol.setCellFactory(TextFieldTableCell.<Vendor, Number>forTableColumn(new NumberStringConverter()));
        vendorAcctCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_acc_num(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        vendorJoinDateCol.setCellFactory(TextFieldTableCell.<Vendor, Date>forTableColumn(new DateStringConverter()));
        vendorJoinDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_join_date(t.getNewValue())
        );

        vendorStatusFKIDCol.setCellFactory(TextFieldTableCell.<Vendor, Number>forTableColumn(new NumberStringConverter()));
        vendorStatusFKIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

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

        vendorPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorPhoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_company_phone(t.getNewValue())
        );

        vendorMobileCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorMobileCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_mobile_phone(t.getNewValue())
        );

        vendorEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_email(t.getNewValue())
        );

        vendorAddressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorAddressCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_address(t.getNewValue())
        );

        vendorRegionCol.setCellFactory(TextFieldTableCell.<Vendor, Number>forTableColumn(new NumberStringConverter()));
        vendorRegionCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_region_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        vendorPayTermsCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vendorPayTermsCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPayment_terms(t.getNewValue())
        );

        vendorCreditCol.setCellFactory(TextFieldTableCell.<Vendor, Number>forTableColumn(new NumberStringConverter()));
        vendorCreditCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Vendor, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVendor_credit_limit(Double.parseDouble(String.valueOf(t.getNewValue())))
        );
    }

    public void saveVendorChanges() throws SQLException, ParseException {
        //get the connection
        Connection c = DBClass.connect();

        if(vendorTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = vendorTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) vendorIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            //collect each cell's value in a variable
            String vendorNameCell = (String) vendorNameCol.getCellObservableValue(row).getValue();
            Integer vendorAcctCell = (Integer) vendorAcctCol.getCellObservableValue(row).getValue();
            java.util.Date vendor_join_dateCell = vendorJoinDateCol.getCellObservableValue(row).getValue();
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

            // SQL statement to update the vendor, put question marks after each = sign,
            // you'll replace these with the variables in the next step
            PreparedStatement statement = c.prepareStatement("UPDATE VENDOR SET VENDOR_NAME = ?, VENDOR_ACC_NUM = ?, VENDOR_JOIN_DATE = ?, " +
                    "VENDOR_STATUS_ID = ?, VENDOR_CONTACT_FIRST_NAME = ?, VENDOR_CONTACT_LAST_NAME = ?, " +
                    "VENDOR_COMPANY_PHONE = ?, VENDOR_MOBILE_PHONE = ?, VENDOR_EMAIL = ?, VENDOR_ADDRESS = ?, " +
                    "VENDOR_REGION_ID = ?, PAYMENT_TERMS = ?, VENDOR_CREDIT_LIMIT = ? " + "WHERE VENDOR_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
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
            c.close();
        }

    }

    public void deleteVendor() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(vendorTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = vendorTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) vendorIDCol.getCellObservableValue(row).getValue(); //get the id of the selected vendor

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM VENDOR WHERE VENDOR_ID =" + currentID;

            stmt.executeUpdate(SQL);
            vendorData.remove(vendorTable.getSelectionModel().getSelectedIndex()); //update the observable list
            vendorTable.setItems(vendorData); //update the tableview so the deletion shows immediately
            c.close();
        }
    }

    public void openVendorStatus(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Vendor Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Vendor Status Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
