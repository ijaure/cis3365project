package sample;

import javafx.beans.property.*;

public class Planner {
    IntegerProperty planner_id = new SimpleIntegerProperty();
    StringProperty planner_first_name = new SimpleStringProperty();
    StringProperty planner_last_name = new SimpleStringProperty();
    StringProperty planner_phone = new SimpleStringProperty();
    StringProperty planner_email = new SimpleStringProperty();
    BooleanProperty is_client = new SimpleBooleanProperty();
    IntegerProperty fk_venue_id = new SimpleIntegerProperty();

    public Planner(IntegerProperty planner_id, StringProperty planner_first_name, StringProperty planner_last_name, StringProperty planner_phone, StringProperty planner_email, BooleanProperty is_client, IntegerProperty fk_venue_id) {
        this.planner_id = planner_id;
        this.planner_first_name = planner_first_name;
        this.planner_last_name = planner_last_name;
        this.planner_phone = planner_phone;
        this.planner_email = planner_email;
        this.is_client = is_client;
        this.fk_venue_id = fk_venue_id;
    }

    public Planner() {
    }

    public int getPlanner_id() {
        return planner_id.get();
    }

    public IntegerProperty planner_idProperty() {
        return planner_id;
    }

    public void setPlanner_id(int planner_id) {
        this.planner_id.set(planner_id);
    }

    public String getPlanner_first_name() {
        return planner_first_name.get();
    }

    public StringProperty planner_first_nameProperty() {
        return planner_first_name;
    }

    public void setPlanner_first_name(String planner_first_name) {
        this.planner_first_name.set(planner_first_name);
    }

    public String getPlanner_last_name() {
        return planner_last_name.get();
    }

    public StringProperty planner_last_nameProperty() {
        return planner_last_name;
    }

    public void setPlanner_last_name(String planner_last_name) {
        this.planner_last_name.set(planner_last_name);
    }

    public String getPlanner_phone() {
        return planner_phone.get();
    }

    public StringProperty planner_phoneProperty() {
        return planner_phone;
    }

    public void setPlanner_phone(String planner_phone) {
        this.planner_phone.set(planner_phone);
    }

    public String getPlanner_email() {
        return planner_email.get();
    }

    public StringProperty planner_emailProperty() {
        return planner_email;
    }

    public void setPlanner_email(String planner_email) {
        this.planner_email.set(planner_email);
    }

    public boolean isIs_client() {
        return is_client.get();
    }

    public BooleanProperty is_clientProperty() {
        return is_client;
    }

    public void setIs_client(boolean is_client) {
        this.is_client.set(is_client);
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
}
