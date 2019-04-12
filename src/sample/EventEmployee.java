package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EventEmployee {

    IntegerProperty event_employee_id = new SimpleIntegerProperty();
    IntegerProperty fk_event_id = new SimpleIntegerProperty();
    IntegerProperty fk_employee_id = new SimpleIntegerProperty();

    public EventEmployee(IntegerProperty event_employee_id, IntegerProperty fk_event_id, IntegerProperty fk_employee_id) {
        this.event_employee_id = event_employee_id;
        this.fk_event_id = fk_event_id;
        this.fk_employee_id = fk_employee_id;
    }

    public EventEmployee() {
    }

    public int getEvent_employee_id() {
        return event_employee_id.get();
    }

    public IntegerProperty event_employee_idProperty() {
        return event_employee_id;
    }

    public void setEvent_employee_id(int event_employee_id) {
        this.event_employee_id.set(event_employee_id);
    }

    public int getFk_event_id() {
        return fk_event_id.get();
    }

    public IntegerProperty fk_event_idProperty() {
        return fk_event_id;
    }

    public void setFk_event_id(int fk_event_id) {
        this.fk_event_id.set(fk_event_id);
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
}
