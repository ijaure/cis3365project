package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventOccurance {
    IntegerProperty event_occurance_id = new SimpleIntegerProperty();
    StringProperty event_occurance_name = new SimpleStringProperty();

    public EventOccurance(IntegerProperty event_occurance_id, StringProperty event_occurance_name) {
        this.event_occurance_id = event_occurance_id;
        this.event_occurance_name = event_occurance_name;
    }

    public EventOccurance() {
    }

    public int getEvent_occurance_id() {
        return event_occurance_id.get();
    }

    public IntegerProperty event_occurance_idProperty() {
        return event_occurance_id;
    }

    public void setEvent_occurance_id(int event_occurance_id) {
        this.event_occurance_id.set(event_occurance_id);
    }

    public String getEvent_occurance_name() {
        return event_occurance_name.get();
    }

    public StringProperty event_occurance_nameProperty() {
        return event_occurance_name;
    }

    public void setEvent_occurance_name(String event_occurance_name) {
        this.event_occurance_name.set(event_occurance_name);
    }

    @Override
    public String toString(){
        return event_occurance_id.get() + ", " + event_occurance_name.get();
    }
}
