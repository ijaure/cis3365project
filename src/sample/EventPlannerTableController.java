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

public class EventPlannerTableController {
    public ObservableList<EventPlanner> epData = FXCollections.observableArrayList();
    public TableView<EventPlanner> epTable;
    public TableColumn<EventPlanner, Number> epIDCol;
    public TableColumn<EventPlanner, Number> plannerIDCol;
    public TableColumn<EventPlanner, Number> eventIDCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        epIDCol.setCellValueFactory(data -> data.getValue().event_planner_idProperty());
        plannerIDCol.setCellValueFactory(data -> data.getValue().fk_planner_idProperty());
        eventIDCol.setCellValueFactory(data -> data.getValue().fk_event_idProperty());

        epTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from EVENT_PLANNER";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                EventPlanner ep = new EventPlanner();

                //set the values based on what's in the database
                ep.event_planner_id.set(rs.getInt("EVENT_PLANNER_ID")); //columnLabel should match column name in database
                ep.fk_planner_id.set(rs.getInt("PLANNER_ID"));
                ep.fk_event_id.set((rs.getInt("EVENT_ID")));

                epData.add(ep); //add to an observable list
            }
            epTable.setItems(epData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Event Planner Data");
        }


    }

    public void editEventPlanner(){
        plannerIDCol.setCellFactory(TextFieldTableCell.<EventPlanner, Number>forTableColumn(new NumberStringConverter()));
        plannerIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventPlanner, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_planner_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        eventIDCol.setCellFactory(TextFieldTableCell.<EventPlanner, Number>forTableColumn(new NumberStringConverter()));
        eventIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EventPlanner, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_event_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveEventPlannerChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(epTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = epTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) epIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer plannerCell = (Integer) plannerIDCol.getCellObservableValue(row).getValue();
            Integer eventCell = (Integer) eventIDCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE EVENT_PLANNER SET PLANNER_ID = ?, " +
                    "EVENT_ID = ? "
                    + "WHERE EVENT_PLANNER_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, plannerCell);
            statement.setInt(2, eventCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteEventPlanner() throws SQLException {
//get the connection
        Connection c = DBClass.connect();

        Statement stmt = c.createStatement();

        if (epTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = epTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) epIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EVENT_PLANNER WHERE EVENT_PLANNER_ID =" + currentID;

            stmt.executeUpdate(SQL);
            epData.remove(epTable.getSelectionModel().getSelectedIndex()); //update the observable list
            epTable.setItems(epData); //update the tableview so the deletion shows immediately
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

    public void openPlannerTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Planner Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Planner Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
