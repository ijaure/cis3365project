package sample;

import javafx.beans.property.*;

import java.util.Date;

public class Product {
    IntegerProperty product_id = new SimpleIntegerProperty();
    IntegerProperty fk_product_type_id = new SimpleIntegerProperty();
    IntegerProperty fk_product_status_id = new SimpleIntegerProperty();
    IntegerProperty fk_vendor_id = new SimpleIntegerProperty();
    StringProperty product_name = new SimpleStringProperty();
    StringProperty product_size = new SimpleStringProperty();
    DoubleProperty product_price = new SimpleDoubleProperty();
    ObjectProperty<Date> product_price_date = new SimpleObjectProperty<>(this, "product_price_date");

    public Product(IntegerProperty product_id, IntegerProperty fk_product_type_id, IntegerProperty fk_product_status_id, IntegerProperty fk_vendor_id, StringProperty product_name, StringProperty product_size, DoubleProperty product_price, ObjectProperty<Date> product_price_date) {
        this.product_id = product_id;
        this.fk_product_type_id = fk_product_type_id;
        this.fk_product_status_id = fk_product_status_id;
        this.fk_vendor_id = fk_vendor_id;
        this.product_name = product_name;
        this.product_size = product_size;
        this.product_price = product_price;
        this.product_price_date = product_price_date;
    }

    public Product() {
    }

    public int getProduct_id() {
        return product_id.get();
    }

    public IntegerProperty product_idProperty() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id.set(product_id);
    }

    public int getFk_product_type_id() {
        return fk_product_type_id.get();
    }

    public IntegerProperty fk_product_type_idProperty() {
        return fk_product_type_id;
    }

    public void setFk_product_type_id(int fk_product_type_id) {
        this.fk_product_type_id.set(fk_product_type_id);
    }

    public int getFk_product_status_id() {
        return fk_product_status_id.get();
    }

    public IntegerProperty fk_product_status_idProperty() {
        return fk_product_status_id;
    }

    public void setFk_product_status_id(int fk_product_status_id) {
        this.fk_product_status_id.set(fk_product_status_id);
    }

    public int getFk_vendor_id() {
        return fk_vendor_id.get();
    }

    public IntegerProperty fk_vendor_idProperty() {
        return fk_vendor_id;
    }

    public void setFk_vendor_id(int fk_vendor_id) {
        this.fk_vendor_id.set(fk_vendor_id);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public StringProperty product_nameProperty() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public String getProduct_size() {
        return product_size.get();
    }

    public StringProperty product_sizeProperty() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size.set(product_size);
    }

    public double getProduct_price() {
        return product_price.get();
    }

    public DoubleProperty product_priceProperty() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price.set(product_price);
    }

    public Date getProduct_price_date() {
        return product_price_date.get();
    }

    public ObjectProperty<Date> product_price_dateProperty() {
        return product_price_date;
    }

    public void setProduct_price_date(Date product_price_date) {
        this.product_price_date.set(product_price_date);
    }

    @Override
    public String toString(){
        return product_id.get() + ", " + product_name.get();
    }
}


