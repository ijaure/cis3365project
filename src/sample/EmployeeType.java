package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeType {

    IntegerProperty employee_type_id = new SimpleIntegerProperty();
    StringProperty position_name = new SimpleStringProperty();
    StringProperty position_description = new SimpleStringProperty();

    public EmployeeType() {
    }

    public EmployeeType(IntegerProperty employee_type_id, StringProperty position_name, StringProperty position_description) {
        this.employee_type_id = employee_type_id;
        this.position_name = position_name;
        this.position_description = position_description;
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

    public String getPosition_description() {
        return position_description.get();
    }

    public StringProperty position_descriptionProperty() {
        return position_description;
    }

    public void setPosition_description(String position_description) {
        this.position_description.set(position_description);
    }

    @Override
    public String toString(){
        return employee_type_id.get() + ", " + position_name.get() + ", " + position_description.get();
    }
}

