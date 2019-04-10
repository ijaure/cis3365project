package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;

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


    }


