package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportTableController {

    public TableView<ObservableList<String>> dynamicTable;
    public ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();

    //each MenuItem is a new query following this format
     public void vendorsTexasProduct(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT VENDOR.VENDOR_NAME, VENDOR_STATUS.VENDOR_STATUS_NAME, REGION.REGION_NAME, PRODUCT.PRODUCT_NAME " +
                        "FROM VENDOR JOIN VENDOR_STATUS ON VENDOR.VENDOR_STATUS_ID=VENDOR_STATUS.VENDOR_STATUS_ID \n" +
                        "JOIN PRODUCT ON PRODUCT.VENDOR_ID=VENDOR.VENDOR_ID \n" +
                        "JOIN REGION ON REGION.REGION_ID=VENDOR.VENDOR_REGION_ID WHERE VENDOR_STATUS_NAME='Current'\n" +
                        "AND REGION_NAME='Texas,'";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void vendorsCaliNoProduct(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT DISTINCT VENDOR.VENDOR_NAME, VENDOR_STATUS.VENDOR_STATUS_NAME, REGION.REGION_NAME " +
                        "FROM VENDOR JOIN VENDOR_STATUS ON VENDOR.VENDOR_STATUS_ID=VENDOR_STATUS.VENDOR_STATUS_ID\n" +
                        "JOIN PRODUCT ON NOT PRODUCT.VENDOR_ID=VENDOR.VENDOR_ID\n" +
                        "JOIN REGION ON REGION.REGION_ID=VENDOR.VENDOR_REGION_ID WHERE VENDOR_STATUS_NAME='Not Specified'\n" +
                        "AND REGION_NAME='California,'";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void orderJimBeam(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT [ORDER].ORDER_ID, PRODUCT.PRODUCT_NAME, ORDER_LINE.ORDER_LINE_ID, ORDER_LINE_STATUS.ORDER_LINE_STATUS_NAME\n" +
                        "FROM [ORDER] JOIN ORDER_LINE ON [ORDER].ORDER_ID=ORDER_LINE.FK_ORDER_ID \n" +
                        "JOIN PRODUCT ON PRODUCT.PRODUCT_ID=ORDER_LINE.FK_PRODUCT_ID \n" +
                        "JOIN ORDER_LINE_STATUS ON ORDER_LINE.FK_ORDER_LINE_STATUS_ID=ORDER_LINE_STATUS.ORDER_LINE_STATUS_ID \n" +
                        "WHERE PRODUCT_NAME='Jim Beam' AND ORDER_LINE_STATUS_NAME='Complete'";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        public void orderDonJulio(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT [ORDER].ORDER_ID, PRODUCT.PRODUCT_NAME, ORDER_LINE.ORDER_LINE_ID, ORDER_LINE_STATUS.ORDER_LINE_STATUS_NAME, ORDER_LINE.QUANTITY\n" +
                        "FROM [ORDER] JOIN ORDER_LINE ON [ORDER].ORDER_ID=ORDER_LINE.FK_ORDER_ID \n" +
                        "JOIN PRODUCT ON PRODUCT.PRODUCT_ID=ORDER_LINE.FK_PRODUCT_ID \n" +
                        "JOIN ORDER_LINE_STATUS ON ORDER_LINE.FK_ORDER_LINE_STATUS_ID=ORDER_LINE_STATUS.ORDER_LINE_STATUS_ID \n" +
                        "WHERE PRODUCT_NAME='Don Julio' AND ORDER_LINE_STATUS_NAME='In Progress' AND QUANTITY > 10";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        //query to view events and planners, show by name
        public void venueContactNoEvent(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT DISTINCT VENUE_CONTACT.CONTACT_PERSON_FIRST_NAME, VENUE_CONTACT.CONTACT_PERSON_LAST_NAME, VENUE_STATUS.VENUE_STATUS_NAME, VENUE.VENUE_NAME\n" +
                        "FROM VENUE_CONTACT JOIN VENUE ON VENUE_CONTACT.VENUE_ID=VENUE.VENUE_ID\n" +
                        "JOIN EVENT_VENUE ON VENUE_CONTACT.VENUE_ID NOT IN (SELECT VENUE_ID FROM EVENT_VENUE)\n" +
                        "JOIN VENUE_STATUS ON VENUE_STATUS.VENUE_STATUS_ID=VENUE.VENUE_STATUS_ID WHERE VENUE_STATUS_NAME = 'Confirmed'";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        //additional team queries
        /*
        MenuItems for first 5:
         <MenuItem mnemonicParsing="false" onAction="#empRental" text="Rental Employees by Event" />
         <MenuItem mnemonicParsing="false" onAction="#clientActive" text="Active Clients by Venue" />
         <MenuItem mnemonicParsing="false" onAction="#emergencyEmpEvent" text="Employee by Event w/ Emergency Contact" />
         <MenuItem mnemonicParsing="false" onAction="#clientActiveEventNote" text="Active Clients by Event w/ Notes" />
         <MenuItem mnemonicParsing="false" onAction="#clientInactiveEventNote" text="Inactive Clients by Events w/ Notes" />



        public void empRental(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.EMPLOYEE_LAST_NAME, " +
                        "EVENT.EVENT_NAME, " +
                        "EVENT.EVENT_STATUS, " +
                        "VENUE.VENUE_NAME " +
                        "FROM " +
                        "EMPLOYEE INNER JOIN EVENT_EMPLOYEE ON EMPLOYEE.EMPLOYEE_ID=EVENT_EMPLOYEE.EMPLOYEE_ID " +
                        "INNER JOIN EVENT ON EVENT_EMPLOYEE.EVENT_ID=EVENT.EVENT_ID " +
                        "INNER JOIN EVENT_VENUE ON EVENT.EVENT_ID=EVENT_VENUE.EVENT_ID " +
                        "INNER JOIN VENUE ON EVENT_VENUE.VENUE_ID=VENUE.VENUE_ID " +
                        "WHERE EMPLOYEE.EMPLOYEE_TYPE_ID='2';";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void clientActive(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT CLIENT.CLIENT_ID, CLIENT.CLIENT_LNAME, VENUE.VENUE_NAME, " +
                        "VENUE.VENUE_STATUS_ID " +
                        "FROM " +
                        "CLIENT INNER JOIN CLIENT_EVENT ON CLIENT.CLIENT_ID=CLIENT_EVENT.CLIENT_ID " +
                        "INNER JOIN EVENT ON CLIENT_EVENT.EVENT_ID=EVENT.EVENT_ID " +
                        "INNER JOIN EVENT_VENUE ON EVENT.EVENT_ID=EVENT_VENUE.EVENT_ID " +
                        "INNER JOIN VENUE ON EVENT_VENUE.VENUE_ID=VENUE.VENUE_ID " +
                        "WHERE CLIENT.CLIENT_STATUS_ID='1';";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void emergencyEmpEvent(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT EMPLOYEE_EMERGENCY_CONTACT.EMERGENCY_CONTACT_FIRST_NAME, EMPLOYEE_EMERGENCY_CONTACT.EMERGENCY_CONTACT_PHONE, " +
                        "EMPLOYEE.EMPLOYEE_ID, " +
                        "EMPLOYEE.EMPLOYEE_LAST_NAME, " +
                        "EVENT.EVENT_ID, " +
                        "EVENT.EVENT_NAME " +
                        "FROM " +
                        "EMPLOYEE_EMERGENCY_CONTACT INNER JOIN EMPLOYEE ON EMPLOYEE_EMERGENCY_CONTACT.EMPLOYEE_ID=EMPLOYEE.EMPLOYEE_ID " +
                        "INNER JOIN EVENT_EMPLOYEE ON EMPLOYEE.EMPLOYEE_ID=EVENT_EMPLOYEE.EMPLOYEE_ID " +
                        "INNER JOIN EVENT ON EVENT_EMPLOYEE.EVENT_ID=EVENT.EVENT_ID " +
                        "WHERE EVENT.EVENT_STATUS='2';";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void clientActiveEventNote(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT " +
                        "CLIENT.CLIENT_ID, " +
                        "CLIENT.CLIENT_LNAME, " +
                        "EVENT.EVENT_NAME, " +
                        "NOTE.NOTE_DESCRIPTION " +
                        "FROM " +
                        "CLIENT INNER JOIN CLIENT_EVENT ON CLIENT.CLIENT_ID=CLIENT_EVENT.CLIENT_ID " +
                        "INNER JOIN EVENT ON CLIENT_EVENT.EVENT_ID=EVENT.EVENT_ID " +
                        "INNER JOIN EVENT_NOTE ON EVENT.EVENT_ID=EVENT_NOTE.EVENT_ID " +
                        "INNER JOIN NOTE ON EVENT_NOTE.NOTE_ID=NOTE.NOTE_ID " +
                        "WHERE CLIENT.CLIENT_STATUS_ID='1' " +
                        "AND EVENT.EVENT_STATUS='4';";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void clientInactiveEventNote(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT CLIENT.CLIENT_ID, " +
                        "CLIENT.CLIENT_LNAME, " +
                        "EVENT.EVENT_NAME, " +
                        "NOTE.NOTE_DESCRIPTION " +
                        "FROM " +
                        "CLIENT INNER JOIN CLIENT_EVENT ON CLIENT.CLIENT_ID=CLIENT_EVENT.CLIENT_ID " +
                        "INNER JOIN EVENT ON CLIENT_EVENT.EVENT_ID=EVENT.EVENT_ID " +
                        "INNER JOIN EVENT_NOTE ON EVENT.EVENT_ID=EVENT_NOTE.EVENT_ID " +
                        "INNER JOIN NOTE ON EVENT_NOTE.NOTE_ID=NOTE.NOTE_ID " +
                        "WHERE CLIENT.CLIENT_STATUS_ID='2' " +
                        "AND EVENT.EVENT_STATUS='4';";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

//other unused queries
    public void queryEmployeeStatusName(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT EMPLOYEE_STATUS.EMPLOYEE_STATUS_NAME, EMPLOYEE.EMPLOYEE_FIRST_NAME, EMPLOYEE.EMPLOYEE_LAST_NAME " +
                        " FROM EMPLOYEE JOIN EMPLOYEE_STATUS " +
                        "ON EMPLOYEE.FK_EMPLOYEE_STATUS_ID = EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void queryProductVendorOrder(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT VENDOR.VENDOR_NAME, PRODUCT.PRODUCT_NAME, ORDER_LINE.FK_ORDER_ID " +
                        " FROM VENDOR JOIN PRODUCT ON PRODUCT.FK_VENDOR_ID = VENDOR.VENDOR_ID " +
                        "JOIN ORDER_LINE ON ORDER_LINE.FK_PRODUCT_ID = PRODUCT.PRODUCT_ID";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }

        public void queryVendorState(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT VENDOR.VENDOR_NAME, REGION.REGION_NAME " +
                        " FROM VENDOR JOIN REGION ON VENDOR.FK_VENDOR_REGION_ID = REGION.REGION_ID ";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        public void queryInactiveVendor(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            try {
                c = DBClass.connect();
                String SQL = "SELECT VENDOR.VENDOR_NAME, VENDOR_STATUS.VENDOR_STATUS_NAME, PRODUCT.PRODUCT_NAME " +
                        " FROM VENDOR JOIN VENDOR_STATUS ON VENDOR.FK_VENDOR_STATUS_ID = VENDOR_STATUS.VENDOR_STATUS_ID " +
                        "JOIN PRODUCT ON PRODUCT.FK_VENDOR_ID = VENDOR.VENDOR_ID WHERE VENDOR_STATUS_NAME = 'Inactive' ";
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        public void queryOrdersHold(){

        }

        //query to view events and planners, show by name
        public void eventPlanner(){

        }*/
}

