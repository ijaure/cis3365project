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
    public TextField typeDescInput;

    public void addEmployeeType() throws SQLException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
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

    public void addNoteType(){
        if(typeNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            System.out.println("Code for new type goes here");
        }

    }

    public void addProductType() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
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
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
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
                System.out.println("Code for adding a new payment requirement goes here");
            }
        }
}
