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


public class EventOccuranceTableController {

    public ObservableList<EventOccurance> eoData = FXCollections.observableArrayList();
    public TableView<EventOccurance> eoTable;
    public TableColumn<EventOccurance, Number> eoIDCol;
    public TableColumn<EventOccurance, String> occurNameCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        eoIDCol.setCellValueFactory(data -> data.getValue().event_occurance_idProperty());
        occurNameCol.setCellValueFactory(data -> data.getValue().event_occurance_nameProperty());

        eoTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_OCCURANCE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventOccurance eo = new EventOccurance();

                //set the values for this new Vendor based on what's in the database
                eo.event_occurance_id.set(rs.getInt("EVENT_OCCURANCE_ID")); //columnLabel should match column name in database
                eo.event_occurance_name.set(rs.getString("EVENT_OCCURANCE_NAME"));

                eoData.add(eo); //add the new Vendor to an observable list
            }
            eoTable.setItems(eoData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Occurance Table Data");
        }

    }

    public void editEventOccur(){
        occurNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        occurNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventOccurance, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_occurance_name(t.getNewValue())
        );

    }

    public void saveEventOccurChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(eoTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = eoTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) eoIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            String nameCell = (String) occurNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_OCCURANCE SET EVENT_OCCURANCE_NAME = ? "
                    + "WHERE EVENT_OCCURANCE_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }


    }

    public void deleteEventOccur() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(eoTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = eoTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) eoIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EVENT_OCCURANCE WHERE EVENT_OCCURANCE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            eoData.remove(eoTable.getSelectionModel().getSelectedIndex()); //update the observable list
            eoTable.setItems(eoData); //update the tableview so the deletion shows immediately
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

    public void openTypeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Type Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Type");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
