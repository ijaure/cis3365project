package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.NumberStringConverter;

import javax.management.NotCompliantMBeanException;
import java.sql.*;
import java.util.Date;

public class EmployeeTableController {

    public ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    public TableView<Employee> employeeTable;
    public TableColumn<Employee, Number> empIDCol;
    public TableColumn<Employee, String> empFNameCol;
    public TableColumn<Employee, String> empLNameCol;
    public TableColumn<Employee, String> empAddrCol;
    public TableColumn<Employee, String> empPhoneCol;
    public TableColumn<Employee, String> empEmailCol;
    public TableColumn<Employee, Date> empDOBCol;
    public TableColumn<Employee, String> empGenderCol;
    public TableColumn<Employee, Number> empTypeCol;
    public TableColumn<Employee, Number> empStatusCol;
    public TableColumn<Employee, String> empSSCol;
    public TableColumn<Employee, Date> empStartDateCol;
    public TableColumn<Employee, Number> empStartSalaryCol;
    public TableColumn<Employee, Number> empCurrentSalaryCol;
    public TableColumn<Employee, Boolean> empTABCCertCol;
    public TableColumn<Employee, Date> empTABCExpCol;
    public TableColumn<Employee, String> empCitizenCol;
    public TableColumn<Employee, Boolean> empMaritalCol;
    public TableColumn<Employee, Boolean> empAppFileCol;
    public TableColumn<Employee, Number> empTaxCol;



    public void initialize(){
//Connect to Database
        Connection c;

        //assign columns to the property methods in the Vendor class
        empIDCol.setCellValueFactory(data -> data.getValue().employee_idProperty());
        empFNameCol.setCellValueFactory(data -> data.getValue().employee_first_nameProperty());
        empLNameCol.setCellValueFactory(data -> data.getValue().employee_last_nameProperty());
        empAddrCol.setCellValueFactory(data -> data.getValue().employee_addressProperty());
        empPhoneCol.setCellValueFactory(data -> data.getValue().employee_phone_numberProperty());
        empEmailCol.setCellValueFactory(data -> data.getValue().employee_emailProperty());
        empDOBCol.setCellValueFactory(data -> data.getValue().date_of_birthProperty());
        empGenderCol.setCellValueFactory(data -> data.getValue().genderProperty());
        empTypeCol.setCellValueFactory(data -> data.getValue().fk_employee_type_idProperty());
        empStatusCol.setCellValueFactory(data -> data.getValue().fk_employee_status_idProperty());
        empSSCol.setCellValueFactory(data -> data.getValue().social_securityProperty());
        empStartDateCol.setCellValueFactory(data -> data.getValue().start_dateProperty());
        empStartSalaryCol.setCellValueFactory(data -> data.getValue().starting_salaryProperty());
        empCurrentSalaryCol.setCellValueFactory(data -> data.getValue().current_salaryProperty());
        empTABCCertCol.setCellValueFactory(data -> data.getValue().tabc_certifiedProperty());
        empTABCExpCol.setCellValueFactory(data -> data.getValue().tabc_cert_expirationProperty());
        empCitizenCol.setCellValueFactory(data -> data.getValue().citizenshipProperty());
        empMaritalCol.setCellValueFactory(data -> data.getValue().marital_statusProperty());
        empAppFileCol.setCellValueFactory(data -> data.getValue().job_application_on_fileProperty());
        empTaxCol.setCellValueFactory(data -> data.getValue().fk_employee_tax_idProperty());

        employeeTable.setEditable(true); //so we can edit rows later

        //this try/catch loads data from the database into the tableview
        try{
            c = DBClass.connect();
            String SQL = "Select * from EMPLOYEE";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Employee e = new Employee();

                //set the values for this new Vendor based on what's in the database
                e.employee_id.set(rs.getInt("EMPLOYEE_ID")); //columnLabel should match column name in database
                e.employee_first_name.set(rs.getString("EMPLOYEE_FIRST_NAME"));
                e.employee_last_name.set((rs.getString("EMPLOYEE_LAST_NAME")));
                e.employee_address.set(rs.getString("EMPLOYEE_ADDRESS"));

                e.employee_phone_number.set((rs.getString("EMPLOYEE_PHONE_NUMBER")));
                e.employee_email.set(rs.getString("EMPLOYEE_EMAIL"));
                e.date_of_birth.set(rs.getDate("DATE_OF_BIRTH"));
                e.gender.set(rs.getString("GENDER"));
                e.fk_employee_type_id.set(rs.getInt("FK_EMPLOYEE_TYPE_ID"));
                e.fk_employee_status_id.set(rs.getInt("FK_EMPLOYEE_STATUS_ID"));
                e.social_security.set(rs.getString("SOCIAL_SECURITY"));

                e.start_date.set((rs.getDate("START_DATE")));
                e.starting_salary.set(rs.getDouble("STARTING_SALARY"));
                e.current_salary.set((rs.getDouble("CURRENT_SALARY")));
                e.tabc_certified.set((rs.getBoolean("TABC_CERTIFIED")));
                e.tabc_cert_expiration.set((rs.getDate("TABC_CERT_EXPIRATION")));
                e.citizenship.set((rs.getString("CITIZENSHIP")));
                e.marital_status.set((rs.getBoolean("MARITAL_STATUS")));
                e.job_application_on_file.set((rs.getBoolean("JOB_APPLICATION_ON_FILE")));
                e.fk_employee_tax_id.set((rs.getInt("FK_EMPLOYEE_TAX_ID")));

                employeeData.add(e); //add the new Vendor to an observable list
            }
            employeeTable.setItems(employeeData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Employee Data");
        }
    }

