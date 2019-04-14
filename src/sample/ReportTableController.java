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
                String SQL = "SELECT VENDOR.VENDOR_NAME, VENDOR_STATUS.VENDOR_STATUS_NAME, REGION.REGION_NAME\n" +
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
                String SQL = "SELECT DISTINCT VENDOR.VENDOR_NAME, VENDOR_STATUS.VENDOR_STATUS_NAME, REGION.REGION_NAME\n" +
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
                String SQL = "SELECT DISTINCT VENUE_CONTACT.CONTACT_PERSON_FIRST_NAME, VENUE_CONTACT.CONTACT_PERSON_LAST_NAME, VENUE_STATUS.VENUE_STATUS_NAME, VENUE.VENUE_NAME \n" +
                        "FROM VENUE_CONTACT JOIN VENUE ON VENUE_CONTACT.VENUE_ID=VENUE.VENUE_ID \n" +
                        "JOIN VENUE_STATUS ON VENUE_STATUS.VENUE_STATUS_ID=VENUE.FK_VENUE_STATUS_ID \n" +
                        "JOIN EVENT ON NOT EVENT_VENUE.VENUE_ID=VENUE.VENUE_ID ";
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
}

