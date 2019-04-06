package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product_Type {

    IntegerProperty product_type_id = new SimpleIntegerProperty();
    StringProperty product_type_name = new SimpleStringProperty();

    public Product_Type(IntegerProperty product_type_id, StringProperty product_type_name) {
        this.product_type_id = product_type_id;
        this.product_type_name = product_type_name;
    }

    public Product_Type() {
    }

    public int getProduct_type_id() {
        return product_type_id.get();
    }

    public IntegerProperty product_type_idProperty() {
        return product_type_id;
    }

    public void setProduct_type_id(int product_type_id) {
        this.product_type_id.set(product_type_id);
    }

    public String getProduct_type_name() {
        return product_type_name.get();
    }

    public StringProperty product_type_nameProperty() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name.set(product_type_name);
    }

    @Override
    public String toString(){
        return product_type_id.get() + ", " + product_type_name.get();
    }
}
