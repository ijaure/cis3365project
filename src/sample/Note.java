package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    IntegerProperty note_id = new SimpleIntegerProperty();
    StringProperty note_description = new SimpleStringProperty();
    IntegerProperty fk_note_type_id = new SimpleIntegerProperty();

    public Note(IntegerProperty note_id, StringProperty note_description, IntegerProperty fk_note_type_id) {
        this.note_id = note_id;
        this.note_description = note_description;
        this.fk_note_type_id = fk_note_type_id;
    }

    public Note() {
    }

    public int getNote_id() {
        return note_id.get();
    }

    public IntegerProperty note_idProperty() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id.set(note_id);
    }

    public String getNote_description() {
        return note_description.get();
    }

    public StringProperty note_descriptionProperty() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description.set(note_description);
    }

    public int getFk_note_type_id() {
        return fk_note_type_id.get();
    }

    public IntegerProperty fk_note_type_idProperty() {
        return fk_note_type_id;
    }

    public void setFk_note_type_id(int fk_note_type_id) {
        this.fk_note_type_id.set(fk_note_type_id);
    }

    @Override
    public String toString(){
        return note_id.get() + ", " + note_description.get();
    }
}
