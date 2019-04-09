package sample;

import javafx.beans.property.*;

public class Venue {
    IntegerProperty venue_id = new SimpleIntegerProperty();
    StringProperty venue_name = new SimpleStringProperty();
    IntegerProperty fk_venue_status_id = new SimpleIntegerProperty();
    StringProperty venue_address = new SimpleStringProperty();
    StringProperty venue_phone_number = new SimpleStringProperty();
    StringProperty venue_email_address = new SimpleStringProperty();
    StringProperty venue_work_hours = new SimpleStringProperty();
    StringProperty venue_delivery_hours = new SimpleStringProperty();
    BooleanProperty contract_expiration = new SimpleBooleanProperty();
    IntegerProperty comission_percentage = new SimpleIntegerProperty();

    public Venue(IntegerProperty venue_id, StringProperty venue_name, IntegerProperty fk_venue_status_id, StringProperty venue_address, StringProperty venue_phone_number, StringProperty venue_email_address, StringProperty venue_work_hours, StringProperty venue_delivery_hours, BooleanProperty contract_expiration, IntegerProperty comission_percentage) {
        this.venue_id = venue_id;
        this.venue_name = venue_name;
        this.fk_venue_status_id = fk_venue_status_id;
        this.venue_address = venue_address;
        this.venue_phone_number = venue_phone_number;
        this.venue_email_address = venue_email_address;
        this.venue_work_hours = venue_work_hours;
        this.venue_delivery_hours = venue_delivery_hours;
        this.contract_expiration = contract_expiration;
        this.comission_percentage = comission_percentage;
    }

    public Venue() {
    }

    public int getVenue_id() {
        return venue_id.get();
    }

    public IntegerProperty venue_idProperty() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id.set(venue_id);
    }

    public String getVenue_name() {
        return venue_name.get();
    }

    public StringProperty venue_nameProperty() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name.set(venue_name);
    }

    public int getFk_venue_status_id() {
        return fk_venue_status_id.get();
    }

    public IntegerProperty fk_venue_status_idProperty() {
        return fk_venue_status_id;
    }

    public void setFk_venue_status_id(int fk_venue_status_id) {
        this.fk_venue_status_id.set(fk_venue_status_id);
    }

    public String getVenue_address() {
        return venue_address.get();
    }

    public StringProperty venue_addressProperty() {
        return venue_address;
    }

    public void setVenue_address(String venue_address) {
        this.venue_address.set(venue_address);
    }

    public String getVenue_phone_number() {
        return venue_phone_number.get();
    }

    public StringProperty venue_phone_numberProperty() {
        return venue_phone_number;
    }

    public void setVenue_phone_number(String venue_phone_number) {
        this.venue_phone_number.set(venue_phone_number);
    }

    public String getVenue_email_address() {
        return venue_email_address.get();
    }

    public StringProperty venue_email_addressProperty() {
        return venue_email_address;
    }

    public void setVenue_email_address(String venue_email_address) {
        this.venue_email_address.set(venue_email_address);
    }

    public String getVenue_work_hours() {
        return venue_work_hours.get();
    }

    public StringProperty venue_work_hoursProperty() {
        return venue_work_hours;
    }

    public void setVenue_work_hours(String venue_work_hours) {
        this.venue_work_hours.set(venue_work_hours);
    }

    public String getVenue_delivery_hours() {
        return venue_delivery_hours.get();
    }

    public StringProperty venue_delivery_hoursProperty() {
        return venue_delivery_hours;
    }

    public void setVenue_delivery_hours(String venue_delivery_hours) {
        this.venue_delivery_hours.set(venue_delivery_hours);
    }

    public boolean isContract_expiration() {
        return contract_expiration.get();
    }

    public BooleanProperty contract_expirationProperty() {
        return contract_expiration;
    }

    public void setContract_expiration(boolean contract_expiration) {
        this.contract_expiration.set(contract_expiration);
    }

    public int getComission_percentage() {
        return comission_percentage.get();
    }

    public IntegerProperty comission_percentageProperty() {
        return comission_percentage;
    }

    public void setComission_percentage(int comission_percentage) {
        this.comission_percentage.set(comission_percentage);
    }
}
