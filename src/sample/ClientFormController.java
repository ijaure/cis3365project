package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientFormController {

    public ComboBox<Client_Status> clientStatusList;
    public ObservableList<Client_Status> clientStatusData = FXCollections.observableArrayList();

    public ComboBox<Region> clientRegionList;
    public ObservableList<Region> clientRegionData = FXCollections.observableArrayList();

    public TextField FName;
    public TextField LName;
    public TextField MainPhone;
    public TextField SecPhone;
    public TextField Email;
    public TextField CCEmail;
    public TextField BillingStreet;
    public TextField BillingCity;
    public TextField zipcode;
    public TextField ClientWebsite;
    public TextField clientCompany;



    public void initialize(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c;

        //these try/catch statements load Vendor Status and Regions into the appropriate drop-down lists
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from CLIENT_STATUS";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Client_Status vStatus = new Client_Status(); //make a new Vendor_status object

                //assign an ID and Status Name from the database
                vStatus.client_status_id.set(rs.getInt("CLIENT_STATUS_ID"));
                vStatus.client_status_name.set(rs.getString("CLIENT_STATUS_NAME"));

                clientStatusData.add(vStatus); //add these to an observable list
            }
            clientStatusList.setItems(clientStatusData); //set the ComboBox values to the observable list
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Vendor Status Data");
        }

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from REGION";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while(rs.next()){
                Region vRegion = new Region();
                vRegion.region_id.set(rs.getInt("REGION_ID"));
                vRegion.region_name.set(rs.getString("REGION_NAME"));

                clientRegionData.add(vRegion);
            }
            clientRegionList.setItems(clientRegionData);
            c.close();
        }
        catch(Exception e){ //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Vendor Region Data");
        }
    }

    //add a new client to the database
    public void addClient(){
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url);

            if(conn != null) //if it connected successfully
            {
                System.out.println("Connection successful");
            }
            Statement stmt = conn.createStatement();
            String Client_First= FName.getText();
            String Client_Last=LName.getText();
            String Client_Main_Phone=MainPhone.getText();
            String Client_S_Phone= SecPhone.getText();
            String Client_Email=Email.getText();
            String Client_CC_Email=CCEmail.getText();
            String Client_Street=BillingStreet.getText();
            String Client_City=BillingCity.getText();

            Integer Client_Zip= Integer.parseInt(zipcode.getText());

            String Client_Company_name= clientCompany.getText();
            String Client_Website=ClientWebsite.getText();
            Integer Region=clientRegionList.getSelectionModel().getSelectedItem().getRegion_id();
            Integer Client_Status=clientStatusList.getSelectionModel().getSelectedItem().getClient_status_id();
            String sqlStatement = "INSERT INTO Client " +"(CLIENT_FNAME,CLIENT_LNAME,CLIENT_MAIN_PHONE,CLIENT_SECONDARY_PHONE,CLIENT_EMAIL, CLIENT_CC_EMAIL,BILLING_STREET,BILLING_CITY,FK_REGION_ID,CLIENT_ZIPCODE,CLIENT_COMPANY_NAME,CLIENT_WEBSITE,FK_CLIENT_STATUS_ID)" +
                    "VALUES ('" + Client_First + "', '"+ Client_Last +"', '" + Client_Main_Phone +"', '" + Client_S_Phone +"', '" + Client_Email +"', '" +Client_CC_Email +"','" +Client_Street +"', '" +Client_City +"','" +Region+"','" +Client_Zip +"','" +Client_Company_name +"','"+ Client_Website+"','"+ Client_Status+"')";
            //If statement for validations before submission
            stmt.executeUpdate(sqlStatement);




            conn.close();
        }
        catch (Exception e) //catch any exceptions
        {
            e.printStackTrace();
        }





    }
}


