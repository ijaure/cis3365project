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

public class ClientStatusTableController {

    public ObservableList<Client_Status> csData = FXCollections.observableArrayList();
    public TableView<Client_Status> csTable;
    public TableColumn<Client_Status, Number> csIDCol;
    public TableColumn<Client_Status, String> csNameCol;

    public void initialize(){
        Connection c;

        csIDCol.setCellValueFactory(data -> data.getValue().client_status_id_property());
        csNameCol.setCellValueFactory(data -> data.getValue().client_status_name_property());

        csTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from CLIENT_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Client_Status cs = new Client_Status();

                //set the values for this new Vendor based on what's in the database
                cs.client_status_id.set(rs.getInt("CLIENT_STATUS_ID")); //columnLabel should match column name in database
                cs.client_status_name.set(rs.getString("CLIENT_STATUS_NAME"));

                csData.add(cs); //add the new Vendor to an observable list
            }
            csTable.setItems(csData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Client Status Data");
        }

    }

    public void editStatus(){
        csNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        csNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client_Status, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_status_name(t.getNewValue())
        );
    }

    public void saveStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(csTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = csTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) csIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) csNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE CLIENT_STATUS SET CLIENT_STATUS_NAME = ? "
                    + "WHERE CLIENT_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }
    }

    public void deleteStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(csTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = csTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) csIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM CLIENT_STATUS WHERE CLIENT_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            csData.remove(csTable.getSelectionModel().getSelectedIndex()); //update the observable list
            csTable.setItems(csData); //update the tableview so the deletion shows immediately
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

    public void openClientTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ClientTable Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
