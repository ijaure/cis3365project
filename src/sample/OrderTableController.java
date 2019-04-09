package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

public class OrderTableController {

    public ObservableList<Order> orderData = FXCollections.observableArrayList();
    public TableView<Order> orderTable;
    public TableColumn<Order, Number> orderIDCol;
    public TableColumn<Order, Number> orderClientCol;
    public TableColumn<Order, Number> orderStatusCol;
    public TableColumn<Order, Date> orderCompleteDateCol;
    public TableColumn<Order, Date> orderDateCol;
    public TableColumn<Order, String> orderNotesCol;
    public TableColumn<Order, Date> orderDelDateCol;
    public TableColumn<Order, Number> orderDelTimeCol;

    //initialize the table data; reference other table controllers
    public void initialize(){

    }

    public void openOrderForm(){

    }

    public void editOrder(){

    }

    public void saveOrderChanges(){

    }

    public void deleteOrder(){

    }

    public void openOrderStatusTable(){

    }

    public void openOrderLineTable(){

    }


}
