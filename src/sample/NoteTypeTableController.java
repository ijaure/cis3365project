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

public class NoteTypeTableController {

    public ObservableList<NoteType> ntData = FXCollections.observableArrayList();

    public TableView<NoteType> ntTable;
    public TableColumn<NoteType, Number> ntIDCol;
    public TableColumn<NoteType, String> ntNameCol;

    public void initialize(){
        Connection c;

        ntIDCol.setCellValueFactory(data -> data.getValue().note_type_idProperty());
        ntNameCol.setCellValueFactory(data -> data.getValue().note_type_nameProperty());

        ntTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from NOTE_TYPE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                NoteType nt = new NoteType();

                //set the values for this new Vendor based on what's in the database
                nt.note_type_id.set(rs.getInt("NOTE_TYPE_ID")); //columnLabel should match column name in database
                nt.note_type_name.set(rs.getString("NOTE_TYPE_NAME"));

                ntData.add(nt); //add the new Vendor to an observable list
            }
            ntTable.setItems(ntData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Note Type Data");
        }
    }

    public void editNoteType(){
        ntNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ntNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<NoteType, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNote_type_name(t.getNewValue())
        );

    }

    public void saveNoteTypeChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(ntTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = ntTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) ntIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) ntNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE NOTE_TYPE SET NOTE_TYPE_NAME = ? "
                    + "WHERE NOTE_TYPE_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }
    }

    public void deleteNoteType() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(ntTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = ntTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) ntIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM NOTE_TYPE WHERE NOTE_TYPE_ID =" +currentID;

            stmt.executeUpdate(SQL);
            ntData.remove(ntTable.getSelectionModel().getSelectedIndex()); //update the observable list
            ntTable.setItems(ntData); //update the tableview so the deletion shows immediately
            c.close();
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
