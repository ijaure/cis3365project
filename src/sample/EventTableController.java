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

public class EventTableController {
    public ObservableList<Event> eventData = FXCollections.observableArrayList();
    public TableView<Event> eventTable;
    public TableColumn<Event, Number> eventIDCol;
    public TableColumn<Event, String> eventNameCol;
    public TableColumn<Event, Number> eventOccurCol;
    public TableColumn<Event, Number> eventVenueCol;
    public TableColumn<Event, Number> eventClientCol;
    public TableColumn<Event, Number> eventStatusCol;
    public TableColumn<Event, Number> eventNoteCol;
    public TableColumn<Event, String> eventBillAddrCol;
    public TableColumn<Event, String> contactFNameCol;
    public TableColumn<Event, String> contactLNameCol;
    public TableColumn<Event, String> phoneCol;
    public TableColumn<Event, String> emailCol;
    public TableColumn<Event, Date> eventStartCol;
    public TableColumn<Event, Date> eventProjEndCol;
    public TableColumn<Event, Date> eventActEndCol;

    public void initialize(){
        Connection c;

        //assign columns to the property methods in the Vendor class
        eventIDCol.setCellValueFactory(data -> data.getValue().event_idProperty());
        eventNameCol.setCellValueFactory(data -> data.getValue().event_nameProperty());
        eventVenueCol.setCellValueFactory(data -> data.getValue().fk_venue_idProperty());
        eventClientCol.setCellValueFactory(data -> data.getValue().fk_client_idProperty());
        eventBillAddrCol.setCellValueFactory(data -> data.getValue().billing_addressProperty());
        contactFNameCol.setCellValueFactory(data -> data.getValue().event_contact_first_nameProperty());
        contactLNameCol.setCellValueFactory(data -> data.getValue().event_contact_last_nameProperty());
        phoneCol.setCellValueFactory(data -> data.getValue().event_phoneProperty());
        emailCol.setCellValueFactory(data -> data.getValue().event_emailProperty());
        eventStartCol.setCellValueFactory(data -> data.getValue().start_dateProperty());
        eventProjEndCol.setCellValueFactory(data -> data.getValue().proj_end_dateProperty());
        eventActEndCol.setCellValueFactory(data -> data.getValue().act_end_dateProperty());
        eventStatusCol.setCellValueFactory(data -> data.getValue().fk_event_statusProperty());
        eventOccurCol.setCellValueFactory(data -> data.getValue().fk_event_occuranceProperty());
        eventNoteCol.setCellValueFactory(data -> data.getValue().fk_event_note_idProperty());

        eventTable.setEditable(true); //so we can edit rows later

        //this try/catch loads data from the database into the tableview
        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Event e = new Event();

                //set the values for this new Vendor based on what's in the database
                e.event_id.set(rs.getInt("EVENT_ID")); //columnLabel should match column name in database
                e.event_name.set(rs.getString("EVENT_NAME"));
                e.fk_venue_id.set((rs.getInt("FK_VENUE_ID")));
                e.fk_client_id.set(rs.getInt("FK_CLIENT_ID"));

                e.billing_address.set((rs.getString("BILLING_ADDRESS")));
                e.event_contact_first_name.set(rs.getString("EVENT_CONTACT_FIRST"));
                e.event_contact_last_name.set(rs.getString("EVENT_CONTACT_LAST"));
                e.event_phone.set(rs.getString("EVENT_PHONE"));
                e.event_email.set(rs.getString("EVENT_EMAIL"));
                e.start_date.set(rs.getDate("START_DATE"));
                e.proj_end_date.set(rs.getDate("PROJ_END_DATE"));

                e.act_end_date.set((rs.getDate("ACT_END_DATE")));
                e.fk_event_status.set(rs.getInt("FK_EVENT_STATUS"));
                e.fk_event_occurance.set((rs.getInt("FK_EVENT_OCCURANCE_ID")));
                e.fk_event_note_id.set((rs.getInt("FK_EVENT_NOTE_ID")));

                eventData.add(e); //add the new Vendor to an observable list
            }
            eventTable.setItems(eventData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Data");
        }
    }

    public void editEvent(){

        eventNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        eventNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_name(t.getNewValue())
        );

        eventVenueCol.setCellFactory(TextFieldTableCell.<Event, Number>forTableColumn(new NumberStringConverter()));
        eventVenueCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_venue_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventClientCol.setCellFactory(TextFieldTableCell.<Event, Number>forTableColumn(new NumberStringConverter()));
        eventClientCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_client_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventBillAddrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        eventBillAddrCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setBilling_address(t.getNewValue())
        );

        contactFNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactFNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_contact_first_name(t.getNewValue())
        );

        contactLNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactLNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_contact_last_name(t.getNewValue())
        );

        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_phone(t.getNewValue())
        );

        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEvent_email(t.getNewValue())
        );

        eventStartCol.setCellFactory(TextFieldTableCell.<Event, Date>forTableColumn(new DateStringConverter()));
        eventStartCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setStart_date(t.getNewValue())
        );

        eventProjEndCol.setCellFactory(TextFieldTableCell.<Event, Date>forTableColumn(new DateStringConverter()));
        eventProjEndCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setProj_end_date(t.getNewValue())
        );

        eventActEndCol.setCellFactory(TextFieldTableCell.<Event, Date>forTableColumn(new DateStringConverter()));
        eventActEndCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAct_end_date(t.getNewValue())
        );

        eventStatusCol.setCellFactory(TextFieldTableCell.<Event, Number>forTableColumn(new NumberStringConverter()));
        eventStatusCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_status(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventOccurCol.setCellFactory(TextFieldTableCell.<Event, Number>forTableColumn(new NumberStringConverter()));
        eventOccurCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_occurance(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventNoteCol.setCellFactory(TextFieldTableCell.<Event, Number>forTableColumn(new NumberStringConverter()));
        eventNoteCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_note_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveEventChanges() throws SQLException {
//get the connection
        Connection c = DBClass.connect();

        if(eventTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = eventTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) eventIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            //collect each cell's value in a variable
            String eNameCell = (String) eventNameCol.getCellObservableValue(row).getValue();

            Integer venueCell = (Integer) eventVenueCol.getCellObservableValue(row).getValue();
            Integer clientCell = (Integer) eventClientCol.getCellObservableValue(row).getValue();

            String addrCell = (String) eventBillAddrCol.getCellObservableValue(row).getValue();
            String fNameCell = (String) contactFNameCol.getCellObservableValue(row).getValue();
            String lNameCell = (String) contactLNameCol.getCellObservableValue(row).getValue();
            String phoneCell = (String) phoneCol.getCellObservableValue(row).getValue();
            String emailCell = (String) emailCol.getCellObservableValue(row).getValue();

            java.util.Date start = eventStartCol.getCellObservableValue(row).getValue();
            java.sql.Date startSQL = new java.sql.Date(start.getTime());

            java.util.Date proj = eventProjEndCol.getCellObservableValue(row).getValue();
            java.sql.Date projSQL = new java.sql.Date(proj.getTime());

            java.util.Date act = eventActEndCol.getCellObservableValue(row).getValue();
            java.sql.Date actSQL = new java.sql.Date(act.getTime());

            Integer statusCell = (Integer) eventStatusCol.getCellObservableValue(row).getValue();
            Integer occurCell = (Integer) eventOccurCol.getCellObservableValue(row).getValue();
            Integer noteCell = (Integer) eventNoteCol.getCellObservableValue(row).getValue();

            // SQL statement to update the vendor, put question marks after each = sign,
            // you'll replace these with the variables in the next step
            PreparedStatement statement = c.prepareStatement("UPDATE EVENT SET EVENT_NAME = ?, FK_VENUE_ID = ?, " +
                    "FK_CLIENT_ID = ?, BILLING_ADDRESS = ?, EVENT_CONTACT_FIRST = ?, " +
                    "EVENT_CONTACT_LAST = ?, EVENT_PHONE = ?, EVENT_EMAIL = ?, START_DATE = ?, " +
                    "PROJ_END_DATE = ?, ACT_END_DATE = ?, FK_EVENT_STATUS = ?, FK_EVENT_OCCURANCE_ID = ?, " +
                    "FK_EVENT_NOTE_ID = ? " + "WHERE EVENT_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, eNameCell);
            statement.setInt(2, venueCell);
            statement.setInt(3, clientCell);
            statement.setString(4, addrCell);
            statement.setString(5, fNameCell);
            statement.setString(6, lNameCell);
            statement.setString(7, phoneCell);
            statement.setString(8, emailCell);
            statement.setDate(9, startSQL);
            statement.setDate(10, projSQL);
            statement.setDate(11, actSQL);
            statement.setInt(12, statusCell);
            statement.setInt(13, occurCell);
            statement.setInt(14, noteCell);
            statement.execute();

            c.close();
        }

    }

    public void deleteEvent() throws SQLException {
        //get the connection
            Connection c = DBClass.connect();

            Statement stmt = c.createStatement();

            if (eventTable.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Row Selection");
                alert.setContentText("Please select a row in the table");
                alert.showAndWait();
            } else {
                int row = eventTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
                Integer currentID = (Integer) eventIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

                //delete the vendor whose id matches the currently selected vendor's id
                String SQL = "DELETE FROM EVENT WHERE EVENT_ID =" + currentID;

                stmt.executeUpdate(SQL);
                eventData.remove(eventTable.getSelectionModel().getSelectedIndex()); //update the observable list
                eventTable.setItems(eventData); //update the tableview so the deletion shows immediately
                c.close();
            }

    }

    public void openEventStatusTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Status Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEventOccuranceTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Occurrence Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Occurrence Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEventForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Event");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openEventEmployeeTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Employee Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Employee Table");
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

    public void openEventNoteTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Note Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Note Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEventPlannerTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Planner Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Planner Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openEventVenue(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Event Venue Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Venue Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
