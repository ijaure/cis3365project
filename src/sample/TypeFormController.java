package sample;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeFormController {

    public TextField typeNameInput;
    public TextField typeDescInput; //used for employee type since it has a desc. field

    public void addEmployeeType() throws SQLException {
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(typeNameInput.getText().trim().isEmpty() ||
        typeDescInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            String typeName = typeNameInput.getText();
            String typeDesc = typeDescInput.getText();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EMPLOYEE_TYPE " + "(POSITION_NAME, POSITION_DESCRIPTION) "
                    + "VALUES ('" + typeName + "', '" + typeDesc + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addNoteType() throws SQLException {
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        if(typeNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            String typeName = typeNameInput.getText();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO NOTE_TYPE " + "(NOTE_TYPE_NAME) "
                    + "VALUES ('" + typeName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addProductType() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(typeNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //get the inputted type name
            String typeName = typeNameInput.getText();

            //insert the new type into the type table
            String SQL = "INSERT INTO PRODUCT_TYPE " + "(PRODUCT_TYPE_NAME) "
                    + "VALUES ('" + typeName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }
    }

    public void addPaymentRequirement() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if (typeNameInput.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        } else {
            //get the inputted type name
            String typeName = typeNameInput.getText();

            //insert the new type into the type table
            String SQL = "INSERT INTO PAYMENT_REQUIREMENT " + "(PAYMENT_REQ_NAME) "
                    + "VALUES ('" + typeName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection            }
        }
    }
}
