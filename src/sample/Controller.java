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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

//TODO Initialize the all tables data, maybe use a local database to test everything
//TODO Open Forms or other tables from the Menu buttons in each table, such as with openEmployeeForm()
//this controller is for the tables
public class Controller {

    public TableView<ObservableList<String>> vendorTable;
    public ObservableList<ObservableList<String>> vendorData = FXCollections.observableArrayList();

    //initialize the tables
    public void initialize()
    {
        //Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);

            String SQL = "SELECT * from VENDOR";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            int index = rs.getMetaData().getColumnCount();

            //dynamically add table columns, so they are made based off database columns
            //Not sure if this method will make it harder to add data later
            for(int i=0 ; i<index; i++){
                final int j = i;
                TableColumn<ObservableList<String>,String> col = new TableColumn<>(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));

                vendorTable.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            //add to observable list
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                vendorData.add(row);

            }
            //add to tableview
            vendorTable.setItems(vendorData);
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }


    }

    //open the employees form from the "New Employee" button in employees table
    public void openEmployeeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Employee Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Employee");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //open client from from Clients table
    public void openClientForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Client Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Client");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
