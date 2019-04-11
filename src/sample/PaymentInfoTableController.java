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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void savePayInfoChanges(){
        /*//get the connection
        Connection c = DBClass.connect();

        if(orderTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {
            int row = orderTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) orderIDCol.getCellObservableValue(row).getValue(); //collect the selection id

            //collect each cell's value in a variable
            Integer clientCell = (Integer) orderClientCol.getCellObservableValue(row).getValue();
            Integer statusCell = (Integer) orderStatusCol.getCellObservableValue(row).getValue();

            java.util.Date dateCell = orderDateCol.getCellObservableValue(row).getValue();
            java.sql.Date dateCellSQL = new java.sql.Date(dateCell.getTime());

            java.util.Date compDateCell = orderCompleteDateCol.getCellObservableValue(row).getValue();
            java.sql.Date compDateCellSQL = new java.sql.Date(compDateCell.getTime());

            String notesCell = (String) orderNotesCol.getCellObservableValue(row).getValue();

            java.util.Date delDateCell = orderDelDateCol.getCellObservableValue(row).getValue();
            java.sql.Date delDateCellSQL = new java.sql.Date(delDateCell.getTime());

            String delTimeCell = (String) orderDelTimeCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE [ORDER] SET FK_CLIENT_ID = ?, " +
                    "FK_ORDER_STATUS_ID = ?, ORDER_DATE = ?, ORDER_COMPLETE_DATE = ?, ORDER_NOTES = ?, ORDER_DELIVERY_DATE = ?, "
                    + "ORDER_DELIVERY_TIME = ? "
                    + "WHERE ORDER_ID =" + currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setInt(1, clientCell);
            statement.setInt(2, statusCell);
            statement.setDate(3, dateCellSQL);
            statement.setDate(4, compDateCellSQL);
            statement.setString(5, notesCell);
            statement.setDate(6, delDateCellSQL);
            statement.setString(7, delTimeCell);
            statement.execute();
            c.close();
        }*/

    }

    public void deletePayInfo(){

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
