package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product_Status {
    IntegerProperty product_status_id = new SimpleIntegerProperty();
    StringProperty product_status_name = new SimpleStringProperty();

    public Product_Status(IntegerProperty product_status_id, StringProperty product_status_name) {
        this.product_status_id = product_status_id;
        this.product_status_name = product_status_name;
    }

    public Product_Status() {
    }

    public int getProduct_status_id() {
        return product_status_id.get();
    }

    public IntegerProperty product_status_idProperty() {
        return product_status_id;
    }

    public void setProduct_status_id(int product_status_id) {
        this.product_status_id.set(product_status_id);
    }

    public String getProduct_status_name() {
        return product_status_name.get();
    }

    public StringProperty product_status_nameProperty() {
        return product_status_name;
    }

    public void setProduct_status_name(String product_status_name) {
        this.product_status_name.set(product_status_name);
    }

    @Override
    public String toString(){
        return product_status_id.get() + ", " + product_status_name.get();
    }
}
