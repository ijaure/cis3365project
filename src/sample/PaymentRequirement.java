package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaymentRequirement {
    IntegerProperty payment_req_id = new SimpleIntegerProperty();
    StringProperty payment_req_name = new SimpleStringProperty();

    public PaymentRequirement(IntegerProperty payment_req_id, StringProperty payment_req_name) {
        this.payment_req_id = payment_req_id;
        this.payment_req_name = payment_req_name;
    }

    public PaymentRequirement() {
    }

    public int getPayment_req_id() {
        return payment_req_id.get();
    }

    public IntegerProperty payment_req_idProperty() {
        return payment_req_id;
    }

    public void setPayment_req_id(int payment_req_id) {
        this.payment_req_id.set(payment_req_id);
    }

    public String getPayment_req_name() {
        return payment_req_name.get();
    }

    public StringProperty payment_req_nameProperty() {
        return payment_req_name;
    }

    public void setPayment_req_name(String payment_req_name) {
        this.payment_req_name.set(payment_req_name);
    }
}
