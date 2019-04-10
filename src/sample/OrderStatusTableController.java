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

import java.sql.*;

public class OrderStatusTableController {


    public ObservableList<OrderStatus> orderStatusData = FXCollections.observableArrayList();
    public TableView<OrderStatus> orderStatusTable;
    public TableColumn<OrderStatus, Number> orderStatusIDCol;
    public TableColumn<OrderStatus, String> orderStatusNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        orderStatusIDCol.setCellValueFactory(data -> data.getValue().order_status_idProperty());
        orderStatusNameCol.setCellValueFactory(data -> data.getValue().order_status_nameProperty());

        orderStatusTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from ORDER_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                OrderStatus os = new OrderStatus();

                //set the values for this new Vendor based on what's in the database
                os.order_status_id.set(rs.getInt("ORDER_STATUS_ID")); //columnLabel should match column name in database
                os.order_status_name.set(rs.getString("ORDER_STATUS_NAME"));

                orderStatusData.add(os); //add the new Vendor to an observable list
            }
            orderStatusTable.setItems(orderStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Order Status Data");
        }

    }

    public void editOrderStatus(){
        orderStatusNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderStatusNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderStatus, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_status_name(t.getNewValue())
        );

    }

    public void saveOrderStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(orderStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = orderStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) orderStatusIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) orderStatusNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE ORDER_STATUS SET ORDER_STATUS_NAME = ? "
                    + "WHERE ORDER_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteOrderStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(orderStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = orderStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) orderStatusIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM ORDER_STATUS WHERE ORDER_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            orderStatusData.remove(orderStatusTable.getSelectionModel().getSelectedIndex()); //update the observable list
            orderStatusTable.setItems(orderStatusData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openStatusForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Status Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Status");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openOrderTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Order Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
