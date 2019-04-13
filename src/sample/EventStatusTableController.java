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


public class EventStatusTableController {

    public ObservableList<EventStatus> eStatusData = FXCollections.observableArrayList();
    public TableView<EventStatus> esTable;
    public TableColumn<EventStatus, Number> esIDCol;
    public TableColumn<EventStatus, String> sNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        esIDCol.setCellValueFactory(data -> data.getValue().event_status_idProperty());
        sNameCol.setCellValueFactory(data -> data.getValue().event_status_nameProperty());

        esTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_STATUS";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventStatus es = new EventStatus();

                //set the values for this new Vendor based on what's in the database
                es.event_status_id.set(rs.getInt("EVENT_STATUS_ID")); //columnLabel should match column name in database
                es.event_status_name.set(rs.getString("EVENT_STATUS_NAME"));

                eStatusData.add(es); //add the new Vendor to an observable list
            }
            esTable.setItems(eStatusData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Status Table Data");
        }

    }

    public void editEventStatus(){
        sNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        sNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventStatus, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_status_name(t.getNewValue())
        );

    }

    public void saveEventStatusChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(esTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = esTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) esIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) sNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_STATUS SET EVENT_STATUS_NAME = ? "
                    + "WHERE EVENT_STATUS_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }
    }

    public void deleteEventStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(esTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = esTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) esIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EVENT_STATUS WHERE EVENT_STATUS_ID=" + currentID;

            stmt.executeUpdate(SQL);
            eStatusData.remove(esTable.getSelectionModel().getSelectedIndex()); //update the observable list
            esTable.setItems(eStatusData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openEventTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Table Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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


}
