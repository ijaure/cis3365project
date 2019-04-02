package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    IntegerProperty client_id = new SimpleIntegerProperty();
    StringProperty client_fname = new SimpleStringProperty();
    StringProperty client_lname = new SimpleStringProperty();
    StringProperty client_main_phone = new SimpleStringProperty();
    StringProperty client_secondary_phone = new SimpleStringProperty();
    StringProperty client_email = new SimpleStringProperty();
    StringProperty client_cc_email = new SimpleStringProperty();
    StringProperty billing_street = new SimpleStringProperty();
    StringProperty billing_city = new SimpleStringProperty();
    IntegerProperty fk_region_id = new SimpleIntegerProperty();
    IntegerProperty client_zipcode = new SimpleIntegerProperty();
    StringProperty client_company_name = new SimpleStringProperty();
    StringProperty client_website = new SimpleStringProperty();
    IntegerProperty fk_client_status_id = new SimpleIntegerProperty();

    public Client(IntegerProperty client_id, StringProperty client_fname, StringProperty client_lname, StringProperty client_main_phone, StringProperty client_secondary_phone, StringProperty client_email, StringProperty client_cc_email, StringProperty billing_street, StringProperty billing_city, IntegerProperty fk_region_id, IntegerProperty client_zipcode, StringProperty client_company_name, StringProperty client_website, IntegerProperty fk_client_status_id) {
        this.client_id = client_id;
        this.client_fname = client_fname;
        this.client_lname = client_lname;
        this.client_main_phone = client_main_phone;
        this.client_secondary_phone = client_secondary_phone;
        this.client_email = client_email;
        this.client_cc_email = client_cc_email;
        this.billing_street = billing_street;
        this.billing_city = billing_city;
        this.fk_region_id = fk_region_id;
        this.client_zipcode = client_zipcode;
        this.client_company_name = client_company_name;
        this.client_website = client_website;
        this.fk_client_status_id = fk_client_status_id;
    }

    public Client() {
    }

    public int getClient_id() {
        return client_id.get();
    }

    public IntegerProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public String getClient_fname() {
        return client_fname.get();
    }

    public StringProperty client_fnameProperty() {
        return client_fname;
    }

    public void setClient_fname(String client_fname) {
        this.client_fname.set(client_fname);
    }

    public String getClient_lname() {
        return client_lname.get();
    }

    public StringProperty client_lnameProperty() {
        return client_lname;
    }

    public void setClient_lname(String client_lname) {
        this.client_lname.set(client_lname);
    }

    public String getClient_main_phone() {
        return client_main_phone.get();
    }

    public StringProperty client_main_phoneProperty() {
        return client_main_phone;
    }

    public void setClient_main_phone(String client_main_phone) {
        this.client_main_phone.set(client_main_phone);
    }

    public String getClient_secondary_phone() {
        return client_secondary_phone.get();
    }

    public StringProperty client_secondary_phoneProperty() {
        return client_secondary_phone;
    }

    public void setClient_secondary_phone(String client_secondary_phone) {
        this.client_secondary_phone.set(client_secondary_phone);
    }

    public String getClient_email() {
        return client_email.get();
    }

    public StringProperty client_emailProperty() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email.set(client_email);
    }

    public String getClient_cc_email() {
        return client_cc_email.get();
    }

    public StringProperty client_cc_emailProperty() {
        return client_cc_email;
    }

    public void setClient_cc_email(String client_cc_email) {
        this.client_cc_email.set(client_cc_email);
    }

    public String getBilling_street() {
        return billing_street.get();
    }

    public StringProperty billing_streetProperty() {
        return billing_street;
    }

    public void setBilling_street(String billing_street) {
        this.billing_street.set(billing_street);
    }

    public String getBilling_city() {
        return billing_city.get();
    }

    public StringProperty billing_cityProperty() {
        return billing_city;
    }

    public void setBilling_city(String billing_city) {
        this.billing_city.set(billing_city);
    }

    public int getFk_region_id() {
        return fk_region_id.get();
    }

    public IntegerProperty fk_region_idProperty() {
        return fk_region_id;
    }

    public void setFk_region_id(int fk_region_id) {
        this.fk_region_id.set(fk_region_id);
    }

    public int getClient_zipcode() {
        return client_zipcode.get();
    }

    public IntegerProperty client_zipcodeProperty() {
        return client_zipcode;
    }

    public void setClient_zipcode(int client_zipcode) {
        this.client_zipcode.set(client_zipcode);
    }

    public String getClient_company_name() {
        return client_company_name.get();
    }

    public StringProperty client_company_nameProperty() {
        return client_company_name;
    }

    public void setClient_company_name(String client_company_name) {
        this.client_company_name.set(client_company_name);
    }

    public String getClient_website() {
        return client_website.get();
    }

    public StringProperty client_websiteProperty() {
        return client_website;
    }

    public void setClient_website(String client_website) {
        this.client_website.set(client_website);
    }

    public int getFk_client_status_id() {
        return fk_client_status_id.get();
    }

    public IntegerProperty fk_client_status_idProperty() {
        return fk_client_status_id;
    }

    public void setFk_client_status_id(int fk_client_status_id) {
        this.fk_client_status_id.set(fk_client_status_id);
    }
}
