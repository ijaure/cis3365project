package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VenueStatus {
    IntegerProperty venue_status_id = new SimpleIntegerProperty();
    StringProperty venue_status_name = new SimpleStringProperty();

    public VenueStatus(IntegerProperty venue_status_id, StringProperty venue_status_name) {
        this.venue_status_id = venue_status_id;
        this.venue_status_name = venue_status_name;
    }

    public VenueStatus() {
    }

    public int getVenue_status_id() {
        return venue_status_id.get();
    }

    public IntegerProperty venue_status_idProperty() {
        return venue_status_id;
    }

    public void setVenue_status_id(int venue_status_id) {
        this.venue_status_id.set(venue_status_id);
    }

    public String getVenue_status_name() {
        return venue_status_name.get();
    }

    public StringProperty venue_status_nameProperty() {
        return venue_status_name;
    }

    public void setVenue_status_name(String venue_status_name) {
        this.venue_status_name.set(venue_status_name);
    }
}
