package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderLineStatus {
    IntegerProperty order_line_status_id = new SimpleIntegerProperty();
    StringProperty order_line_status_name = new SimpleStringProperty();

    public OrderLineStatus(IntegerProperty order_line_status_id, StringProperty order_line_status_name) {
        this.order_line_status_id = order_line_status_id;
        this.order_line_status_name = order_line_status_name;
    }

    public OrderLineStatus() {
    }

    public int getOrder_line_status_id() {
        return order_line_status_id.get();
    }

    public IntegerProperty order_line_status_idProperty() {
        return order_line_status_id;
    }

    public void setOrder_line_status_id(int order_line_status_id) {
        this.order_line_status_id.set(order_line_status_id);
    }

    public String getOrder_line_status_name() {
        return order_line_status_name.get();
    }

    public StringProperty order_line_status_nameProperty() {
        return order_line_status_name;
    }

    public void setOrder_line_status_name(String order_line_status_name) {
        this.order_line_status_name.set(order_line_status_name);
    }

    @Override
    public String toString(){
        return order_line_status_id.get() + ", " + order_line_status_name.get();
    }
}
