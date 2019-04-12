package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ClientEvent {
    IntegerProperty client_event_id = new SimpleIntegerProperty();
    IntegerProperty fk_client_id = new SimpleIntegerProperty();
    IntegerProperty fk_event_id = new SimpleIntegerProperty();

    public ClientEvent(IntegerProperty client_event_id, IntegerProperty fk_client_id, IntegerProperty fk_event_id) {
        this.client_event_id = client_event_id;
        this.fk_client_id = fk_client_id;
        this.fk_event_id = fk_event_id;
    }

    public ClientEvent() {
    }

    public int getClient_event_id() {
        return client_event_id.get();
    }

    public IntegerProperty client_event_idProperty() {
        return client_event_id;
    }

    public void setClient_event_id(int client_event_id) {
        this.client_event_id.set(client_event_id);
    }

    public int getFk_client_id() {
        return fk_client_id.get();
    }

    public IntegerProperty fk_client_idProperty() {
        return fk_client_id;
    }

    public void setFk_client_id(int fk_client_id) {
        this.fk_client_id.set(fk_client_id);
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
