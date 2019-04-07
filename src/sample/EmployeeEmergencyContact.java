package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeEmergencyContact {

    IntegerProperty emergency_contact_id = new SimpleIntegerProperty();
    IntegerProperty fk_employee_id = new SimpleIntegerProperty();
    StringProperty emergency_contact_first_name = new SimpleStringProperty();
    StringProperty emergency_contact_last_name = new SimpleStringProperty();
    StringProperty emergency_contact_phone = new SimpleStringProperty();
    StringProperty emergency_contact_email = new SimpleStringProperty();

    public EmployeeEmergencyContact() {
    }

    public EmployeeEmergencyContact(IntegerProperty emergency_contact_id, IntegerProperty fk_employee_id, StringProperty emergency_contact_first_name, StringProperty emergency_contact_last_name, StringProperty emergency_contact_phone, StringProperty emergency_contact_email) {
        this.emergency_contact_id = emergency_contact_id;
        this.fk_employee_id = fk_employee_id;
        this.emergency_contact_first_name = emergency_contact_first_name;
        this.emergency_contact_last_name = emergency_contact_last_name;
        this.emergency_contact_phone = emergency_contact_phone;
        this.emergency_contact_email = emergency_contact_email;
    }

    public int getEmergency_contact_id() {
        return emergency_contact_id.get();
    }

    public IntegerProperty emergency_contact_idProperty() {
        return emergency_contact_id;
    }

    public void setEmergency_contact_id(int emergency_contact_id) {
        this.emergency_contact_id.set(emergency_contact_id);
    }

    public int getFk_employee_id() {
        return fk_employee_id.get();
    }

    public IntegerProperty fk_employee_idProperty() {
        return fk_employee_id;
    }

    public void setFk_employee_id(int fk_employee_id) {
        this.fk_employee_id.set(fk_employee_id);
    }

    public String getEmergency_contact_first_name() {
        return emergency_contact_first_name.get();
    }

    public StringProperty emergency_contact_first_nameProperty() {
        return emergency_contact_first_name;
    }

    public void setEmergency_contact_first_name(String emergency_contact_first_name) {
        this.emergency_contact_first_name.set(emergency_contact_first_name);
    }

    public String getEmergency_contact_last_name() {
        return emergency_contact_last_name.get();
    }

    public StringProperty emergency_contact_last_nameProperty() {
        return emergency_contact_last_name;
    }

    public void setEmergency_contact_last_name(String emergency_contact_last_name) {
        this.emergency_contact_last_name.set(emergency_contact_last_name);
    }

    public String getEmergency_contact_phone() {
        return emergency_contact_phone.get();
    }

    public StringProperty emergency_contact_phoneProperty() {
        return emergency_contact_phone;
    }

    public void setEmergency_contact_phone(String emergency_contact_phone) {
        this.emergency_contact_phone.set(emergency_contact_phone);
    }

    public String getEmergency_contact_email() {
        return emergency_contact_email.get();
    }

    public StringProperty emergency_contact_emailProperty() {
        return emergency_contact_email;
    }

    public void setEmergency_contact_email(String emergency_contact_email) {
        this.emergency_contact_email.set(emergency_contact_email);
    }
}
