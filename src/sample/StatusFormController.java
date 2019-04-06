package sample;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StatusFormController {

    public TextField statusNameInput;

    public void addVendorStatus() throws SQLException {
        //get the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        String statusName = statusNameInput.getText();

        String SQL = "INSERT INTO VENDOR_STATUS " + "(VENDOR_STATUS_NAME) "
                + "VALUES ('" + statusName + "')";

        stmt.executeUpdate(SQL); //execute the sql statement
        c.close(); //close the connection
    }

}
