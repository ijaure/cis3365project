package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeFormController {

    public TextField typeNameInput;

    public void addEmployeeType(){
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
}
