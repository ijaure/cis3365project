package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ReportsTableController {


    public TableView<ObservableList<String>> dynamicTable;
    public ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();


    //initialize the tables
    public void queryVendorStatusName() {
        //clear the columns and rows from the previous query
        dynamicTable.getColumns().clear();
        dynamicTable.getItems().clear();

        //Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
            try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT VENDOR_STATUS.VENDOR_STATUS_NAME, VENDOR.VENDOR_NAME " +
                    " FROM VENDOR JOIN VENDOR_STATUS " +
                    "ON VENDOR.FK_VENDOR_STATUS_ID = VENDOR_STATUS.VENDOR_STATUS_ID";
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
         }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }

        }

        public void queryEmployeeStatusName(){
            //clear the columns and rows from the previous query
            dynamicTable.getColumns().clear();
            dynamicTable.getItems().clear();

            //Connect to Database
            Connection c;
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                c = DriverManager.getConnection(url);
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
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                c = DriverManager.getConnection(url);
                String SQL = "SELECT VENDOR.VENDOR_NAME, PRODUCT.PRODUCT_NAME, ORDER_LINE.ORDER_LINE_ID " +
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
            }
            catch(Exception e){ //catch any exceptions
                e.printStackTrace();
                System.out.println("Error on Building Reports Table Data");
            }
        }
}
