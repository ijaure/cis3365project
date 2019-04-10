package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderFormController {

    public ObservableList<OrderStatus> orderStatusData = FXCollections.observableArrayList();
    public ObservableList<Client> orderClientData = FXCollections.observableArrayList();
    public ComboBox<OrderStatus> orderStatusList;
    public ComboBox<Client> orderClientList;


    public TextField orderDateInput;
    public TextField orderDelDateInput;
    public TextField orderDelTimeInput;
    public TextField orderCompDateInput;
    public TextArea orderNotesInput;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from ORDER_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                OrderStatus os = new OrderStatus();

                //assign an ID Name from the database
                os.order_status_id.set(rs.getInt("ORDER_STATUS_ID"));
                os.order_status_name.set(rs.getString("ORDER_STATUS_NAME"));

                orderStatusData.add(os); //add these to an observable list
            }
            orderStatusList.setItems(orderStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Order Status Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Client cl = new Client();

                //assign an ID Name from the database
                cl.client_id.set(rs.getInt("CLIENT_ID"));
                cl.client_fname.set(rs.getString("CLIENT_FNAME"));
                cl.client_lname.set(rs.getString("CLIENT_LNAME"));

                orderClientData.add(cl); //add these to an observable list
            }
            orderClientList.setItems(orderClientData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Order Client Combobox Data");
        }

    }

    public void addOrder() throws SQLException, ParseException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy"); //used the parse the textfield input to a Date

        //output an error if any of the fields are empty
        if(orderStatusList.getSelectionModel().isEmpty() ||
                orderClientList.getSelectionModel().isEmpty() ||
                orderDateInput.getText().trim().isEmpty() ||
                orderCompDateInput.getText().trim().isEmpty() ||
                orderNotesInput.getText().trim().isEmpty() ||
                orderDelDateInput.getText().trim().isEmpty() ||
                orderDelTimeInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer orderStatus = orderStatusList.getSelectionModel().getSelectedItem().getOrder_status_id();
            Integer orderClient = orderClientList.getSelectionModel().getSelectedItem().getClient_id();
            String notes = orderNotesInput.getText();

            java.util.Date date = formatter.parse(orderDateInput.getText());
            java.sql.Date dateSQL = new java.sql.Date(date.getTime());

            java.util.Date compDate = formatter.parse(orderCompDateInput.getText());
            java.sql.Date compDateSQL = new java.sql.Date(compDate.getTime());

            java.util.Date delDate = formatter.parse(orderDelDateInput.getText());
            java.sql.Date delDateSQL = new java.sql.Date(delDate.getTime());

            String delTime = orderDelTimeInput.getText();
            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO [ORDER] " + "(FK_CLIENT_ID, FK_ORDER_STATUS_ID, ORDER_DATE, ORDER_COMPLETE_DATE, ORDER_NOTES, ORDER_DELIVERY_DATE, ORDER_DELIVERY_TIME) "
                    + "VALUES ('" + orderClient + "', '" + orderStatus + "', '" + dateSQL + "', '"
                    + compDateSQL + "', '" + notes + "', '" + delDateSQL + "', '" + delTime + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }
    }



}
