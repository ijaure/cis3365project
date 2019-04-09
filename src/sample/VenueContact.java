package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VenueContact {
    IntegerProperty contact_person_id = new SimpleIntegerProperty();
    IntegerProperty fk_venue_id = new SimpleIntegerProperty();
    StringProperty contact_person_first_name = new SimpleStringProperty();
    StringProperty contact_person_last_name = new SimpleStringProperty();
    StringProperty contact_person_phone = new SimpleStringProperty();
    StringProperty contact_person_email = new SimpleStringProperty();

    public VenueContact(IntegerProperty contact_person_id, IntegerProperty fk_venue_id, StringProperty contact_person_first_name, StringProperty contact_person_last_name, StringProperty contact_person_phone, StringProperty contact_person_email) {
        this.contact_person_id = contact_person_id;
        this.fk_venue_id = fk_venue_id;
        this.contact_person_first_name = contact_person_first_name;
        this.contact_person_last_name = contact_person_last_name;
        this.contact_person_phone = contact_person_phone;
        this.contact_person_email = contact_person_email;
    }

    public VenueContact() {
    }

    public int getContact_person_id() {
        return contact_person_id.get();
    }

    public IntegerProperty contact_person_idProperty() {
        return contact_person_id;
    }

    public void setContact_person_id(int contact_person_id) {
        this.contact_person_id.set(contact_person_id);
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

    public String getContact_person_first_name() {
        return contact_person_first_name.get();
    }

    public StringProperty contact_person_first_nameProperty() {
        return contact_person_first_name;
    }

    public void setContact_person_first_name(String contact_person_first_name) {
        this.contact_person_first_name.set(contact_person_first_name);
    }

    public String getContact_person_last_name() {
        return contact_person_last_name.get();
    }

    public StringProperty contact_person_last_nameProperty() {
        return contact_person_last_name;
    }

    public void setContact_person_last_name(String contact_person_last_name) {
        this.contact_person_last_name.set(contact_person_last_name);
    }

    public String getContact_person_phone() {
        return contact_person_phone.get();
    }

    public StringProperty contact_person_phoneProperty() {
        return contact_person_phone;
    }

    public void setContact_person_phone(String contact_person_phone) {
        this.contact_person_phone.set(contact_person_phone);
    }

    public String getContact_person_email() {
        return contact_person_email.get();
    }

    public StringProperty contact_person_emailProperty() {
        return contact_person_email;
    }

    public void setContact_person_email(String contact_person_email) {
        this.contact_person_email.set(contact_person_email);
    }
}
