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
import javafx.util.converter.NumberStringConverter;

import java.sql.*;

public class OrderLineTableController {

    public ObservableList<OrderLine> orderLineData = FXCollections.observableArrayList();
    public TableView<OrderLine> orderLineTable;
    public TableColumn<OrderLine, Number> orderLineIDCol;
    public TableColumn<OrderLine, Number> orderLineOrderIDCol;
    public TableColumn<OrderLine, Number> orderLineProductIDCol;
    public TableColumn<OrderLine, Number> orderLineStatusCol;
    public TableColumn<OrderLine, Number> orderLineQuantCol;
    public TableColumn<OrderLine, Number> orderLineTotCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        orderLineIDCol.setCellValueFactory(data -> data.getValue().order_line_idProperty());
        orderLineOrderIDCol.setCellValueFactory(data -> data.getValue().fk_order_idProperty());
        orderLineProductIDCol.setCellValueFactory(data -> data.getValue().fk_product_idProperty());
        orderLineStatusCol.setCellValueFactory(data -> data.getValue().fk_order_line_status_idProperty());
        orderLineQuantCol.setCellValueFactory(data -> data.getValue().quantityProperty());
        orderLineTotCol.setCellValueFactory(data -> data.getValue().totalProperty());

        orderLineTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from ORDER_LINE";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                OrderLine ol = new OrderLine();

                //set the values based on what's in the database
                ol.order_line_id.set(rs.getInt("ORDER_LINE_ID")); //columnLabel should match column name in database
                ol.fk_order_id.set(rs.getInt("FK_ORDER_ID"));
                ol.fk_product_id.set((rs.getInt("FK_PRODUCT_ID")));
                ol.fk_order_line_status_id.set(rs.getInt("FK_ORDER_LINE_STATUS_ID"));
                ol.quantity.set((rs.getInt("QUANTITY")));
                ol.total.set(rs.getDouble("TOTAL"));

                orderLineData.add(ol); //add to an observable list
            }
            orderLineTable.setItems(orderLineData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Order Line Data");
        }

    }

    public void editOrderLine(){
        orderLineOrderIDCol.setCellFactory(TextFieldTableCell.<OrderLine, Number>forTableColumn(new NumberStringConverter()));
        orderLineOrderIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLine, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_order_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderLineProductIDCol.setCellFactory(TextFieldTableCell.<OrderLine, Number>forTableColumn(new NumberStringConverter()));
        orderLineProductIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLine, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_product_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderLineStatusCol.setCellFactory(TextFieldTableCell.<OrderLine, Number>forTableColumn(new NumberStringConverter()));
        orderLineStatusCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLine, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_order_line_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderLineQuantCol.setCellFactory(TextFieldTableCell.<OrderLine, Number>forTableColumn(new NumberStringConverter()));
        orderLineQuantCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLine, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setQuantity(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        orderLineTotCol.setCellFactory(TextFieldTableCell.<OrderLine, Number>forTableColumn(new NumberStringConverter()));
        orderLineTotCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLine, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTotal(Double.parseDouble(String.valueOf(t.getNewValue())))
        );

    }

    public void saveOrderLineChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(orderLineTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = orderLineTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) orderLineIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer orderCell = (Integer) orderLineOrderIDCol.getCellObservableValue(row).getValue();
            Integer productCell = (Integer) orderLineProductIDCol.getCellObservableValue(row).getValue();
            Integer statusCell = (Integer) orderLineStatusCol.getCellObservableValue(row).getValue();
            Integer quantCell = (Integer) orderLineQuantCol.getCellObservableValue(row).getValue();
            Double totCell = (Double) orderLineTotCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE ORDER_LINE SET FK_ORDER_ID = ?, " +
                    "FK_PRODUCT_ID = ?, FK_ORDER_LINE_STATUS_ID = ?, QUANTITY = ?, TOTAL = ? "
                    + "WHERE ORDER_LINE_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, orderCell);
            statement.setInt(2, productCell);
            statement.setInt(3, statusCell);
            statement.setInt(4, quantCell);
            statement.setDouble(5, totCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteOrderLine() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(orderLineTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = orderLineTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) orderLineIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM ORDER_LINE WHERE ORDER_LINE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            orderLineData.remove(orderLineTable.getSelectionModel().getSelectedIndex()); //update the observable list
            orderLineTable.setItems(orderLineData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openOrderLineForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order Line Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Order Line");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openOrderLineStatusTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Order Line Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Order Line Status Table");
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
