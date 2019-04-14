package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VenueContactFormController {
    public ObservableList<Venue> venueData = FXCollections.observableArrayList();
    public ComboBox<Venue> venueList;

    public TextField fNameInput;
    public TextField lNameInput;
    public TextField phoneInput;
    public TextField emailInput;

    public void initialize(){
        Connection c;

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from VENUE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Venue ve = new Venue();

                //assign an ID Name from the database
                ve.venue_id.set(rs.getInt("VENUE_ID"));
                ve.venue_name.set(rs.getString("VENUE_NAME"));

                venueData.add(ve); //add these to an observable list
            }
            venueList.setItems(venueData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Venue Contact Combobox Data");
        }

    }

    public void addVenueContact() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        //output an error if any of the fields are empty
        if(venueList.getSelectionModel().isEmpty() ||
                fNameInput.getText().trim().isEmpty() ||
                lNameInput.getText().trim().isEmpty() ||
                phoneInput.getText().trim().isEmpty() ||
                emailInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer venue = venueList.getSelectionModel().getSelectedItem().getVenue_id();
            String fName = fNameInput.getText();
            String lName = lNameInput.getText();
            String phone = phoneInput.getText();
            String email = emailInput.getText();


            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO VENUE_CONTACT " + "(VENUE_ID, CONTACT_PERSON_FIRST_NAME, CONTACT_PERSON_LAST_NAME, CONTACT_PERSON_PHONE, CONTACT_PERSON_EMAIL) "
                    + "VALUES ('" + venue + "', '" + fName + "', '" + lName + "', '" + phone + "', '" + email + "')";

            stmt.executeUpdate(SQL);
            c.close();
        }

    }

}
