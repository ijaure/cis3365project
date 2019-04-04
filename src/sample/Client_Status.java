package sample;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client_Status {
    IntegerProperty client_status_id= new SimpleIntegerProperty();
    StringProperty client_status_name=new SimpleStringProperty();

    public Client_Status(IntegerProperty client_status_id, StringProperty client_status_name){
        this.client_status_id=client_status_id;
        this.client_status_name=client_status_name;
    }
    public Client_Status(){

    }
    public int getClient_status_id(){
        return client_status_id.get();
    }
    public IntegerProperty client_status_id_property(){
        return client_status_id;
    }
    public void setClient_status_id(int client_status_id){
        this.client_status_id.set(client_status_id);

    }
    public String getClient_status_name(){
        return client_status_name.get();
    }
    public StringProperty client_status_name_property(){
        return client_status_name;
    }
    public void setClient_status_name(String client_status_name){
        this.client_status_name.set(client_status_name);
    }
    @Override
    public String toString(){
        return client_status_id.get()+", "+client_status_name.get();
    }

}
