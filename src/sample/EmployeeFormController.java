package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmployeeFormController {

    public ObservableList<EmployeeStatus> employeeStatusData = FXCollections.observableArrayList();
    public ObservableList<EmployeeTaxInfo> employeeTaxInfoData = FXCollections.observableArrayList();
    public ObservableList<EmployeeType> employeeTypeData = FXCollections.observableArrayList();

    public TextField empFirstNameInput;
    public TextField empLastNameInput;
    public TextField empGenderInput;
    public TextField empDOBInput;
    public TextField empCitizenInput;
    public TextField empSSinput;
    public TextField empAddressInput;
    public TextField empStartDateInput;
    public TextField empStartSalaryInput;
    public TextField empCurrentSalaryInput;
    public TextField empTABCExpInput;
    public TextField empPhoneInput;
    public TextField empEmailInput;

    public CheckBox empMarriedTrue;
    public CheckBox empMarriedFalse;
    public CheckBox empCertifiedTrue;
    public CheckBox empOnFileTrue;

    public ComboBox<EmployeeStatus> empStatusList;
    public ComboBox<EmployeeTaxInfo> empTaxList;
    public ComboBox<EmployeeType> empTypeList;

    public void initialize(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from EMPLOYEE_TYPE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                EmployeeType et = new EmployeeType();

                //assign an ID and Status Name from the database
                et.employee_type_id.set(rs.getInt("EMPLOYEE_TYPE_ID"));
                et.position_name.set(rs.getString("POSITION_NAME"));
                et.position_description.set(rs.getString("POSITION_DESCRIPTION"));

                employeeTypeData.add(et); //add these to an observable list
            }
            empTypeList.setItems(employeeTypeData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Employee Type Data");
        }

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from EMPLOYEE_TAX_INFORMATION";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                EmployeeTaxInfo eti = new EmployeeTaxInfo();

                //assign an ID and Status Name from the database
                eti.employee_tax_id.set(rs.getInt("EMPLOYEE_TAX_ID"));
                eti.tax_form.set(rs.getString("TAX_FORM"));

                employeeTaxInfoData.add(eti); //add these to an observable list
            }
            empTaxList.setItems(employeeTaxInfoData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Employee Tax Data");
        }

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from EMPLOYEE_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                EmployeeStatus es = new EmployeeStatus();

                //assign an ID and Status Name from the database
                es.employee_status_id.set(rs.getInt("EMPLOYEE_STATUS_ID"));
                es.employee_status_name.set(rs.getString("EMPLOYEE_STATUS_NAME"));

                employeeStatusData.add(es); //add these to an observable list
            }
            empStatusList.setItems(employeeStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Employee Status Data");
        }

    }

    public void addEmployee() throws SQLException, ClassNotFoundException, ParseException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy"); //used the parse the textfield input to a Date


        if (empFirstNameInput.getText().trim().isEmpty() ||
                empLastNameInput.getText().trim().isEmpty() ||
                empAddressInput.getText().trim().isEmpty() ||
                empPhoneInput.getText().trim().isEmpty() ||
                empEmailInput.getText().trim().isEmpty() ||
                empDOBInput.getText().trim().isEmpty() ||
                empGenderInput.getText().trim().isEmpty() ||
                empTypeList.getSelectionModel().isEmpty() ||
                empStatusList.getSelectionModel().isEmpty() ||
                empSSinput.getText().trim().isEmpty() ||
                empStartDateInput.getText().trim().isEmpty() ||
                empStartSalaryInput.getText().trim().isEmpty() ||
                empCurrentSalaryInput.getText().trim().isEmpty() ||
                empTABCExpInput.getText().trim().isEmpty() ||
                empCitizenInput.getText().trim().isEmpty() ||
                empTaxList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();

        } else {
            String empFirst = empFirstNameInput.getText();
            String empLast = empLastNameInput.getText();
            String empAddress = empAddressInput.getText();
            String empPhone = empPhoneInput.getText();
            String empEmail = empEmailInput.getText();

            java.util.Date empDOB = formatter.parse(empDOBInput.getText());
            java.sql.Date empDOBSQL = new java.sql.Date(empDOB.getTime());

            String empGender = empGenderInput.getText();
            Integer empTypeID = empTypeList.getSelectionModel().getSelectedItem().getEmployee_type_id();
            Integer empStatusID = empStatusList.getSelectionModel().getSelectedItem().getEmployee_status_id();
            String empSS = empSSinput.getText();

            java.util.Date empStartDate = formatter.parse(empStartDateInput.getText());
            java.sql.Date empStartDateSQL = new java.sql.Date(empStartDate.getTime());

            Double empStartSalary = Double.parseDouble(empStartSalaryInput.getText());
            Double empCurrentSalary = Double.parseDouble(empCurrentSalaryInput.getText());

            Boolean empCertifiedSelection = false;
            Boolean empMarried = false;
            Boolean empOnFile = false;
            if(empCertifiedTrue.isSelected()) {
                empCertifiedSelection = true;
            }
            if(empMarriedTrue.isSelected()){
                empMarried = true;
            }
            if(empMarriedFalse.isSelected()){
                empMarried = false;
            }
            if(empOnFileTrue.isSelected()){
                empOnFile = true;
            }

            java.util.Date empTABCExpDate = formatter.parse(empTABCExpInput.getText());
            java.sql.Date empTABCExpDateSQL = new java.sql.Date(empTABCExpDate.getTime());

            String empCitizen = empCitizenInput.getText();
            Integer empTaxID = empTaxList.getSelectionModel().getSelectedItem().getEmployee_tax_id();

            String SQL = "INSERT INTO EMPLOYEE " + "(EMPLOYEE_FIRST_NAME, EMPLOYEE_LAST_NAME, EMPLOYEE_ADDRESS, EMPLOYEE_PHONE_NUMBER, EMPLOYEE_EMAIL, DATE_OF_BIRTH, GENDER, FK_EMPLOYEE_TYPE_ID, FK_EMPLOYEE_STATUS_ID, SOCIAL_SECURITY, START_DATE, STARTING_SALARY, CURRENT_SALARY, TABC_CERTIFIED, TABC_CERT_EXPIRATION, CITIZENSHIP, MARITAL_STATUS, JOB_APPLICATION_ON_FILE, FK_EMPLOYEE_TAX_ID) "
                    + "VALUES ('" + empFirst + "', '" + empLast + "', '" + empAddress + "', '"
                    + empPhone + "', '" + empEmail + "', '" + empDOBSQL + "', '" + empGender + "', '" +
                    empTypeID + "', '" + empStatusID + "', '" + empSS + "', '" + empStartDateSQL + "', '" + empStartSalary + "', '" + empCurrentSalary +
                    "', '" + empCertifiedSelection + "', '" + empTABCExpDateSQL + "', '" + empCitizen + "', '" + empMarried + "', '" + empOnFile
                    + "', '" + empTaxID + "')";

            stmt.executeUpdate(SQL); //execute the sql statement
            c.close(); //close the connection

        }
    }

}
