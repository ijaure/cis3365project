package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ProductVendor {
    IntegerProperty product_vendor_id = new SimpleIntegerProperty();
    IntegerProperty fk_product_id = new SimpleIntegerProperty();
    IntegerProperty fk_vendor_id = new SimpleIntegerProperty();

    public ProductVendor(IntegerProperty product_vendor_id, IntegerProperty fk_product_id, IntegerProperty fk_vendor_id) {
        this.product_vendor_id = product_vendor_id;
        this.fk_product_id = fk_product_id;
        this.fk_vendor_id = fk_vendor_id;
    }

    public ProductVendor() {
    }

    public int getProduct_vendor_id() {
        return product_vendor_id.get();
    }

    public IntegerProperty product_vendor_idProperty() {
        return product_vendor_id;
    }

    public void setProduct_vendor_id(int product_vendor_id) {
        this.product_vendor_id.set(product_vendor_id);
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

    public int getFk_vendor_id() {
        return fk_vendor_id.get();
    }

    public IntegerProperty fk_vendor_idProperty() {
        return fk_vendor_id;
    }

    public void setFk_vendor_id(int fk_vendor_id) {
        this.fk_vendor_id.set(fk_vendor_id);
    }
}
