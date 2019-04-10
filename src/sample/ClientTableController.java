package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.sql.*;

public class ClientTableController {

    public ObservableList<Client> clientData = FXCollections.observableArrayList();
    public TableView<Client> clientTable;
    public TableColumn<Client, Number> clientIDCol;
    public TableColumn<Client, String> clientFNameCol;
    public TableColumn<Client, String> clientLNameCol;
    public TableColumn<Client, String> clientCoNameCol;
    public TableColumn<Client, Number> clientStatusIDCol;
    public TableColumn<Client, String> clientBillStCol;
    public TableColumn<Client, String> clientBillCityCol;
    public TableColumn<Client, Number> clientZipCol;
    public TableColumn<Client, Number> clientRegionIDCol;
    public TableColumn<Client, String> clientMainPhoneCol;
    public TableColumn<Client, String> client2ndPhoneCol;
    public TableColumn<Client, String> clientEmailCol;
    public TableColumn<Client, String> clientCCEmailCol;
    public TableColumn<Client, String> clientWebsiteCol;


    public void initialize() {
        //Connect to Database
        Connection c = null;

        //assign columns to the property methods in the Vendor class
        clientIDCol.setCellValueFactory(data -> data.getValue().client_idProperty());
        clientFNameCol.setCellValueFactory(data -> data.getValue().client_fnameProperty());
        clientLNameCol.setCellValueFactory(data -> data.getValue().client_lnameProperty());
        clientCoNameCol.setCellValueFactory(data -> data.getValue().client_company_nameProperty());
        clientStatusIDCol.setCellValueFactory(data -> data.getValue().fk_client_status_idProperty());
        clientBillStCol.setCellValueFactory(data -> data.getValue().billing_streetProperty());
        clientBillCityCol.setCellValueFactory(data -> data.getValue().billing_cityProperty());
        clientZipCol.setCellValueFactory(data -> data.getValue().client_zipcodeProperty());
        clientRegionIDCol.setCellValueFactory(data -> data.getValue().fk_region_idProperty());
        clientMainPhoneCol.setCellValueFactory(data -> data.getValue().client_main_phoneProperty());
        client2ndPhoneCol.setCellValueFactory(data -> data.getValue().client_secondary_phoneProperty());
        clientEmailCol.setCellValueFactory(data -> data.getValue().client_emailProperty());
        clientCCEmailCol.setCellValueFactory(data -> data.getValue().client_cc_emailProperty());
        clientWebsiteCol.setCellValueFactory(data -> data.getValue().client_websiteProperty());

        clientTable.setEditable(true);

        //this try/catch loads data from the database into the tableview
        try {
            c = DBClass.connect();
            String SQL = "Select * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Client client = new Client();

                //set the values for this new Client based on what's in the database
                client.client_id.set(rs.getInt("CLIENT_ID")); //columnLabel should match column name in database
                client.client_fname.set(rs.getString("CLIENT_FNAME"));
                client.client_lname.set((rs.getString("CLIENT_LNAME")));
                client.client_main_phone.set(rs.getString("CLIENT_MAIN_PHONE"));
                client.client_secondary_phone.set((rs.getString("CLIENT_SECONDARY_PHONE")));
                client.client_email.set(rs.getString("CLIENT_EMAIL"));
                client.client_cc_email.set(rs.getString("CLIENT_CC_EMAIL"));
                client.billing_street.set(rs.getString("BILLING_STREET"));
                client.billing_city.set(rs.getString("BILLING_CITY"));
                client.fk_region_id.set(rs.getInt("FK_REGION_ID"));
                client.client_zipcode.set(rs.getInt("CLIENT_ZIPCODE"));
                client.client_company_name.set((rs.getString("CLIENT_COMPANY_NAME")));
                client.client_website.set(rs.getString("CLIENT_WEBSITE"));
                client.fk_client_status_id.set((rs.getInt("FK_CLIENT_STATUS_ID")));

                clientData.add(client); //add the new Client to an observable list
            }
            clientTable.setItems(clientData); //set the table items to the observable list's Vendors
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void openClientForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Client Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Client");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editClient() {
        clientFNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientFNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_fname(t.getNewValue())
        );

        clientLNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientLNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_lname(t.getNewValue())
        );
        clientCoNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientCoNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_company_name(t.getNewValue())
        );
        clientStatusIDCol.setCellFactory(TextFieldTableCell.<Client, Number>forTableColumn(new NumberStringConverter()));
        clientStatusIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, Number> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_client_status_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
        clientBillStCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientBillStCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setBilling_street(t.getNewValue())
        );
        clientBillCityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientBillCityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setBilling_city(t.getNewValue())
        );
        clientZipCol.setCellFactory(TextFieldTableCell.<Client, Number>forTableColumn(new NumberStringConverter()));
        clientZipCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, Number> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_zipcode(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
        clientRegionIDCol.setCellFactory(TextFieldTableCell.<Client, Number>forTableColumn(new NumberStringConverter()));
        clientRegionIDCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, Number> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFk_region_id(Integer.parseInt(String.valueOf(t.getNewValue())))
        );
        clientMainPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientMainPhoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_main_phone(t.getNewValue())
        );
        client2ndPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        client2ndPhoneCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_secondary_phone(t.getNewValue())
        );
        clientEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_email(t.getNewValue())
        );
        clientCCEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientCCEmailCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_cc_email(t.getNewValue())
        );
        clientWebsiteCol.setCellFactory(TextFieldTableCell.forTableColumn());
        clientWebsiteCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Client, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setClient_website(t.getNewValue())
        );


    }

    public void saveClientChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if (clientTable.getSelectionModel().isEmpty()) //output an error message if there is nothing selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = clientTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) clientIDCol.getCellObservableValue(row).getValue(); //collect the selected vendor's id

            //collect each cell's value in a variable
            String clientFNameCell = (String) clientFNameCol.getCellObservableValue(row).getValue();
            String clientLNameCell = (String) clientLNameCol.getCellObservableValue(row).getValue();
            String clientCoNameCell = (String) clientCoNameCol.getCellObservableValue(row).getValue();

            Integer ClientStatusCell = (Integer) clientStatusIDCol.getCellObservableValue(row).getValue();
            String clientBillstCell = (String) clientBillStCol.getCellObservableValue(row).getValue();
            String clientBillcityCell = (String) clientBillCityCol.getCellObservableValue(row).getValue();

            Integer ClientzipcodeCell = (Integer) clientZipCol.getCellObservableValue(row).getValue();
            Integer ClientregionCell = (Integer) clientRegionIDCol.getCellObservableValue(row).getValue();
            String clientmainphoneCell = (String) clientMainPhoneCol.getCellObservableValue(row).getValue();

            String clientsecphoneCell = (String) client2ndPhoneCol.getCellObservableValue(row).getValue();
            String clientemailCell = (String) clientEmailCol.getCellObservableValue(row).getValue();
            String clientCCemailCell = (String) clientCCEmailCol.getCellObservableValue(row).getValue();

            String clientwebsiteCell = (String) clientWebsiteCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE CLIENT SET CLIENT_FNAME = ?,CLIENT_LNAME=?,CLIENT_MAIN_PHONE=?,CLIENT_SECONDARY_PHONE=?,CLIENT_EMAIL=?,CLIENT_CC_EMAIL=?,BILLING_STREET=?,BILLING_CITY=?,FK_REGION_ID=?,CLIENT_ZIPCODE=?,CLIENT_COMPANY_NAME=?,CLIENT_WEBSITE=?,FK_CLIENT_STATUS_ID=? " + "WHERE CLIENT_ID =" + currentID);

            statement.setString(1, clientFNameCell);
            statement.setString(2, clientLNameCell);
            statement.setString(3, clientmainphoneCell);
            statement.setString(4, clientsecphoneCell);
            statement.setString(5, clientemailCell);
            statement.setString(6, clientCCemailCell);
            statement.setString(7, clientBillstCell);
            statement.setString(8, clientBillcityCell);
            statement.setInt(9, ClientregionCell);
            statement.setInt(10, ClientzipcodeCell);
            statement.setString(11, clientCoNameCell);
            statement.setString(12, clientwebsiteCell);
            statement.setInt(13, ClientStatusCell);

            statement.execute();
            c.close();
        }


    }

    public void deleteClient() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if (clientTable.getSelectionModel().isEmpty()) //output an error message if nothing is selected
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        } else {
            int row = clientTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) clientIDCol.getCellObservableValue(row).getValue(); //get the id of the selected

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM CLIENT WHERE CLIENT_ID =" + currentID;

            stmt.executeUpdate(SQL);
            clientData.remove(clientTable.getSelectionModel().getSelectedIndex()); //update the observable list
            clientTable.setItems(clientData); //update the tableview so the deletion shows immediately
            c.close();
        }

    }



        //open th client status table
        public void openClientStatusTable () {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Client Status Table.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Client Status Table");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //open the client event table
        public void openClientEventTable () {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Client Event Table.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Client Event Table");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

