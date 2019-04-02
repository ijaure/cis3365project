package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

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


    public void initialize(){
//Connect to Database
        Connection c;
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

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

        //this try/catch loads data from the database into the tableview
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "Select * from CLIENT";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
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
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void openClientForm()
    {
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
}
