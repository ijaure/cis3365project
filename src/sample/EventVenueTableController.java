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

public class EventVenueTableController {
    public ObservableList<EventVenue> evData = FXCollections.observableArrayList();
    public TableView<EventVenue> evTable;
    public TableColumn<EventVenue, Number> evIDCol;
    public TableColumn<EventVenue, Number> venueIDCol;
    public TableColumn<EventVenue, Number> eventIDCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        evIDCol.setCellValueFactory(data -> data.getValue().event_venue_idProperty());
        venueIDCol.setCellValueFactory(data -> data.getValue().fk_venue_idProperty());
        eventIDCol.setCellValueFactory(data -> data.getValue().fk_event_idProperty());

        evTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_VENUE";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventVenue ev = new EventVenue();

                //set the values based on what's in the database
                ev.event_venue_id.set(rs.getInt("EVENT_VENUE_ID")); //columnLabel should match column name in database
                ev.fk_event_id.set((rs.getInt("EVENT_ID")));//removed from sql script. need to delete.
                ev.fk_venue_id.set((rs.getInt("VENUE_ID")));

                evData.add(ev); //add to an observable list
            }
            evTable.setItems(evData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Venue Data");
        }


    }

    public void editEventVenue(){

        venueIDCol.setCellFactory(TextFieldTableCell.<EventVenue, Number>forTableColumn(new NumberStringConverter()));
        venueIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventVenue, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_venue_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventIDCol.setCellFactory(TextFieldTableCell.<EventVenue, Number>forTableColumn(new NumberStringConverter()));
        eventIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventVenue, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveEventVenueChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(evTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = evTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) evIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer venueCell = (Integer) venueIDCol.getCellObservableValue(row).getValue();
            Integer eventCell = (Integer) eventIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_VENUE SET EVENT_ID = ?, " +
                    "VENUE_ID = ? "
                    + "WHERE EVENT_VENUE_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, eventCell);
            statement.setInt(2, venueCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteEventVenue() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        Statement stmt = c.createStatement();

        if (evTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = evTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) evIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EVENT_VENUE WHERE EVENT_VENUE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            evData.remove(evTable.getSelectionModel().getSelectedIndex()); //update the observable list
            evTable.setItems(evData); //update the tableview so the deletion shows immediately
            c.close();
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

    public void openEventVenueForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Venue Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Event Venue");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openClientEventTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Client Event Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Event Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
