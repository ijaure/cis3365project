package sample;

import javafx.beans.property.*;

import java.util.Date;

public class Order {


    IntegerProperty order_id = new SimpleIntegerProperty();
    IntegerProperty client_id = new SimpleIntegerProperty();
    IntegerProperty order_status_id = new SimpleIntegerProperty();
    ObjectProperty<Date> order_date = new SimpleObjectProperty<>(this, "order_join_date");
    ObjectProperty<Date> order_complete_date = new SimpleObjectProperty<>(this, "order_complete_date");
    StringProperty order_notes = new SimpleStringProperty();
    ObjectProperty<Date> order_delivery_date = new SimpleObjectProperty<>(this, "order_complete_date");
    StringProperty order_delivery_time = new SimpleStringProperty();


    public Order(IntegerProperty order_id, IntegerProperty client_id, IntegerProperty order_status_id, ObjectProperty<Date> order_date, ObjectProperty<Date> order_complete_date, StringProperty order_notes, ObjectProperty<Date> order_delivery_date, StringProperty order_delivery_time) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.order_status_id = order_status_id;
        this.order_date = order_date;
        this.order_complete_date = order_complete_date;
        this.order_notes = order_notes;
        this.order_delivery_date = order_delivery_date;
        this.order_delivery_time = order_delivery_time;
    }

    public Order() {
    }


    public int getOrder_id() {
        return order_id.get();
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id.set(order_id);
    }

    public int getClient_id() {
        return client_id.get();
    }

    public IntegerProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public int getOrder_status_id() {
        return order_status_id.get();
    }

    public IntegerProperty order_status_idProperty() {
        return order_status_id;
    }

    public void setOrder_status_id(int order_status_id) {
        this.order_status_id.set(order_status_id);
    }

    public Date getOrder_date() {
        return order_date.get();
    }

    public ObjectProperty<Date> order_dateProperty() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date.set(order_date);
    }

    public Date getOrder_complete_date() {
        return order_complete_date.get();
    }

    public ObjectProperty<Date> order_complete_dateProperty() {
        return order_complete_date;
    }

    public void setOrder_complete_date(Date order_complete_date) {
        this.order_complete_date.set(order_complete_date);
    }

    public String getOrder_notes() {
        return order_notes.get();
    }

    public StringProperty order_notesProperty() {
        return order_notes;
    }

    public void setOrder_notes(String order_notes) {
        this.order_notes.set(order_notes);
    }

    public Date getOrder_delivery_date() {
        return order_delivery_date.get();
    }

    public ObjectProperty<Date> order_delivery_dateProperty() {
        return order_delivery_date;
    }

    public void setOrder_delivery_date(Date order_delivery_date) {
        this.order_delivery_date.set(order_delivery_date);
    }

    public String getOrder_delivery_time() {
        return order_delivery_time.get();
    }

    public StringProperty order_delivery_timeProperty() {
        return order_delivery_time;
    }

    public void setOrder_delivery_time(String order_delivery_time) {
        this.order_delivery_time.set(order_delivery_time);
    }







}
