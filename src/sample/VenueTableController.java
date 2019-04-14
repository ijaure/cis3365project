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

public class VenueTableController {
    public TableView<Venue> venueTable;
    public ObservableList<Venue> venueData = FXCollections.observableArrayList();
    public TableColumn<Venue, Number> venueIDCol;
    public TableColumn<Venue, String> venueNameCol;
    public TableColumn<Venue, Number> venueStatusIDCol;
    public TableColumn<Venue, String> venueaddressCol;
    public TableColumn<Venue, String> venuephoneCol;
    public TableColumn<Venue, String> venueEmailCol;
    public TableColumn<Venue, String> venueWorkHoursCol;
    public TableColumn<Venue, String> venueDeliveryHoursCol;
    public TableColumn<Venue, Number> commission_percentageCol;
    public TableColumn<Venue, Boolean> contractexpirationCol;

    public void initialize() {
//Connect to Database
        Connection c;
        venueIDCol.setCellValueFactory(data -> data.getValue().venue_idProperty());
        venueNameCol.setCellValueFactory(data -> data.getValue().venue_nameProperty());
        venueaddressCol.setCellValueFactory(data -> data.getValue().venue_addressProperty());
        venuephoneCol.setCellValueFactory(data -> data.getValue().venue_phone_numberProperty());
        venueEmailCol.setCellValueFactory(data -> data.getValue().venue_email_addressProperty());
        venueWorkHoursCol.setCellValueFactory(data -> data.getValue().venue_work_hoursProperty());
        venueDeliveryHoursCol.setCellValueFactory(data -> data.getValue().venue_delivery_hoursProperty());
        venueStatusIDCol.setCellValueFactory(data -> data.getValue().fk_venue_status_idProperty());
        contractexpirationCol.setCellValueFactory(data -> data.getValue().contract_expirationProperty());
        commission_percentageCol.setCellValueFactory(data->data.getValue().comission_percentageProperty());
        venueTable.setEditable(true);
        try {
            c = DBClass.connect();
            String SQL = "Select * from VENUE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Venue v = new Venue();
                v.venue_id.set(rs.getInt("VENUE_ID"));
                v.venue_name.set(rs.getString("VENUE_NAME"));
                v.fk_venue_status_id.set(rs.getInt("VENUE_STATUS_ID"));
                v.venue_address.set(rs.getString("VENUE_ADDRESS"));
                v.venue_phone_number.set(rs.getString("VENUE_PHONE_NUMBER"));
                v.venue_email_address.set(rs.getString("VENUE_EMAIL_ADDRESS"));
                v.venue_work_hours.set(rs.getString("VENUE_WORK_HOURS"));
                v.venue_delivery_hours.set(rs.getString("VENUE_DELIVERY_HOURS"));
                v.contract_expiration.set(rs.getBoolean("CONTRACT_EXPIRATION"));
                v.comission_percentage.set(rs.getInt("COMISSION_PERCENTAGE"));



                venueData.add(v);
            }
            venueTable.setItems(venueData);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Venue Data");
        }

    }
    public void openVenueForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Venue Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Venue Form");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openVenueContact(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Venue Contact Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Venue Contact Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openVenueStatus(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Venue Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Venue Status Table");
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


    public void editVenue(){
        venueNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_name(t.getNewValue())
        );
        venueaddressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueaddressCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_address(t.getNewValue())
        );
        venuephoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venuephoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_phone_number(t.getNewValue())
        );
        venueEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_email_address(t.getNewValue())
        );
        venueWorkHoursCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueWorkHoursCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_work_hours(t.getNewValue())
        );
        venueDeliveryHoursCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueDeliveryHoursCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVenue_delivery_hours(t.getNewValue())
        );
        venueStatusIDCol.setCellFactory(TextFieldTableCell.<Venue, Number>forTableColumn(new NumberStringConverter()));
        venueStatusIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_venue_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
        commission_percentageCol.setCellFactory(TextFieldTableCell.<Venue, Number>forTableColumn(new NumberStringConverter()));
        commission_percentageCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setComission_percentage(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
        contractexpirationCol.setCellFactory(TextFieldTableCell.<Venue, Boolean>forTableColumn(new BooleanStringConverter()));
        contractexpirationCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Venue, Boolean> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContract_expiration(t.getNewValue())
        );



    }
    public void saveVenueChanges() throws SQLException{
        Connection c = DBClass.connect();

        if(venueTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = venueTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) venueIDCol.getCellObservableValue(row).getValue(); //collect the selected venues's id

            String venueNameCell = (String) venueNameCol.getCellObservableValue(row).getValue();
            String venueaddressCell = (String) venueaddressCol.getCellObservableValue(row).getValue();
            String venueemailCell = (String) venueEmailCol.getCellObservableValue(row).getValue();
            String venuePhoneCell = (String) venuephoneCol.getCellObservableValue(row).getValue();
            String venueworkHoursCell = (String) venueWorkHoursCol.getCellObservableValue(row).getValue();
            String venueDeliveryHoursCell = (String) venueDeliveryHoursCol.getCellObservableValue(row).getValue();
            Integer venueStatusIDCell = (Integer) venueStatusIDCol.getCellObservableValue(row).getValue();
            Integer venuecommissionpercentageCell = (Integer) commission_percentageCol.getCellObservableValue(row).getValue();
            Boolean contractExpirationCell = (Boolean) contractexpirationCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE VENUE SET VENUE_NAME=?,VENUE_STATUS_ID=?,VENUE_ADDRESS=?,VENUE_PHONE_NUMBER=?,VENUE_EMAIL_ADDRESS=?,VENUE_WORK_HOURS=?,VENUE_DELIVERY_HOURS=?,CONTRACT_EXPIRATION=?,COMISSION_PERCENTAGE=? "+ "WHERE VENUE_ID =" + currentID);
            statement.setString(1, venueNameCell);
            statement.setInt(2,venueStatusIDCell);
            statement.setString(3,venueaddressCell);
            statement.setString(4,venuePhoneCell);
            statement.setString(5,venueemailCell);
            statement.setString(6,venueworkHoursCell);
            statement.setString(7,venueDeliveryHoursCell);
            statement.setBoolean(8,contractExpirationCell);
            statement.setInt(9,venuecommissionpercentageCell);
            statement.execute();
            c.close();

        }

    }
    public void deleteVenue() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(venueTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = venueTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) venueIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM VENUE WHERE VENUE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            venueData.remove(venueTable.getSelectionModel().getSelectedIndex()); //update the observable list
            venueTable.setItems(venueData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }
}
