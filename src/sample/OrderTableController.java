package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

public class OrderTableController {

    //public ObservableList<Vendor> vendorData = FXCollections.observableArrayList();
    public TableView<Vendor> orderTable;
    public TableColumn<Vendor, Number> orderIDCol;
    public TableColumn<Vendor, Date> orderDateCol;
    public TableColumn<Vendor, Number> orderClientCol;
    public TableColumn<Vendor, Number> orderStatusCol;
    public TableColumn<Vendor, Date> orderDelDateCol;
    public TableColumn<Vendor, Number> orderDelTimeCol;
    public TableColumn<Vendor, Date> orderCompleteDateCol;
    public TableColumn<Vendor, String> orderNotesCol;







}
