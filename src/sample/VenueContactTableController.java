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

public class VenueContactTableController {

    public ObservableList<VenueContact> vcData = FXCollections.observableArrayList();
    public TableView<VenueContact> vcTable;
    public TableColumn<VenueContact, Number> vcIDCol;
    public TableColumn<VenueContact, Number> venueIDCol;
    public TableColumn<VenueContact, String> fNameCol;
    public TableColumn<VenueContact, String> lNameCol;
    public TableColumn<VenueContact, String> phoneCol;
    public TableColumn<VenueContact, String> emailCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        vcIDCol.setCellValueFactory(data -> data.getValue().contact_person_idProperty());
        venueIDCol.setCellValueFactory(data -> data.getValue().fk_venue_idProperty());
        fNameCol.setCellValueFactory(data -> data.getValue().contact_person_first_nameProperty());
        lNameCol.setCellValueFactory(data -> data.getValue().contact_person_last_nameProperty());
        phoneCol.setCellValueFactory(data -> data.getValue().contact_person_phoneProperty());
        emailCol.setCellValueFactory(data -> data.getValue().contact_person_emailProperty());

        vcTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from VENUE_CONTACT";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                VenueContact vc = new VenueContact();

                //set the values based on what's in the database
                vc.contact_person_id.set(rs.getInt("CONTACT_PERSON_ID")); //columnLabel should match column name in database
                vc.fk_venue_id.set(rs.getInt("FK_VENUE_ID"));
                vc.contact_person_first_name.set((rs.getString("CONTACT_PERSON_FIRST_NAME")));
                vc.contact_person_last_name.set((rs.getString("CONTACT_PERSON_LAST_NAME")));
                vc.contact_person_phone.set((rs.getString("CONTACT_PERSON_PHONE")));
                vc.contact_person_email.set((rs.getString("CONTACT_PERSON_EMAIL")));

                vcData.add(vc); //add to an observable list
            }
            vcTable.setItems(vcData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Venue Contact Data");
        }
    }

    public void editVenueContact(){
        venueIDCol.setCellFactory(TextFieldTableCell.<VenueContact, Number>forTableColumn(new NumberStringConverter()));
        venueIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueContact, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_venue_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        fNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_person_first_name(t.getNewValue())
        );

        lNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_person_last_name(t.getNewValue())
        );

        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_person_phone(t.getNewValue())
        );

        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<VenueContact, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_person_email(t.getNewValue())
        );

    }

    public void saveVenueContactChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(vcTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = vcTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) vcIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            Integer venueCell = (Integer) venueIDCol.getCellObservableValue(row).getValue();
            String fCell = (String) fNameCol.getCellObservableValue(row).getValue();
            String lCell = (String) lNameCol.getCellObservableValue(row).getValue();
            String phoneCell = (String) phoneCol.getCellObservableValue(row).getValue();
            String emailCell = (String) emailCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE VENUE_CONTACT SET FK_VENUE_ID = ?, CONTACT_PERSON_FIRST_NAME = ?, CONTACT_PERSON_LAST_NAME = ?, CONTACT_PERSON_PHONE = ?, CONTACT_PERSON_EMAIL = ? "
                    + "WHERE CONTACT_PERSON_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, venueCell);
            statement.setString(2, fCell);
            statement.setString(3, lCell);
            statement.setString(4, phoneCell);
            statement.setString(5, emailCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteVenueContact() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(vcTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = vcTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) vcIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM VENUE_CONTACT WHERE CONTACT_PERSON_ID =" + currentID;

            stmt.executeUpdate(SQL);
            vcData.remove(vcTable.getSelectionModel().getSelectedIndex()); //update the observable list
            vcTable.setItems(vcData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openVenueContactForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Venue Contact Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Venue Contact");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
}
