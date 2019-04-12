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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventNoteTableController {
    public ObservableList<EventNote> enData = FXCollections.observableArrayList();
    public TableView<EventNote> enTable;
    public TableColumn<EventNote, Number> enIDCol;
    public TableColumn<EventNote, Number> eventIDCol;
    public TableColumn<EventNote, Number> noteIDCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        enIDCol.setCellValueFactory(data -> data.getValue().event_note_idProperty());
        eventIDCol.setCellValueFactory(data -> data.getValue().fk_event_idProperty());
        noteIDCol.setCellValueFactory(data -> data.getValue().fk_note_idProperty());

        enTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_NOTE";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventNote en = new EventNote();

                //set the values based on what's in the database
                en.event_note_id.set(rs.getInt("EVENT_NOTE_ID")); //columnLabel should match column name in database
                en.fk_event_id.set(rs.getInt("FK_EVENT_ID"));
                en.fk_note_id.set((rs.getInt("FK_NOTE_ID")));

                enData.add(en); //add to an observable list
            }
            enTable.setItems(enData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Note Data");
        }

    }

    public void editEventNote(){
        eventIDCol.setCellFactory(TextFieldTableCell.<EventNote, Number>forTableColumn(new NumberStringConverter()));
        eventIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventNote, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        noteIDCol.setCellFactory(TextFieldTableCell.<EventNote, Number>forTableColumn(new NumberStringConverter()));
        noteIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventNote, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_note_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveEventNoteChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(enTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = enTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) enIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer eventCell = (Integer) eventIDCol.getCellObservableValue(row).getValue();
            Integer noteCell = (Integer) noteIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_NOTE SET FK_EVENT_ID = ?, " +
                    "FK_NOTE_ID = ? "
                    + "WHERE EVENT_NOTE_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, eventCell);
            statement.setInt(2, noteCell);
            statement.execute();
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

    public void openNoteTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Note Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Note Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
