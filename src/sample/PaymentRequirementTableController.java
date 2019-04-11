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

import java.sql.*;

public class PaymentRequirementTableController {

    public ObservableList<PaymentRequirement> payReqData = FXCollections.observableArrayList();
    public TableView<PaymentRequirement> payReqTable;
    public TableColumn<PaymentRequirement, Number> payReqIDCol;
    public TableColumn<PaymentRequirement, String> payReqNameCol;

    public void initialize(){
        Connection c;

        payReqIDCol.setCellValueFactory(data -> data.getValue().payment_req_idProperty());
        payReqNameCol.setCellValueFactory(data -> data.getValue().payment_req_nameProperty());

        payReqTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from PAYMENT_REQUIREMENT";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                PaymentRequirement pr = new PaymentRequirement();

                //set the values for this new Vendor based on what's in the database
                pr.payment_req_id.set(rs.getInt("PAYMENT_REQ_ID")); //columnLabel should match column name in database
                pr.payment_req_name.set(rs.getString("PAYMENT_REQ_NAME"));

                payReqData.add(pr); //add the new Vendor to an observable list
            }
            payReqTable.setItems(payReqData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Pay Requirement Data");
        }

    }

    public void editPayReq(){
        payReqNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        payReqNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PaymentRequirement, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPayment_req_name(t.getNewValue())
        );

    }

    public void savePayReqChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(payReqTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = payReqTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) payReqIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) payReqNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE PAYMENT_REQUIREMENT SET PAYMENT_REQ_NAME = ? "
                    + "WHERE PAYMENT_REQ_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }

    }

    public void deletePayReq() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(payReqTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = payReqTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) payReqIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM PAYMENT_REQUIREMENT WHERE PAYMENT_REQ_ID =" + currentID;

            stmt.executeUpdate(SQL);
            payReqData.remove(payReqTable.getSelectionModel().getSelectedIndex()); //update the observable list
            payReqTable.setItems(payReqData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }

    public void openPayInfoTable(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Payment Info Table.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Payment Information");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openNewTypeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Type Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Payment Requirement");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

}

}
