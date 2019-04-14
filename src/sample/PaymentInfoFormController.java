package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentInfoFormController {

    public ObservableList<Client> payClientData = FXCollections.observableArrayList();
    public ObservableList<PaymentRequirement> payReqData = FXCollections.observableArrayList();
    public ComboBox<Client> payClientList;
    public ComboBox<PaymentRequirement> payReqList;

    public TextField cvcInput;
    public TextField ccInput;
    public TextField expMonthInput;
    public TextField expYearInput;
    public TextField fNameInput;
    public TextField lNameInput;


    public void initialize(){
        Connection c;

        //these try/catch statements load data into the appropriate drop-down lists
        try{
            c = DBClass.connect();
            String SQL = "SELECT * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Client cl = new Client();

                //assign an ID and Status Name from the database
                cl.client_id.set(rs.getInt("CLIENT_ID"));
                cl.client_fname.set(rs.getString("CLIENT_FNAME"));
                cl.client_lname.set(rs.getString("CLIENT_LNAME"));

                payClientData.add(cl); //add these to an observable list
            }
            payClientList.setItems(payClientData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Pay Info Form's Client Data");
        }

        try{
            c = DBClass.connect();
            String SQL = "SELECT * from PAYMENT_REQUIREMENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                PaymentRequirement pir = new PaymentRequirement();

                //assign an ID and Status Name from the database
                pir.payment_req_id.set(rs.getInt("PAYMENT_REQ_ID"));
                pir.payment_req_name.set(rs.getString("PAYMENT_REQ_NAME"));

                payReqData.add(pir); //add these to an observable list
            }
            payReqList.setItems(payReqData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Pay Info Form's Requirement Data");
        }

    }

    public void addPayInfo() throws SQLException {
        Connection conn = DBClass.connect();
        Statement stmt = conn.createStatement();

        if (fNameInput.getText().trim().isEmpty() ||
                lNameInput.getText().trim().isEmpty() ||
                ccInput.getText().trim().isEmpty() ||
                cvcInput.getText().trim().isEmpty() ||
                expMonthInput.getText().trim().isEmpty() ||
                expYearInput.getText().trim().isEmpty() ||
                payClientList.getSelectionModel().isEmpty() ||
                payReqList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        } else {
            Integer client = payClientList.getSelectionModel().getSelectedItem().getClient_id();
            String fName = fNameInput.getText();
            String lName = lNameInput.getText();
            Integer cc = Integer.parseInt(ccInput.getText());
            Integer cvc = Integer.parseInt(cvcInput.getText());
            Integer month = Integer.parseInt(expMonthInput.getText());
            Integer year = Integer.parseInt(expYearInput.getText());
            Integer payReq = payReqList.getSelectionModel().getSelectedItem().getPayment_req_id();

            String sqlStatement = "INSERT INTO PAYMENT_INFORMATION" + "(CLIENT_ID, PAYMENT_FIRST_NAME, PAYMENT_LAST_NAME, CC_NUMBER, CVC_NUMBER, EXP_MONTH_DATE, EXP_YEAR_DATE, PAYMENT_REQUIREMENT)" +
                    " VALUES ('" + client + "', '" + fName + "', '" + lName + "', '" + cc + "', '" + cvc + "', '" + month + "', '" + year + "', '" + payReq + "')";

            //If statement for validations before submission
            stmt.executeUpdate(sqlStatement);

            conn.close();

        }
    }
}
