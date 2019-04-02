package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Region {
    IntegerProperty region_id = new SimpleIntegerProperty();
    StringProperty region_name = new SimpleStringProperty();

    public Region(IntegerProperty region_id, StringProperty region_name) {
        this.region_id = region_id;
        this.region_name = region_name;
    }

    public Region() {
    }

    public int getRegion_id() {
        return region_id.get();
    }

    public IntegerProperty region_idProperty() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id.set(region_id);
    }

    public String getRegion_name() {
        return region_name.get();
    }

    public StringProperty region_nameProperty() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name.set(region_name);
    }

    @Override
    public String toString(){
        return region_id.get() + ", " + region_name.get();
    }
}
