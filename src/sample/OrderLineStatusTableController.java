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

public class OrderLineStatusTableController {

    public ObservableList<OrderLineStatus> lineStatusData = FXCollections.observableArrayList();
    public TableView<OrderLineStatus> lineStatusTable;
    public TableColumn<OrderLineStatus, Number> lineStatusIDCol;
    public TableColumn<OrderLineStatus, String> lineStatusNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        lineStatusIDCol.setCellValueFactory(data -> data.getValue().order_line_status_idProperty());
        lineStatusNameCol.setCellValueFactory(data -> data.getValue().order_line_status_nameProperty());

        lineStatusTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from ORDER_LINE_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                OrderLineStatus ols = new OrderLineStatus();

                //set the values for this new Vendor based on what's in the database
                ols.order_line_status_id.set(rs.getInt("ORDER_LINE_STATUS_ID")); //columnLabel should match column name in database
                ols.order_line_status_name.set(rs.getString("ORDER_LINE_STATUS_NAME"));

                lineStatusData.add(ols); //add the new Vendor to an observable list
            }
            lineStatusTable.setItems(lineStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Order Line Status Data");
        }

    }

    public void editLineStatus(){
        lineStatusNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lineStatusNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderLineStatus, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setOrder_line_status_name(t.getNewValue())
        );

    }

    public void saveLineStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(lineStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = lineStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) lineStatusIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) lineStatusNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE ORDER_LINE_STATUS SET ORDER_LINE_STATUS_NAME = ? "
                    + "WHERE ORDER_LINE_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteLineStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(lineStatusTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = lineStatusTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) lineStatusIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM ORDER_LINE_STATUS WHERE ORDER_LINE_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            lineStatusData.remove(lineStatusTable.getSelectionModel().getSelectedIndex()); //update the observable list
            lineStatusTable.setItems(lineStatusData); //update the tableview so the deletion shows immediately
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
