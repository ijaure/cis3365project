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
import java.util.Date;

public class OrderTableController {

    public ObservableList<Order> orderData = FXCollections.observableArrayList();
    public TableView<Order> orderTable;
    public TableColumn<Order, Number> orderIDCol;
    public TableColumn<Order, Number> orderClientCol;
    public TableColumn<Order, Number> orderStatusCol;
    public TableColumn<Order, Date> orderCompleteDateCol;
    public TableColumn<Order, Date> orderDateCol;
    public TableColumn<Order, String> orderNotesCol;
    public TableColumn<Order, Date> orderDelDateCol;
    public TableColumn<Order, String> orderDelTimeCol;

    //initialize the table data; reference other table controllers
    public void initialize(){
        //Connect to Database
        Connection c;

        orderIDCol.setCellValueFactory(data -> data.getValue().order_idProperty());
        orderClientCol.setCellValueFactory(data -> data.getValue().client_idProperty());
        orderStatusCol.setCellValueFactory(data -> data.getValue().order_status_idProperty());
        orderCompleteDateCol.setCellValueFactory(data -> data.getValue().order_complete_dateProperty());
        orderDateCol.setCellValueFactory(data -> data.getValue().order_dateProperty());
        orderNotesCol.setCellValueFactory(data -> data.getValue().order_notesProperty());
        orderDelDateCol.setCellValueFactory(data -> data.getValue().order_delivery_dateProperty());
        orderDelTimeCol.setCellValueFactory(data -> data.getValue().order_delivery_timeProperty());

        orderTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from [ORDER]";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Order o = new Order();

                //set the values based on what's in the database
                o.order_id.set(rs.getInt("ORDER_ID")); //columnLabel should match column name in database
                o.client_id.set(rs.getInt("CLIENT_ID"));
                o.order_status_id.set((rs.getInt("ORDER_STATUS_ID")));
                o.order_date.set(rs.getDate("ORDER_DATE"));
                o.order_complete_date.set((rs.getDate("ORDER_COMPLETE_DATE")));
                o.order_notes.set(rs.getString("ORDER_NOTES"));
                o.order_delivery_date.set(rs.getDate("ORDER_DELIVERY_DATE"));
                o.order_delivery_time.set(rs.getString("ORDER_DELIVERY_TIME"));

                orderData.add(o); //add to an observable list
            }
            orderTable.setItems(orderData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Order Data");
        }

    }

    public void editOrder(){
        orderClientCol.setCellFactory(TextFieldTableCell.<Order, Number>forTableColumn(new NumberStringConverter()));
        orderClientCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderStatusCol.setCellFactory(TextFieldTableCell.<Order, Number>forTableColumn(new NumberStringConverter()));
        orderStatusCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderDateCol.setCellFactory(TextFieldTableCell.<Order, Date>forTableColumn(new DateStringConverter()));
        orderDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_date(t.getNewValue())
        );

        orderCompleteDateCol.setCellFactory(TextFieldTableCell.<Order, Date>forTableColumn(new DateStringConverter()));
        orderCompleteDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_complete_date(t.getNewValue())
        );

        orderNotesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderNotesCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_notes(t.getNewValue())
        );

        orderDelDateCol.setCellFactory(TextFieldTableCell.<Order, Date>forTableColumn(new DateStringConverter()));
        orderDelDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_delivery_date(t.getNewValue())
        );

        orderDelTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderDelTimeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_delivery_time(t.getNewValue())
        );

    }

    public void saveOrderChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(orderTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = orderTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) orderIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer clientCell = (Integer) orderClientCol.getCellObservableValue(row).getValue();
            Integer statusCell = (Integer) orderStatusCol.getCellObservableValue(row).getValue();

            java.util.Date dateCell = orderDateCol.getCellObservableValue(row).getValue();
            java.sql.Date dateCellSQL = new java.sql.Date(dateCell.getTime());

            java.util.Date compDateCell = orderCompleteDateCol.getCellObservableValue(row).getValue();
            java.sql.Date compDateCellSQL = new java.sql.Date(compDateCell.getTime());

            String notesCell = (String) orderNotesCol.getCellObservableValue(row).getValue();

            java.util.Date delDateCell = orderDelDateCol.getCellObservableValue(row).getValue();
            java.sql.Date delDateCellSQL = new java.sql.Date(delDateCell.getTime());

            String delTimeCell = (String) orderDelTimeCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE [ORDER] SET CLIENT_ID = ?, " +
                    "ORDER_STATUS_ID = ?, ORDER_DATE = ?, ORDER_COMPLETE_DATE = ?, ORDER_NOTES = ?, ORDER_DELIVERY_DATE = ?, "
                    + "ORDER_DELIVERY_TIME = ? "
                    + "WHERE ORDER_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, clientCell);
            statement.setInt(2, statusCell);
            statement.setDate(3, dateCellSQL);
            statement.setDate(4, compDateCellSQL);
            statement.setString(5, notesCell);
            statement.setDate(6, delDateCellSQL);
            statement.setString(7, delTimeCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteOrder() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(orderTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = orderTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) orderIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM [ORDER] WHERE ORDER_ID =" + currentID;

            stmt.executeUpdate(SQL);
            orderData.remove(orderTable.getSelectionModel().getSelectedIndex()); //update the observable list
            orderTable.setItems(orderData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openOrderForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Order");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openOrderStatusTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Order Status Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openOrderLineTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order Line Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Order Line Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
