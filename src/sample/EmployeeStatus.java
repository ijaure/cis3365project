package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeStatus {

    IntegerProperty employee_status_id = new SimpleIntegerProperty();
    StringProperty employee_status_name = new SimpleStringProperty();

    public EmployeeStatus() {
    }

    public EmployeeStatus(IntegerProperty employee_status_id, StringProperty employee_status_name) {
        this.employee_status_id = employee_status_id;
        this.employee_status_name = employee_status_name;
    }

    public int getEmployee_status_id() {
        return employee_status_id.get();
    }

    public IntegerProperty employee_status_idProperty() {
        return employee_status_id;
    }

    public void setEmployee_status_id(int employee_status_id) {
        this.employee_status_id.set(employee_status_id);
    }

    public String getEmployee_status_name() {
        return employee_status_name.get();
    }

    public StringProperty employee_status_nameProperty() {
        return employee_status_name;
    }

    public void setEmployee_status_name(String employee_status_name) {
        this.employee_status_name.set(employee_status_name);
    }

    @Override
    public String toString(){
        return employee_status_id.get() + ", " + employee_status_name.get();
    }
}
