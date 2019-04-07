package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeTaxInfo {

    IntegerProperty employee_tax_id = new SimpleIntegerProperty();
    StringProperty tax_form = new SimpleStringProperty();

    public EmployeeTaxInfo() {
    }

    public EmployeeTaxInfo(IntegerProperty employee_tax_id, StringProperty tax_form) {
        this.employee_tax_id = employee_tax_id;
        this.tax_form = tax_form;
    }

    public int getEmployee_tax_id() {
        return employee_tax_id.get();
    }

    public IntegerProperty employee_tax_idProperty() {
        return employee_tax_id;
    }

    public void setEmployee_tax_id(int employee_tax_id) {
        this.employee_tax_id.set(employee_tax_id);
    }

    public String getTax_form() {
        return tax_form.get();
    }

    public StringProperty tax_formProperty() {
        return tax_form;
    }

    public void setTax_form(String tax_form) {
        this.tax_form.set(tax_form);
    }

    @Override
    public String toString(){
        return employee_tax_id.get() + ", " + tax_form.get();
    }
}
