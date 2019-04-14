package sample;

import javafx.beans.property.*;

import java.util.Date;

public class Event {
    IntegerProperty event_id = new SimpleIntegerProperty();
    StringProperty event_name = new SimpleStringProperty();
    StringProperty billing_address = new SimpleStringProperty();
    StringProperty event_contact_first_name = new SimpleStringProperty();
    StringProperty event_contact_last_name = new SimpleStringProperty();
    StringProperty event_phone = new SimpleStringProperty();
    StringProperty event_email = new SimpleStringProperty();
    ObjectProperty<Date> start_date = new SimpleObjectProperty<>(this, "start_date");
    ObjectProperty<Date> proj_end_date = new SimpleObjectProperty<>(this, "proj_end_date");
    ObjectProperty<Date> act_end_date = new SimpleObjectProperty<>(this, "act_end_date");
    IntegerProperty fk_event_status = new SimpleIntegerProperty();
    IntegerProperty fk_event_occurance = new SimpleIntegerProperty();
    IntegerProperty fk_event_note_id = new SimpleIntegerProperty();

    public Event(IntegerProperty event_id, StringProperty event_name, IntegerProperty fk_venue_id, IntegerProperty fk_client_id, StringProperty billing_address, StringProperty event_contact_first_name, StringProperty event_contact_last_name, StringProperty event_phone, StringProperty event_email, ObjectProperty<Date> start_date, ObjectProperty<Date> proj_end_date, ObjectProperty<Date> act_end_date, IntegerProperty fk_event_status, IntegerProperty fk_event_occurance, IntegerProperty fk_event_note_id) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.billing_address = billing_address;
        this.event_contact_first_name = event_contact_first_name;
        this.event_contact_last_name = event_contact_last_name;
        this.event_phone = event_phone;
        this.event_email = event_email;
        this.start_date = start_date;
        this.proj_end_date = proj_end_date;
        this.act_end_date = act_end_date;
        this.fk_event_status = fk_event_status;
        this.fk_event_occurance = fk_event_occurance;
        this.fk_event_note_id = fk_event_note_id;
    }

    public Event() {
    }

    public int getEvent_id() {
        return event_id.get();
    }

    public IntegerProperty event_idProperty() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id.set(event_id);
    }

    public String getEvent_name() {
        return event_name.get();
    }

    public StringProperty event_nameProperty() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name.set(event_name);
    }

    public String getBilling_address() {
        return billing_address.get();
    }

    public StringProperty billing_addressProperty() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address.set(billing_address);
    }

    public String getEvent_contact_first_name() {
        return event_contact_first_name.get();
    }

    public StringProperty event_contact_first_nameProperty() {
        return event_contact_first_name;
    }

    public void setEvent_contact_first_name(String event_contact_first_name) {
        this.event_contact_first_name.set(event_contact_first_name);
    }

    public String getEvent_contact_last_name() {
        return event_contact_last_name.get();
    }

    public StringProperty event_contact_last_nameProperty() {
        return event_contact_last_name;
    }

    public void setEvent_contact_last_name(String event_contact_last_name) {
        this.event_contact_last_name.set(event_contact_last_name);
    }

    public String getEvent_phone() {
        return event_phone.get();
    }

    public StringProperty event_phoneProperty() {
        return event_phone;
    }

    public void setEvent_phone(String event_phone) {
        this.event_phone.set(event_phone);
    }

    public String getEvent_email() {
        return event_email.get();
    }

    public StringProperty event_emailProperty() {
        return event_email;
    }

    public void setEvent_email(String event_email) {
        this.event_email.set(event_email);
    }

    public Date getStart_date() {
        return start_date.get();
    }

    public ObjectProperty<Date> start_dateProperty() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date.set(start_date);
    }

    public Date getProj_end_date() {
        return proj_end_date.get();
    }

    public ObjectProperty<Date> proj_end_dateProperty() {
        return proj_end_date;
    }

    public void setProj_end_date(Date proj_end_date) {
        this.proj_end_date.set(proj_end_date);
    }

    public Date getAct_end_date() {
        return act_end_date.get();
    }

    public ObjectProperty<Date> act_end_dateProperty() {
        return act_end_date;
    }

    public void setAct_end_date(Date act_end_date) {
        this.act_end_date.set(act_end_date);
    }

    public int getFk_event_status() {
        return fk_event_status.get();
    }

    public IntegerProperty fk_event_statusProperty() {
        return fk_event_status;
    }

    public void setFk_event_status(int fk_event_status) {
        this.fk_event_status.set(fk_event_status);
    }

    public int getFk_event_occurance() {
        return fk_event_occurance.get();
    }

    public IntegerProperty fk_event_occuranceProperty() {
        return fk_event_occurance;
    }

    public void setFk_event_occurance(int fk_event_occurance) {
        this.fk_event_occurance.set(fk_event_occurance);
    }

    public int getFk_event_note_id() {
        return fk_event_note_id.get();
    }

    public IntegerProperty fk_event_note_idProperty() {
        return fk_event_note_id;
    }

    public void setFk_event_note_id(int fk_event_note_id) {
        this.fk_event_note_id.set(fk_event_note_id);
    }

    @Override
    public String toString(){
        return event_id.get() + ", " + event_name.get();
    }
}
