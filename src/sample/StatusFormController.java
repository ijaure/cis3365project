package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StatusFormController {

    public TextField statusNameInput;

    public void addEmployeeStatus() throws SQLException {
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO EMPLOYEE_STATUS " + "(EMPLOYEE_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addClientStatus() throws SQLException {
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO CLIENT_STATUS " + "(CLIENT_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addOrderStatus() throws SQLException {
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO ORDER_STATUS " + "(ORDER_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addProductStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO PRODUCT_STATUS " + "(PRODUCT_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addOrderLineStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO ORDER_LINE_STATUS " + "(ORDER_LINE_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addVenueStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO VENUE_STATUS " + "(VENUE_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addEventStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO EVENT_STATUS " + "(EVENT_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }

    public void addVendorStatus() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(statusNameInput.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            String statusName = statusNameInput.getText();

            String SQL = "INSERT INTO VENDOR_STATUS " + "(VENDOR_STATUS_NAME) "
                    + "VALUES ('" + statusName + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }
    }

}
