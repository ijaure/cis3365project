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
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.sql.*;

public class PlannerTableController {
    public TableView<Planner> plannerTable;
    public ObservableList<Planner> plannerData = FXCollections.observableArrayList();
    public TableColumn<Planner, Number> plannerIDCol;
    public TableColumn<Planner, String> PlannerFNameCol;
    public TableColumn<Planner, String> plannerLNameCol;
    public TableColumn<Planner, String> plannerPhoneCol;
    public TableColumn<Planner, String> plannerEmailCol;
    public TableColumn<Planner, Number> venueIDCol;
    public TableColumn<Planner, Boolean> isClientCol;

    public void initialize() {
//Connect to Database
        Connection c;
        plannerIDCol.setCellValueFactory(data -> data.getValue().planner_idProperty());
        PlannerFNameCol.setCellValueFactory(data -> data.getValue().planner_first_nameProperty());
        plannerLNameCol.setCellValueFactory(data -> data.getValue().planner_last_nameProperty());

        plannerPhoneCol.setCellValueFactory(data -> data.getValue().planner_phoneProperty());
        plannerEmailCol.setCellValueFactory(data -> data.getValue().planner_emailProperty());
        venueIDCol.setCellValueFactory(data -> data.getValue().fk_venue_idProperty());
        isClientCol.setCellValueFactory(data -> data.getValue().is_clientProperty());
        plannerTable.setEditable(true);

        try {
            c = DBClass.connect();
            String SQL = "Select * from PLANNER";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Planner p = new Planner();
                p.planner_id.set(rs.getInt("PLANNER_ID"));
                p.planner_first_name.set(rs.getString("PLANNER_FIRST_NAME"));
                p.planner_last_name.set(rs.getString("PLANNER_LAST_NAME"));
                p.planner_phone.set(rs.getString("PLANNER_PHONE"));
                p.planner_email.set(rs.getString("PLANNER_EMAIL"));
                p.is_client.set(rs.getBoolean("IS_CLIENT"));
                p.fk_venue_id.set(rs.getInt("FK_VENUE_ID"));

                plannerData.add(p);
            }
            plannerTable.setItems(plannerData);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Employee Data");
        }
    }
        public void openPlannerForm(){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Planner Form.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Planner Form");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void editPlanner(){
            PlannerFNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            plannerLNameCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, String> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setPlanner_first_name(t.getNewValue())
            );
            plannerLNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            plannerLNameCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, String> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setPlanner_last_name(t.getNewValue())
            );
            plannerPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
            plannerPhoneCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, String> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setPlanner_phone(t.getNewValue())
            );
            plannerEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
            plannerEmailCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, String> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setPlanner_email(t.getNewValue())
            );
            venueIDCol.setCellFactory(TextFieldTableCell.<Planner, Number>forTableColumn(new NumberStringConverter()));
            venueIDCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, Number> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setFk_venue_id(Integer.parseInt(String.valueOf(t.getNewValue())))
            );
            isClientCol.setCellFactory(TextFieldTableCell.<Planner, Boolean>forTableColumn(new BooleanStringConverter()));
            isClientCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<Planner, Boolean> t) ->
                            ( t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setIs_client(t.getNewValue())
            );
        }

        public void savePlannerChanges() throws SQLException {
            Connection c = DBClass.connect();

            if(plannerTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Row Selection");
                alert.setContentText("Please select a row in the table");
                alert.showAndWait();
            }
            else{
                int row = plannerTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
                Integer currentID = (Integer) plannerIDCol.getCellObservableValue(row).getValue(); //collect the selected planner's id
                String plannerFNameCell = (String) PlannerFNameCol.getCellObservableValue(row).getValue();
                String plannerLNameCell = (String) plannerLNameCol.getCellObservableValue(row).getValue();

                String plannerphoneCell = (String) plannerPhoneCol.getCellObservableValue(row).getValue();
                String plannerEmailCell = (String) plannerEmailCol.getCellObservableValue(row).getValue();

                Integer plannerVenueIDcell = (Integer) venueIDCol.getCellObservableValue(row).getValue();

                Boolean isClientCell = (Boolean) isClientCol.getCellObservableValue(row).getValue();

                PreparedStatement statement = c.prepareStatement("UPDATE PLANNER SET PLANNER_FIRST_NAME=?.PLANNER_LAST_NAME=?,PLANNER_PHONE=?,PLANNER_EMAIL=?IS_CLIENT=?,FK_VENUE_ID=?" + "WHERE PLANNER_ID =" + currentID);
                statement.setString(1, plannerFNameCell);
                statement.setString(2, plannerLNameCell);
                statement.setString(3, plannerphoneCell);
                statement.setString(4, plannerEmailCell);
                statement.setBoolean(5, isClientCell);
                statement.setInt(6,plannerVenueIDcell);

                statement.execute();
                c.close();
            }

        }
    public void deletePlanner() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(plannerTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = plannerTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) venueIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PLANNER WHERE PLANNER_ID =" + currentID;

            stmt.executeUpdate(SQL);
            plannerData.remove(plannerTable.getSelectionModel().getSelectedIndex()); //update the observable list
            plannerTable.setItems(plannerData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    }


