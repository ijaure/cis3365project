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
import java.text.CharacterIterator;

public class ClientEventTableController {
    public ObservableList<ClientEvent> ceData = FXCollections.observableArrayList();
    public TableView<ClientEvent> ceTable;
    public TableColumn<ClientEvent, Number> ceIDCol;
    public TableColumn<ClientEvent, Number> clientIDCol;
    public TableColumn<ClientEvent, Number> eventIDCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        ceIDCol.setCellValueFactory(data -> data.getValue().client_event_idProperty());
        clientIDCol.setCellValueFactory(data -> data.getValue().fk_client_idProperty());
        eventIDCol.setCellValueFactory(data -> data.getValue().fk_event_idProperty());

        ceTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from CLIENT_EVENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                ClientEvent ce = new ClientEvent();

                //set the values based on what's in the database
                ce.client_event_id.set(rs.getInt("CLIENT_EVENT_ID")); //columnLabel should match column name in database
                ce.fk_client_id.set(rs.getInt("CLIENT_ID"));
                ce.fk_event_id.set((rs.getInt("EVENT_ID")));

                ceData.add(ce); //add to an observable list
            }
            ceTable.setItems(ceData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Client Event Data");
        }


    }

    public void editClientEvent(){
        clientIDCol.setCellFactory(TextFieldTableCell.<ClientEvent, Number>forTableColumn(new NumberStringConverter()));
        clientIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ClientEvent, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_client_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventIDCol.setCellFactory(TextFieldTableCell.<ClientEvent, Number>forTableColumn(new NumberStringConverter()));
        eventIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ClientEvent, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveClientEventChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(ceTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = ceTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) ceIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer clientCell = (Integer) clientIDCol.getCellObservableValue(row).getValue();
            Integer eventCell = (Integer) eventIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE CLIENT_EVENT SET CLIENT_ID = ?, " +
                    "EVENT_ID = ? "
                    + "WHERE CLIENT_EVENT_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, clientCell);
            statement.setInt(2, eventCell);
            statement.execute();
            c.close();
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
}
