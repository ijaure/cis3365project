package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.sql.*;
import java.util.Date;


public class PaymentInfoTableController {
    public ObservableList<PaymentInformation> payInfoData = FXCollections.observableArrayList();
    public TableView<PaymentInformation> payInfoTable;
    public TableColumn<PaymentInformation, Number> payInfoIDCol;
    public TableColumn<PaymentInformation, Number> payInfoClientCol;
    public TableColumn<PaymentInformation, String> fNameCol;
    public TableColumn<PaymentInformation, String> lNameCol;
    public TableColumn<PaymentInformation, Number> ccNumbCol;
    public TableColumn<PaymentInformation, Number> cvcNumbCol;
    public TableColumn<PaymentInformation, Number> expMonthCol;
    public TableColumn<PaymentInformation, Number> expYearCol;
    public TableColumn<PaymentInformation, String> payInfoReqCol;

    public void initialize(){
        //Connect to Database
        Connection c;

        payInfoIDCol.setCellValueFactory(data -> data.getValue().payment_info_idProperty());
        payInfoClientCol.setCellValueFactory(data -> data.getValue().fk_client_idProperty());
        fNameCol.setCellValueFactory(data -> data.getValue().payment_first_nameProperty());
        lNameCol.setCellValueFactory(data -> data.getValue().payment_last_nameProperty());
        ccNumbCol.setCellValueFactory(data -> data.getValue().cc_numberProperty());
        cvcNumbCol.setCellValueFactory(data -> data.getValue().cvc_numberProperty());
        expMonthCol.setCellValueFactory(data -> data.getValue().exp_month_dateProperty());
        expYearCol.setCellValueFactory(data -> data.getValue().exp_year_dateProperty());
        payInfoReqCol.setCellValueFactory(data -> data.getValue().payment_requirementProperty());

        payInfoTable.setEditable(true); //so we can edit rows later

        try{
            c = DBClass.connect();
            String SQL = "Select * from PAYMENT_INFORMATION";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                PaymentInformation pi = new PaymentInformation();

                //set the values based on what's in the database
                pi.payment_info_id.set(rs.getInt("PAYMENT_INFO_ID")); //columnLabel should match column name in database
                pi.fk_client_id.set(rs.getInt("FK_CLIENT_ID"));
                pi.payment_first_name.set((rs.getString("PAYMENT_FIRST_NAME")));
                pi.payment_last_name.set(rs.getString("PAYMENT_LAST_NAME"));
                pi.cc_number.set((rs.getInt("CC_NUMBER")));
                pi.cvc_number.set(rs.getInt("CVC_NUMBER"));
                pi.exp_month_date.set(rs.getInt("EXP_MONTH_DATE"));
                pi.exp_year_date.set(rs.getInt("EXP_YEAR_DATE"));
                pi.payment_requirement.set(rs.getString("PAYMENT_REQUIREMENT"));

                payInfoData.add(pi); //add to an observable list
            }
            payInfoTable.setItems(payInfoData); //set the table items to the observable list
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Pay Info Data");
        }


    }

    public void editPayInfo(){
        payInfoClientCol.setCellFactory(TextFieldTableCell.<PaymentInformation, Number>forTableColumn(new NumberStringConverter()));
        payInfoClientCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_client_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        fNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPayment_first_name(t.getNewValue())
        );

        lNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPayment_last_name(t.getNewValue())
        );

        ccNumbCol.setCellFactory(TextFieldTableCell.<PaymentInformation, Number>forTableColumn(new NumberStringConverter()));
        ccNumbCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCc_number(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        cvcNumbCol.setCellFactory(TextFieldTableCell.<PaymentInformation, Number>forTableColumn(new NumberStringConverter()));
        cvcNumbCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCvc_number(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        expMonthCol.setCellFactory(TextFieldTableCell.<PaymentInformation, Number>forTableColumn(new NumberStringConverter()));
        expMonthCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setExp_month_date(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        expYearCol.setCellFactory(TextFieldTableCell.<PaymentInformation, Number>forTableColumn(new NumberStringConverter()));
        expYearCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, Number> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setExp_year_date(Integer.parseInt(String.valueOf(t.getNewValue())))
        );

        payInfoReqCol.setCellFactory(TextFieldTableCell.forTableColumn());
        payInfoReqCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentInformation, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPayment_requirement(t.getNewValue())
        );

    }

    public void savePayInfoChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(payInfoTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = payInfoTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) payInfoIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer clientCell = (Integer) payInfoClientCol.getCellObservableValue(row).getValue();
            String fNameCell = (String) fNameCol.getCellObservableValue(row).getValue();
            String lNameCell = (String) lNameCol.getCellObservableValue(row).getValue();
            Integer ccCell = (Integer) ccNumbCol.getCellObservableValue(row).getValue();
            Integer cvcCell = (Integer) cvcNumbCol.getCellObservableValue(row).getValue();
            Integer monthCell = (Integer) expMonthCol.getCellObservableValue(row).getValue();
            Integer yearCell = (Integer) expYearCol.getCellObservableValue(row).getValue();
            String payReqCell = (String) payInfoReqCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE PAYMENT_INFORMATION SET FK_CLIENT_ID = ?, " +
                    "PAYMENT_FIRST_NAME = ?, PAYMENT_LAST_NAME = ?, CC_NUMBER = ?, CVC_NUMBER = ?, EXP_MONTH_DATE = ?, "
                    + "EXP_YEAR_DATE = ?, PAYMENT_REQUIREMENT = ? "
                    + "WHERE PAYMENT_INFO_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, clientCell);
            statement.setString(2, fNameCell);
            statement.setString(3, lNameCell);
            statement.setInt(4, ccCell);
            statement.setInt(5, cvcCell);
            statement.setInt(6, monthCell);
            statement.setInt(7, yearCell);
            statement.setString(8, payReqCell);
            statement.execute();
            c.close();
        }

    }

    public void deletePayInfo() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(payInfoTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = payInfoTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) payInfoIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PAYMENT_INFORMATION WHERE PAYMENT_INFO_ID =" + currentID;

            stmt.executeUpdate(SQL);
            payInfoData.remove(payInfoTable.getSelectionModel().getSelectedIndex()); //update the observable list
            payInfoTable.setItems(payInfoData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openPayInfoForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Payment Info Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Payment Info");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openClientTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ClientTable Updated.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPaymentReqTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Payment Requirements Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Payment Requirement Table");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
