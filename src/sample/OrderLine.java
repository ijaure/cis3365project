package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class OrderLine {
    IntegerProperty order_line_id = new SimpleIntegerProperty();
    IntegerProperty fk_order_id = new SimpleIntegerProperty();
    IntegerProperty fk_product_id = new SimpleIntegerProperty();
    IntegerProperty fk_order_line_status_id = new SimpleIntegerProperty();
    IntegerProperty quantity = new SimpleIntegerProperty();
    DoubleProperty total = new SimpleDoubleProperty();

    public OrderLine(IntegerProperty order_line_id, IntegerProperty fk_order_id, IntegerProperty fk_product_id, IntegerProperty fk_order_line_status_id, IntegerProperty quantity, DoubleProperty total) {
        this.order_line_id = order_line_id;
        this.fk_order_id = fk_order_id;
        this.fk_product_id = fk_product_id;
        this.fk_order_line_status_id = fk_order_line_status_id;
        this.quantity = quantity;
        this.total = total;
    }

    public OrderLine() {
    }

    public int getOrder_line_id() {
        return order_line_id.get();
    }

    public IntegerProperty order_line_idProperty() {
        return order_line_id;
    }

    public void setOrder_line_id(int order_line_id) {
        this.order_line_id.set(order_line_id);
    }

    public int getFk_order_id() {
        return fk_order_id.get();
    }

    public IntegerProperty fk_order_idProperty() {
        return fk_order_id;
    }

    public void setFk_order_id(int fk_order_id) {
        this.fk_order_id.set(fk_order_id);
    }

    public int getFk_product_id() {
        return fk_product_id.get();
    }

    public IntegerProperty fk_product_idProperty() {
        return fk_product_id;
    }

    public void setFk_product_id(int fk_product_id) {
        this.fk_product_id.set(fk_product_id);
    }

    public int getFk_order_line_status_id() {
        return fk_order_line_status_id.get();
    }

    public IntegerProperty fk_order_line_status_idProperty() {
        return fk_order_line_status_id;
    }

    public void setFk_order_line_status_id(int fk_order_line_status_id) {
        this.fk_order_line_status_id.set(fk_order_line_status_id);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }
}
