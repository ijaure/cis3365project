package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeTaxInfoFormController {

    public TextField taxFormInput;

    public void addTaxInfo() throws SQLException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        if(taxFormInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            String taxName = taxFormInput.getText();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EMPLOYEE_TAX_INFORMATION " + "(TAX_FORM) "
                    + "VALUES ('" + taxName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }
}
