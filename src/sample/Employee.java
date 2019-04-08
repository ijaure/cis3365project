package sample;

import javafx.beans.property.*;

import java.util.Date;

public class Employee {
    IntegerProperty employee_id = new SimpleIntegerProperty();
    StringProperty employee_first_name = new SimpleStringProperty();
    StringProperty employee_last_name = new SimpleStringProperty();
    StringProperty employee_address = new SimpleStringProperty();
    StringProperty employee_phone_number = new SimpleStringProperty();
    StringProperty employee_email = new SimpleStringProperty();
    ObjectProperty<Date> date_of_birth = new SimpleObjectProperty<>(this, "date_of_birth");
    StringProperty gender = new SimpleStringProperty();
    IntegerProperty fk_employee_type_id = new SimpleIntegerProperty();
    IntegerProperty fk_employee_status_id = new SimpleIntegerProperty();
    StringProperty social_security = new SimpleStringProperty();
    ObjectProperty<Date> start_date = new SimpleObjectProperty<>(this, "start_date");
    DoubleProperty starting_salary = new SimpleDoubleProperty();
    DoubleProperty current_salary = new SimpleDoubleProperty();
    BooleanProperty tabc_certified = new SimpleBooleanProperty();
    ObjectProperty<Date> tabc_cert_expiration = new SimpleObjectProperty<>(this, "tabc_cert_expiration");
    StringProperty citizenship = new SimpleStringProperty();
    BooleanProperty marital_status = new SimpleBooleanProperty();
    BooleanProperty job_application_on_file = new SimpleBooleanProperty();
    IntegerProperty fk_employee_tax_id = new SimpleIntegerProperty();

    public Employee() {
    }

    public Employee(IntegerProperty employee_id, StringProperty employee_first_name, StringProperty employee_last_name, StringProperty employee_address, StringProperty employee_phone_number, StringProperty employee_email, ObjectProperty<Date> date_of_birth, StringProperty gender, IntegerProperty fk_employee_type_id, IntegerProperty fk_employee_status_id, StringProperty social_security, ObjectProperty<Date> start_date, DoubleProperty starting_salary, DoubleProperty current_salary, BooleanProperty tabc_certified, ObjectProperty<Date> tabc_cert_expiration, StringProperty citizenship, BooleanProperty marital_status, BooleanProperty job_application_on_file, IntegerProperty fk_employee_tax_id) {
        this.employee_id = employee_id;
        this.employee_first_name = employee_first_name;
        this.employee_last_name = employee_last_name;
        this.employee_address = employee_address;
        this.employee_phone_number = employee_phone_number;
        this.employee_email = employee_email;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.fk_employee_type_id = fk_employee_type_id;
        this.fk_employee_status_id = fk_employee_status_id;
        this.social_security = social_security;
        this.start_date = start_date;
        this.starting_salary = starting_salary;
        this.current_salary = current_salary;
        this.tabc_certified = tabc_certified;
        this.tabc_cert_expiration = tabc_cert_expiration;
        this.citizenship = citizenship;
        this.marital_status = marital_status;
        this.job_application_on_file = job_application_on_file;
        this.fk_employee_tax_id = fk_employee_tax_id;
    }

    public int getEmployee_id() {
        return employee_id.get();
    }

