package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventStatus {
    IntegerProperty event_status_id = new SimpleIntegerProperty();
    StringProperty event_status_name = new SimpleStringProperty();

    public EventStatus(IntegerProperty event_status_id, StringProperty event_status_name) {
        this.event_status_id = event_status_id;
        this.event_status_name = event_status_name;
    }

    public EventStatus() {
    }

    public int getEvent_status_id() {
        return event_status_id.get();
    }

    public IntegerProperty event_status_idProperty() {
        return event_status_id;
    }

    public void setEvent_status_id(int event_status_id) {
        this.event_status_id.set(event_status_id);
    }

    public String getEvent_status_name() {
        return event_status_name.get();
    }

    public StringProperty event_status_nameProperty() {
        return event_status_name;
    }

    public void setEvent_status_name(String event_status_name) {
        this.event_status_name.set(event_status_name);
    }

    @Override
    public String toString(){
        return event_status_id.get() + ", " + event_status_name.get();
    }
}
