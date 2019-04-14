package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeType {

    IntegerProperty employee_type_id = new SimpleIntegerProperty();
    StringProperty position_name = new SimpleStringProperty();

    public EmployeeType() {
    }

    public EmployeeType(IntegerProperty employee_type_id, StringProperty position_name) {
        this.employee_type_id = employee_type_id;
        this.position_name = position_name;
    }

    public int getEmployee_type_id() {
        return employee_type_id.get();
    }

    public IntegerProperty employee_type_idProperty() {
        return employee_type_id;
    }

    public void setEmployee_type_id(int employee_type_id) {
        this.employee_type_id.set(employee_type_id);
    }

    public String getPosition_name() {
        return position_name.get();
    }

    public StringProperty position_nameProperty() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name.set(position_name);
    }

    @Override
    public String toString(){
        return employee_type_id.get() + ", " + position_name.get();
    }
}

