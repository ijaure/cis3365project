package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vendor_Status {
    IntegerProperty vendor_status_id = new SimpleIntegerProperty();
    StringProperty vendor_status_name = new SimpleStringProperty();

    public Vendor_Status(IntegerProperty vendor_status_id, StringProperty vendor_status_name) {
        this.vendor_status_id = vendor_status_id;
        this.vendor_status_name = vendor_status_name;
    }

    public Vendor_Status() {
    }

    public int getVendor_status_id() {
        return vendor_status_id.get();
    }

    public IntegerProperty vendor_status_idProperty() {
        return vendor_status_id;
    }

    public void setVendor_status_id(int vendor_status_id) {
        this.vendor_status_id.set(vendor_status_id);
    }

    public String getVendor_status_name() {
        return vendor_status_name.get();
    }

    public StringProperty vendor_status_nameProperty() {
        return vendor_status_name;
    }

    public void setVendor_status_name(String vendor_status_name) {
        this.vendor_status_name.set(vendor_status_name);
    }

    @Override
    public String toString(){
        return vendor_status_id.get() + ", " + vendor_status_name.get();
    }
}
