package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class VenueFormController {
    public ObservableList<VenueStatus> venuestatusData = FXCollections.observableArrayList();
    public TextField venueNameInput;
    public TextField venueAddressInput;
    public TextField venuePhoneInput;
    public TextField venueEmailInput;
    public TextField venueworkhoursInput;
    public TextField venuedeliveryhoursInput;
    public CheckBox contractExpirationInput;
    public TextField commissionPercentageInput;
    public ComboBox<VenueStatus> venuestatusList;

    public void initialize() {
        Connection c;
        try {
            c = DBClass.connect();
            String SQL = "SELECT * from VENUE_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while (rs.next()) {
                VenueStatus venuesstatus = new VenueStatus();

                //assign an ID Name from the database
                venuesstatus.venue_status_id.set(rs.getInt("VENUE_STATUS_ID"));
                venuesstatus.venue_status_name.set(rs.getString("VENUE_STATUS_NAME"));


                venuestatusData.add(venuesstatus); //add these to an observable list
            }
            venuestatusList.setItems(venuestatusData); //set the ComboBox values to the observable list
            c.close();
        } catch (Exception e) { //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Order Client Combobox Data");
        }

    }
    public void addVenue() throws SQLException, ParseException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        if (venueNameInput.getText().trim().isEmpty() ||
                venuePhoneInput.getText().trim().isEmpty() ||
                venueEmailInput.getText().trim().isEmpty() ||
                venueAddressInput.getText().trim().isEmpty() ||
                venuedeliveryhoursInput.getText().trim().isEmpty() ||
                venueworkhoursInput.getText().trim().isEmpty() ||
                commissionPercentageInput.getText().trim().isEmpty() ||
                venuestatusList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else{
            String venueName=venueNameInput.getText();
            String venuePhone=venuePhoneInput.getText();
            String venueEmail=venueEmailInput.getText();
            String venueAddress=venueAddressInput.getText();
            String venueWorkHours=venueworkhoursInput.getText();
            String venueDeliveryHours=venuedeliveryhoursInput.getText();
            Integer venueStatusID=venuestatusList.getSelectionModel().getSelectedItem().getVenue_status_id();
            Integer commissionPercentage=Integer.parseInt(commissionPercentageInput.getText());
            Boolean contractExpiration=false;
            if(contractExpirationInput.isSelected()){
                contractExpiration=true;
            }
            String SQL= "INSERT INTO VENUE "+ "(VENUE_NAME, VENUE_STATUS_ID,VENUE_ADDRESS,VENUE_PHONE_NUMBER,VENUE_EMAIL_ADDRESS,VENUE_WORK_HOURS,VENUE_DELIVERY_HOURS,CONTRACT_EXPIRATION,COMISSION_PERCENTAGE)" +" VALUES('"+venueName +"', '"+venueStatusID +"', '"+ venueAddress +"', '"+venuePhone +"', '"+venueEmail+"','"+venueWorkHours+"','"+venueDeliveryHours+"','"+contractExpiration+"','"+commissionPercentage+"')";
            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection

        }

    }
}
