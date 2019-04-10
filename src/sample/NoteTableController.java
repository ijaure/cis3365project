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

public class NoteTableController {
    public ObservableList<Note> noteData = FXCollections.observableArrayList();
    public TableView<Note> noteTable;
    public TableColumn<Note, Number> noteIDCol;
    public TableColumn<Note, String> noteDescrCol;
    public TableColumn<Note, Number> noteTypeIDCol;

    public void initialize() {
//Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        noteIDCol.setCellValueFactory(data -> data.getValue().note_idProperty());
        noteDescrCol.setCellValueFactory(data -> data.getValue().note_descriptionProperty());
        noteTypeIDCol.setCellValueFactory(data -> data.getValue().fk_note_type_idProperty());

        noteTable.setEditable(true);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from NOTE";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Note note = new Note();
                note.note_id.set(rs.getInt("NOTE_ID"));
                note.note_description.set(rs.getString("NOTE_DESCRIPTION"));
                note.fk_note_type_id.set(rs.getInt("FK_NOTE_TYPE_ID"));

                noteData.add(note);

            }
            noteTable.setItems(noteData);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    public void openNoteForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Note Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Note");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editNote(){
        noteDescrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        noteDescrCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Note, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNote_description(t.getNewValue())
        );
        noteTypeIDCol.setCellFactory(TextFieldTableCell.<Note, Number>forTableColumn(new NumberStringConverter()));
        noteTypeIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Note, Number> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_note_type_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }
    public void saveNoteChanges() throws SQLException {

        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);

        if (noteTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = noteTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) noteIDCol.getCellObservableValue(row).getValue();

            String noteDescriptionCell= (String) noteDescrCol.getCellObservableValue(row).getValue();
            Integer noteTypeCell= (Integer) noteTypeIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE NOTE WHERE NOTE_DESCRIPTION=?,FK_NOTE_TYPE_ID=?"+ "WHERE NOTE_ID =" + currentID);

            statement.setString(1, noteDescriptionCell);
            statement.setInt(2, noteTypeCell);
            statement.execute();
        }

    }
    public void deleteClient() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        if (noteTable.getSelectionModel().isEmpty()) //output an error message if nothing is selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = noteTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) noteIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM NOTE WHERE NOTE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            noteData.remove(noteTable.getSelectionModel().getSelectedIndex()); //update the observable list
            noteTable.setItems(noteData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }
    public void openNoteTypeTable () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Note Type Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Note Type Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
