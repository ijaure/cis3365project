package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaymentInformation {
    IntegerProperty payment_info_id = new SimpleIntegerProperty();
    IntegerProperty fk_client_id = new SimpleIntegerProperty();
    StringProperty payment_first_name = new SimpleStringProperty();
    StringProperty payment_last_name = new SimpleStringProperty();
    IntegerProperty cc_number = new SimpleIntegerProperty();
    IntegerProperty cvc_number = new SimpleIntegerProperty();
    IntegerProperty exp_month_date = new SimpleIntegerProperty();
    IntegerProperty exp_year_date = new SimpleIntegerProperty();
    IntegerProperty payment_requirement = new SimpleIntegerProperty();

    public PaymentInformation(IntegerProperty payment_info_id, IntegerProperty fk_client_id, StringProperty payment_first_name, StringProperty payment_last_name, IntegerProperty cc_number, IntegerProperty cvc_number, IntegerProperty exp_month_date, IntegerProperty exp_year_date, IntegerProperty payment_requirement) {
        this.payment_info_id = payment_info_id;
        this.fk_client_id = fk_client_id;
        this.payment_first_name = payment_first_name;
        this.payment_last_name = payment_last_name;
        this.cc_number = cc_number;
        this.cvc_number = cvc_number;
        this.exp_month_date = exp_month_date;
        this.exp_year_date = exp_year_date;
        this.payment_requirement = payment_requirement;
    }

    public PaymentInformation() {
    }

    public int getPayment_info_id() {
        return payment_info_id.get();
    }

    public IntegerProperty payment_info_idProperty() {
        return payment_info_id;
    }

    public void setPayment_info_id(int payment_info_id) {
        this.payment_info_id.set(payment_info_id);
    }

    public int getFk_client_id() {
        return fk_client_id.get();
    }

    public IntegerProperty fk_client_idProperty() {
        return fk_client_id;
    }

    public void setFk_client_id(int fk_client_id) {
        this.fk_client_id.set(fk_client_id);
    }

    public String getPayment_first_name() {
        return payment_first_name.get();
    }

    public StringProperty payment_first_nameProperty() {
        return payment_first_name;
    }

    public void setPayment_first_name(String payment_first_name) {
        this.payment_first_name.set(payment_first_name);
    }

    public String getPayment_last_name() {
        return payment_last_name.get();
    }

    public StringProperty payment_last_nameProperty() {
        return payment_last_name;
    }

    public void setPayment_last_name(String payment_last_name) {
        this.payment_last_name.set(payment_last_name);
    }

    public int getCc_number() {
        return cc_number.get();
    }

    public IntegerProperty cc_numberProperty() {
        return cc_number;
    }

    public void setCc_number(int cc_number) {
        this.cc_number.set(cc_number);
    }

    public int getCvc_number() {
        return cvc_number.get();
    }

    public IntegerProperty cvc_numberProperty() {
        return cvc_number;
    }

    public void setCvc_number(int cvc_number) {
        this.cvc_number.set(cvc_number);
    }

    public int getExp_month_date() {
        return exp_month_date.get();
    }

    public IntegerProperty exp_month_dateProperty() {
        return exp_month_date;
    }

    public void setExp_month_date(int exp_month_date) {
        this.exp_month_date.set(exp_month_date);
    }

    public int getExp_year_date() {
        return exp_year_date.get();
    }

    public IntegerProperty exp_year_dateProperty() {
        return exp_year_date;
    }

    public void setExp_year_date(int exp_year_date) {
        this.exp_year_date.set(exp_year_date);
    }

    public int getPayment_requirement() {
        return payment_requirement.get();
    }

    public IntegerProperty payment_requirementProperty() {
        return payment_requirement;
    }

    public void setPayment_requirement(int payment_requirement) {
        this.payment_requirement.set(payment_requirement);
    }
}
