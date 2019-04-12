package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EventNote {
    IntegerProperty event_note_id = new SimpleIntegerProperty();
    IntegerProperty fk_event_id = new SimpleIntegerProperty();
    IntegerProperty fk_note_id = new SimpleIntegerProperty();

    public EventNote(IntegerProperty event_note_id, IntegerProperty fk_event_id, IntegerProperty fk_note_id) {
        this.event_note_id = event_note_id;
        this.fk_event_id = fk_event_id;
        this.fk_note_id = fk_note_id;
    }

    public EventNote() {
    }

    public int getEvent_note_id() {
        return event_note_id.get();
    }

    public IntegerProperty event_note_idProperty() {
        return event_note_id;
    }

    public void setEvent_note_id(int event_note_id) {
        this.event_note_id.set(event_note_id);
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

    public int getFk_note_id() {
        return fk_note_id.get();
    }

    public IntegerProperty fk_note_idProperty() {
        return fk_note_id;
    }

    public void setFk_note_id(int fk_note_id) {
        this.fk_note_id.set(fk_note_id);
    }
}