    public void editEmployee(){
        //turn the cells into editable text fields
        empFNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empFNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_first_name(t.getNewValue())
        );

        empLNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empLNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_last_name(t.getNewValue())
        );

        empAddrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empAddrCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_address(t.getNewValue())
        );

        empPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empPhoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_phone_number(t.getNewValue())
        );

        empEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmployee_email(t.getNewValue())
        );

        empDOBCol.setCellFactory(TextFieldTableCell.<Employee, Date>forTableColumn(new DateStringConverter()));
        empDOBCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDate_of_birth(t.getNewValue())
        );

        empGenderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empGenderCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setGender(t.getNewValue())
        );

        empTypeCol.setCellFactory(TextFieldTableCell.<Employee, Number>forTableColumn(new NumberStringConverter()));
        empTypeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_employee_type_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        empStatusCol.setCellFactory(TextFieldTableCell.<Employee, Number>forTableColumn(new NumberStringConverter()));
        empStatusCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_employee_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        empSSCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empSSCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSocial_security(t.getNewValue())
        );

        empStartDateCol.setCellFactory(TextFieldTableCell.<Employee, Date>forTableColumn(new DateStringConverter()));
        empStartDateCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setStart_date(t.getNewValue())
        );

        empStartSalaryCol.setCellFactory(TextFieldTableCell.<Employee, Number>forTableColumn(new NumberStringConverter()));
        empStartSalaryCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setStarting_salary(Double.parseDouble(String.valueOf(t.getNewValue())))
        );

        empCurrentSalaryCol.setCellFactory(TextFieldTableCell.<Employee, Number>forTableColumn(new NumberStringConverter()));
        empCurrentSalaryCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCurrent_salary(Double.parseDouble(String.valueOf(t.getNewValue())))
        );

        empTABCCertCol.setCellFactory(TextFieldTableCell.<Employee, Boolean>forTableColumn(new BooleanStringConverter()));
        empTABCCertCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Boolean> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTabc_certified(t.getNewValue())
        );

        empTABCExpCol.setCellFactory(TextFieldTableCell.<Employee, Date>forTableColumn(new DateStringConverter()));
        empTABCExpCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Date> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTabc_cert_expiration(t.getNewValue())
        );

        empCitizenCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empCitizenCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCitizenship(t.getNewValue())
        );

        empMaritalCol.setCellFactory(TextFieldTableCell.<Employee, Boolean>forTableColumn(new BooleanStringConverter()));
        empMaritalCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Boolean> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMarital_status(t.getNewValue())
        );

        empAppFileCol.setCellFactory(TextFieldTableCell.<Employee, Boolean>forTableColumn(new BooleanStringConverter()));
        empAppFileCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Boolean> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setJob_application_on_file(t.getNewValue())
        );

        empTaxCol.setCellFactory(TextFieldTableCell.<Employee, Number>forTableColumn(new NumberStringConverter()));
        empTaxCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Employee, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_employee_tax_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

    }

    public void saveEmpChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(employeeTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = employeeTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) empIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            //collect each cell's value in a variable
            String empFNameCell = (String) empFNameCol.getCellObservableValue(row).getValue();
            String empLNameCell = (String) empLNameCol.getCellObservableValue(row).getValue();
            String empAddrCell = (String) empAddrCol.getCellObservableValue(row).getValue();
            String empPhoneCell = (String) empPhoneCol.getCellObservableValue(row).getValue();
            String empEmailCell = (String) empEmailCol.getCellObservableValue(row).getValue();

            java.util.Date dobCell = empDOBCol.getCellObservableValue(row).getValue();
            java.sql.Date dobCellSQL = new java.sql.Date(dobCell.getTime());

            String empGenderCell = (String) empGenderCol.getCellObservableValue(row).getValue();
            Integer empTypeCell = (Integer) empTypeCol.getCellObservableValue(row).getValue();
            Integer empStatusCell = (Integer) empStatusCol.getCellObservableValue(row).getValue();
            String empSSCell = (String) empSSCol.getCellObservableValue(row).getValue();

            java.util.Date startDateCell = empDOBCol.getCellObservableValue(row).getValue();
            java.sql.Date startDateCellSQL = new java.sql.Date(startDateCell.getTime());

            Double empStartSalaryCell = (Double) empStartSalaryCol.getCellObservableValue(row).getValue();
            Double empCurrentSalaryCell = (Double) empCurrentSalaryCol.getCellObservableValue(row).getValue();
            Boolean tabcCertCell = (Boolean) empTABCCertCol.getCellObservableValue(row).getValue();

            java.util.Date tabcCertExpCell = empTABCExpCol.getCellObservableValue(row).getValue();
            java.sql.Date tabcCertExpCellSQL = new java.sql.Date(tabcCertExpCell.getTime());

            String citizenCell = (String) empCitizenCol.getCellObservableValue(row).getValue();
            Boolean maritalCell = (Boolean) empMaritalCol.getCellObservableValue(row).getValue();
            Boolean onFileCell = (Boolean) empAppFileCol.getCellObservableValue(row).getValue();
            Integer empTaxCell = (Integer) empTaxCol.getCellObservableValue(row).getValue();

            // SQL statement to update the vendor, put question marks after each = sign,
            // you'll replace these with the variables in the next step
            PreparedStatement statement = c.prepareStatement("UPDATE EMPLOYEE SET EMPLOYEE_FIRST_NAME = ?, EMPLOYEE_LAST_NAME = ?, " +
                    "EMPLOYEE_ADDRESS = ?, EMPLOYEE_PHONE_NUMBER = ?, EMPLOYEE_EMAIL = ?, " +
                    "DATE_OF_BIRTH = ?, GENDER = ?, FK_EMPLOYEE_TYPE_ID = ?, FK_EMPLOYEE_STATUS_ID = ?, " +
                    "SOCIAL_SECURITY = ?, START_DATE = ?, STARTING_SALARY = ?, CURRENT_SALARY = ?, " +
                    "TABC_CERTIFIED = ?, TABC_CERT_EXPIRATION = ?, CITIZENSHIP = ?, " +
                    "MARITAL_STATUS = ?, JOB_APPLICATION_ON_FILE = ?, FK_EMPLOYEE_TAX_ID = ? " + "WHERE EMPLOYEE_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, empFNameCell);
            statement.setString(2, empLNameCell);
            statement.setString(3, empAddrCell);
            statement.setString(4, empPhoneCell);
            statement.setString(5, empEmailCell);
            statement.setDate(6, dobCellSQL);
            statement.setString(7, empGenderCell);
            statement.setInt(8, empTypeCell);
            statement.setInt(9, empStatusCell);
            statement.setString(10, empSSCell);
            statement.setDate(11, startDateCellSQL);
            statement.setDouble(12, empStartSalaryCell);
            statement.setDouble(13, empCurrentSalaryCell);
            statement.setBoolean(14, tabcCertCell);
            statement.setDate(15, tabcCertExpCellSQL);
            statement.setString(16, citizenCell);
            statement.setBoolean(17, maritalCell);
            statement.setBoolean(18, onFileCell);
            statement.setInt(19, empTaxCell);
            statement.execute();
            c.close();
        }


    }

    public void deleteEmployee() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(employeeTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = employeeTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) empIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID =" + currentID;

            stmt.executeUpdate(SQL);
            employeeData.remove(employeeTable.getSelectionModel().getSelectedIndex()); //update the observable list
            employeeTable.setItems(employeeData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openEmployeeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openEmpStatusTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Status Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Status Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEmpTypeTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Type Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Type Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openEmpContactTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Emergency Contact Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Emergency Contact Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openTaxInfoTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Employee Tax Info Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employee Tax Info Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
