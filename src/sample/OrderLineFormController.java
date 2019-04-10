package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrderLineFormController {
    public ObservableList<Order> ordData = FXCollections.observableArrayList();
    public ObservableList<Product> prodData = FXCollections.observableArrayList();
    public ObservableList<OrderLineStatus> lineStatData = FXCollections.observableArrayList();

    public ComboBox<Order> ordList;
    public ComboBox<Product> prodList;
    public ComboBox<OrderLineStatus> lineStatList;

    public TextField quantityInput;
    public TextField totalInput;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from [ORDER]";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Order o = new Order();

                //assign an ID Name from the database
                o.order_id.set(rs.getInt("ORDER_ID"));
                o.order_notes.set(rs.getString("ORDER_NOTES"));

                ordData.add(o); //add these to an observable list
            }
            ordList.setItems(ordData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Order Status Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PRODUCT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Product p = new Product();

                //assign an ID Name from the database
                p.product_id.set(rs.getInt("PRODUCT_ID"));
                p.product_name.set(rs.getString("PRODUCT_NAME"));

                prodData.add(p); //add these to an observable list
            }
            prodList.setItems(prodData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Product Combobox Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from ORDER_LINE_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                OrderLineStatus ols = new OrderLineStatus();

                //assign an ID Name from the database
                ols.order_line_status_id.set(rs.getInt("ORDER_LINE_STATUS_ID"));
                ols.order_line_status_name.set(rs.getString("ORDER_LINE_STATUS_NAME"));

                lineStatData.add(ols); //add these to an observable list
            }
            lineStatList.setItems(lineStatData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Order Line Status Combobox Data");
        }

    }

    public void addOrderLine() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy"); //used the parse the textfield input to a Date

        //output an error if any of the fields are empty
        if(ordList.getSelectionModel().isEmpty() ||
                prodList.getSelectionModel().isEmpty() ||
                lineStatList.getSelectionModel().isEmpty() ||
                quantityInput.getText().trim().isEmpty() ||
                totalInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer order = ordList.getSelectionModel().getSelectedItem().getOrder_id();
            Integer product = prodList.getSelectionModel().getSelectedItem().getProduct_id();
            Integer lineStatus = lineStatList.getSelectionModel().getSelectedItem().getOrder_line_status_id();
            Integer quantity = Integer.parseInt(quantityInput.getText());
            Double total = Double.parseDouble(totalInput.getText());

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO ORDER_LINE " + "(FK_ORDER_ID, FK_PRODUCT_ID, FK_ORDER_LINE_STATUS_ID, QUANTITY, TOTAL) "
                    + "VALUES ('" + order + "', '" + product + "', '" + lineStatus + "', '"
                    + quantity + "', '" + total + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }
}
