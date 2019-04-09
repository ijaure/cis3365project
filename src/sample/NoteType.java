package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NoteType {
    IntegerProperty note_type_id = new SimpleIntegerProperty();
    StringProperty note_type_name = new SimpleStringProperty();

    public NoteType(IntegerProperty note_type_id, StringProperty note_type_name) {
        this.note_type_id = note_type_id;
        this.note_type_name = note_type_name;
    }

    public NoteType() {
    }

    public int getNote_type_id() {
        return note_type_id.get();
    }

    public IntegerProperty note_type_idProperty() {
        return note_type_id;
    }

    public void setNote_type_id(int note_type_id) {
        this.note_type_id.set(note_type_id);
    }

    public String getNote_type_name() {
        return note_type_name.get();
    }

    public StringProperty note_type_nameProperty() {
        return note_type_name;
    }

    public void setNote_type_name(String note_type_name) {
        this.note_type_name.set(note_type_name);
    }
}
