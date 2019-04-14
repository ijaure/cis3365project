package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EmergencyContactFormController {

    public ObservableList<Employee> employeeListData = FXCollections.observableArrayList();
    public ComboBox<Employee> employeeList;

    public TextField empContactFName;
    public TextField empContactLName;
    public TextField empContactPhone;
    public TextField empContactEmail;




    public void initialize(){
        Connection c;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DBClass.connect();
            String SQL = "Select * from EMPLOYEE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Employee e = new Employee();

                //set the values for this new Vendor based on what's in the database
                e.employee_id.set(rs.getInt("EMPLOYEE_ID")); //columnLabel should match column name in database
                e.employee_first_name.set(rs.getString("EMPLOYEE_FIRST_NAME"));
                e.employee_last_name.set((rs.getString("EMPLOYEE_LAST_NAME")));

                employeeListData.add(e); //add the new Vendor to an observable list
            }
            employeeList.setItems(employeeListData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Employee Data");
        }

    }

    public void addEmpContact() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();
        //output an error if any of the fields are empty
        if(employeeList.getSelectionModel().isEmpty() ||
                empContactFName.getText().trim().isEmpty() ||
                empContactLName.getText().trim().isEmpty() ||
                empContactPhone.getText().trim().isEmpty() ||
                empContactEmail.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }
        else {
            //collect all the values from the textfields
            Integer empFKID = employeeList.getSelectionModel().getSelectedItem().getEmployee_id();
            String FName = empContactFName.getText();
            String LName = empContactLName.getText();
            String phone = empContactPhone.getText();
            String email = empContactEmail.getText();

            //insert all of these values to the db, make sure they are in the same order as in the db
            String SQL = "INSERT INTO EMPLOYEE_EMERGENCY_CONTACT " + "(EMPLOYEE_ID, EMERGENCY_CONTACT_FIRST_NAME, EMERGENCY_CONTACT_LAST_NAME, EMERGENCY_CONTACT_PHONE, EMERGENCY_CONTACT_EMAIL) "
                    + "VALUES ('" + empFKID + "', '" + FName + "', '" + LName + "', '"
                    + phone + "', '" + email + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection
        }

    }


}
