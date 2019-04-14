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


public class VenueStatusTableController {

    public ObservableList<VenueStatus> vStatusData = FXCollections.observableArrayList();
    public TableView<VenueStatus> vsTable;
    public TableColumn<VenueStatus, Number> vsIDCol;
    public TableColumn<VenueStatus, String> sNameCol;


    public void initialize(){
        //Connect to Database
        Connection c;

        vsIDCol.setCellValueFactory(data -> data.getValue().venue_status_idProperty());
        sNameCol.setCellValueFactory(data -> data.getValue().venue_status_nameProperty());

        vsTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from VENUE_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                VenueStatus vs = new VenueStatus();

                //set the values for this new Vendor based on what's in the database
                vs.venue_status_id.set(rs.getInt("VENUE_STATUS_ID")); //columnLabel should match column name in database
                vs.venue_status_name.set(rs.getString("VENUE_STATUS_NAME"));

                vStatusData.add(vs); //add the new Vendor to an observable list
            }
            vsTable.setItems(vStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Venue Status Table Data");
        }

    }

    public void editVenueStatus(){
        sNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        sNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueStatus, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_status_name(t.getNewValue())
        );

    }

    public void saveVenueStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(vsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = vsTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) vsIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            String nameCell = (String) sNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE VENUE_STATUS SET VENUE_STATUS_NAME = ? "
                    + "WHERE VENUE_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteVenueStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(vsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = vsTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) vsIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM VENUE_STATUS WHERE VENUE_STATUS_ID =" + currentID;

            stmt.executeUpdate(SQL);
            vStatusData.remove(vsTable.getSelectionModel().getSelectedIndex()); //update the observable list
            vsTable.setItems(vStatusData); //update the tableview so the deletion shows immediately
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

    public void openVenueTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Venue Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Venue Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
