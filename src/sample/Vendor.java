package sample;

import javafx.beans.property.*;

import java.util.Date;

public class Vendor {

    IntegerProperty vendor_id = new SimpleIntegerProperty();
    StringProperty vendor_name = new SimpleStringProperty();
    IntegerProperty vendor_acc_num = new SimpleIntegerProperty();
    ObjectProperty<Date> vendor_join_date = new SimpleObjectProperty<>(this, "vendor_join_date");
    IntegerProperty vendor_status_id = new SimpleIntegerProperty();
    StringProperty vendor_contact_first_name = new SimpleStringProperty();
    StringProperty vendor_contact_last_name = new SimpleStringProperty();
    StringProperty vendor_company_phone = new SimpleStringProperty();
    StringProperty vendor_mobile_phone = new SimpleStringProperty();
    StringProperty vendor_email = new SimpleStringProperty();
    StringProperty vendor_address = new SimpleStringProperty();
    IntegerProperty vendor_region_id = new SimpleIntegerProperty();
    StringProperty payment_terms = new SimpleStringProperty();
    DoubleProperty vendor_credit_limit = new SimpleDoubleProperty();

    public Vendor(IntegerProperty vendor_id, StringProperty vendor_name, IntegerProperty vendor_acc_num, ObjectProperty<Date> vendor_join_date, IntegerProperty vendor_status_id, StringProperty vendor_contact_first_name, StringProperty vendor_contact_last_name, StringProperty vendor_company_phone, StringProperty vendor_mobile_phone, StringProperty vendor_email, StringProperty vendor_address, IntegerProperty vendor_region_id, StringProperty payment_terms, DoubleProperty vendor_credit_limit) {
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.vendor_acc_num = vendor_acc_num;
        this.vendor_join_date = vendor_join_date;
        this.vendor_status_id = vendor_status_id;
        this.vendor_contact_first_name = vendor_contact_first_name;
        this.vendor_contact_last_name = vendor_contact_last_name;
        this.vendor_company_phone = vendor_company_phone;
        this.vendor_mobile_phone = vendor_mobile_phone;
        this.vendor_email = vendor_email;
        this.vendor_address = vendor_address;
        this.vendor_region_id = vendor_region_id;
        this.payment_terms = payment_terms;
        this.vendor_credit_limit = vendor_credit_limit;
    }

    public Vendor() {
    }

    public int getVendor_id() {
        return vendor_id.get();
    }

    public IntegerProperty vendor_idProperty() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id.set(vendor_id);
    }

    public String getVendor_name() {
        return vendor_name.get();
    }

    public StringProperty vendor_nameProperty() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name.set(vendor_name);
    }

    public int getVendor_acc_num() {
        return vendor_acc_num.get();
    }

    public IntegerProperty vendor_acc_numProperty() {
        return vendor_acc_num;
    }

    public void setVendor_acc_num(int vendor_acc_num) {
        this.vendor_acc_num.set(vendor_acc_num);
    }

    public Date getVendor_join_date() {
        return vendor_join_date.get();
    }

    public ObjectProperty<Date> vendor_join_dateProperty() {
        return vendor_join_date;
    }

    public void setVendor_join_date(Date vendor_join_date) {
        this.vendor_join_date.set(vendor_join_date);
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

    public String getVendor_contact_first_name() {
        return vendor_contact_first_name.get();
    }

    public StringProperty vendor_contact_first_nameProperty() {
        return vendor_contact_first_name;
    }

    public void setVendor_contact_first_name(String vendor_contact_first_name) {
        this.vendor_contact_first_name.set(vendor_contact_first_name);
    }

    public String getVendor_contact_last_name() {
        return vendor_contact_last_name.get();
    }

    public StringProperty vendor_contact_last_nameProperty() {
        return vendor_contact_last_name;
    }

    public void setVendor_contact_last_name(String vendor_contact_last_name) {
        this.vendor_contact_last_name.set(vendor_contact_last_name);
    }

    public String getVendor_company_phone() {
        return vendor_company_phone.get();
    }

    public StringProperty vendor_company_phoneProperty() {
        return vendor_company_phone;
    }

    public void setVendor_company_phone(String vendor_company_phone) {
        this.vendor_company_phone.set(vendor_company_phone);
    }

    public String getVendor_mobile_phone() {
        return vendor_mobile_phone.get();
    }

    public StringProperty vendor_mobile_phoneProperty() {
        return vendor_mobile_phone;
    }

    public void setVendor_mobile_phone(String vendor_mobile_phone) {
        this.vendor_mobile_phone.set(vendor_mobile_phone);
    }

    public String getVendor_email() {
        return vendor_email.get();
    }

    public StringProperty vendor_emailProperty() {
        return vendor_email;
    }

    public void setVendor_email(String vendor_email) {
        this.vendor_email.set(vendor_email);
    }

    public String getVendor_address() {
        return vendor_address.get();
    }

    public StringProperty vendor_addressProperty() {
        return vendor_address;
    }

    public void setVendor_address(String vendor_address) {
        this.vendor_address.set(vendor_address);
    }

    public int getVendor_region_id() {
        return vendor_region_id.get();
    }

    public IntegerProperty vendor_region_idProperty() {
        return vendor_region_id;
    }

    public void setVendor_region_id(int vendor_region_id) {
        this.vendor_region_id.set(vendor_region_id);
    }

    public String getPayment_terms() {
        return payment_terms.get();
    }

    public StringProperty payment_termsProperty() {
        return payment_terms;
    }

    public void setPayment_terms(String payment_terms) {
        this.payment_terms.set(payment_terms);
    }

    public double getVendor_credit_limit() {
        return vendor_credit_limit.get();
    }

    public DoubleProperty vendor_credit_limitProperty() {
        return vendor_credit_limit;
    }

    public void setVendor_credit_limit(double vendor_credit_limit) {
        this.vendor_credit_limit.set(vendor_credit_limit);
    }

    @Override
    public String toString(){
        return vendor_id.get() + ", " + vendor_name.get();
    }
}
