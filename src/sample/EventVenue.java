package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EventVenue {
    IntegerProperty event_venue_id = new SimpleIntegerProperty();
    IntegerProperty fk_venue_id = new SimpleIntegerProperty();
    IntegerProperty fk_event_id = new SimpleIntegerProperty();

    public EventVenue(IntegerProperty event_venue_id, IntegerProperty fk_venue_id, IntegerProperty fk_event_id) {
        this.event_venue_id = event_venue_id;
        this.fk_venue_id = fk_venue_id;
        this.fk_event_id = fk_event_id;
    }

    public EventVenue() {
    }

    public int getEvent_venue_id() {
        return event_venue_id.get();
    }

    public IntegerProperty event_venue_idProperty() {
        return event_venue_id;
    }

    public void setEvent_venue_id(int event_venue_id) {
        this.event_venue_id.set(event_venue_id);
    }

    public int getFk_venue_id() {
        return fk_venue_id.get();
    }

    public IntegerProperty fk_venue_idProperty() {
        return fk_venue_id;
    }

    public void setFk_venue_id(int fk_venue_id) {
        this.fk_venue_id.set(fk_venue_id);
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
}
