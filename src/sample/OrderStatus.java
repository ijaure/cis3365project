package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderStatus {
    IntegerProperty order_status_id = new SimpleIntegerProperty();
    StringProperty order_status_name = new SimpleStringProperty();

    public OrderStatus(IntegerProperty order_status_id, StringProperty order_status_name) {
        this.order_status_id = order_status_id;
        this.order_status_name = order_status_name;
    }

    public OrderStatus() {
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

    public String getOrder_status_name() {
        return order_status_name.get();
    }

    public StringProperty order_status_nameProperty() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name.set(order_status_name);
    }
}