    public IntegerProperty employee_idProperty() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id.set(employee_id);
    }

    public String getEmployee_first_name() {
        return employee_first_name.get();
    }

    public StringProperty employee_first_nameProperty() {
        return employee_first_name;
    }

    public void setEmployee_first_name(String employee_first_name) {
        this.employee_first_name.set(employee_first_name);
    }

    public String getEmployee_last_name() {
        return employee_last_name.get();
    }

    public StringProperty employee_last_nameProperty() {
        return employee_last_name;
    }

    public void setEmployee_last_name(String employee_last_name) {
        this.employee_last_name.set(employee_last_name);
    }

    public String getEmployee_address() {
        return employee_address.get();
    }

    public StringProperty employee_addressProperty() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address.set(employee_address);
    }

    public String getEmployee_phone_number() {
        return employee_phone_number.get();
    }

    public StringProperty employee_phone_numberProperty() {
        return employee_phone_number;
    }

    public void setEmployee_phone_number(String employee_phone_number) {
        this.employee_phone_number.set(employee_phone_number);
    }

    public String getEmployee_email() {
        return employee_email.get();
    }

    public StringProperty employee_emailProperty() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email.set(employee_email);
    }

    public Date getDate_of_birth() {
        return date_of_birth.get();
    }

    public ObjectProperty<Date> date_of_birthProperty() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth.set(date_of_birth);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public int getFk_employee_type_id() {
        return fk_employee_type_id.get();
    }

    public IntegerProperty fk_employee_type_idProperty() {
        return fk_employee_type_id;
    }

    public void setFk_employee_type_id(int fk_employee_type_id) {
        this.fk_employee_type_id.set(fk_employee_type_id);
    }

    public int getFk_employee_status_id() {
        return fk_employee_status_id.get();
    }

    public IntegerProperty fk_employee_status_idProperty() {
        return fk_employee_status_id;
    }

    public void setFk_employee_status_id(int fk_employee_status_id) {
        this.fk_employee_status_id.set(fk_employee_status_id);
    }

    public String getSocial_security() {
        return social_security.get();
    }

    public StringProperty social_securityProperty() {
        return social_security;
    }

    public void setSocial_security(String social_security) {
        this.social_security.set(social_security);
    }

    public Date getStart_date() {
        return start_date.get();
    }

    public ObjectProperty<Date> start_dateProperty() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date.set(start_date);
    }

    public double getStarting_salary() {
        return starting_salary.get();
    }

    public DoubleProperty starting_salaryProperty() {
        return starting_salary;
    }

    public void setStarting_salary(double starting_salary) {
        this.starting_salary.set(starting_salary);
    }

    public double getCurrent_salary() {
        return current_salary.get();
    }

    public DoubleProperty current_salaryProperty() {
        return current_salary;
    }

    public void setCurrent_salary(double current_salary) {
        this.current_salary.set(current_salary);
    }

    public boolean isTabc_certified() {
        return tabc_certified.get();
    }

    public BooleanProperty tabc_certifiedProperty() {
        return tabc_certified;
    }

    public void setTabc_certified(boolean tabc_certified) {
        this.tabc_certified.set(tabc_certified);
    }

    public Date getTabc_cert_expiration() {
        return tabc_cert_expiration.get();
    }

    public ObjectProperty<Date> tabc_cert_expirationProperty() {
        return tabc_cert_expiration;
    }

    public void setTabc_cert_expiration(Date tabc_cert_expiration) {
        this.tabc_cert_expiration.set(tabc_cert_expiration);
    }

    public String getCitizenship() {
        return citizenship.get();
    }

    public StringProperty citizenshipProperty() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship.set(citizenship);
    }

    public boolean isMarital_status() {
        return marital_status.get();
    }

    public BooleanProperty marital_statusProperty() {
        return marital_status;
    }

    public void setMarital_status(boolean marital_status) {
        this.marital_status.set(marital_status);
    }

    public boolean isJob_application_on_file() {
        return job_application_on_file.get();
    }

    public BooleanProperty job_application_on_fileProperty() {
        return job_application_on_file;
    }

    public void setJob_application_on_file(boolean job_application_on_file) {
        this.job_application_on_file.set(job_application_on_file);
    }

    public int getFk_employee_tax_id() {
        return fk_employee_tax_id.get();
    }

    public IntegerProperty fk_employee_tax_idProperty() {
        return fk_employee_tax_id;
    }

    public void setFk_employee_tax_id(int fk_employee_tax_id) {
        this.fk_employee_tax_id.set(fk_employee_tax_id);
    }

    @Override
    public String toString(){
        return employee_id.get() + ", " + employee_first_name.get() + " " + employee_last_name.get();
    }
}
