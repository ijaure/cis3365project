package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProductVendorTableController {

    //no need for this table if products and orders can already be seen & edited in order line
    //also products and vendors can be seen & edited in product table
    //keeping controller and fxml in case we decide to use it later

    //if used, link in the menu of Product or Vendor tables

    public ObservableList<ProductVendor> pvData = FXCollections.observableArrayList();
    public TableView<ProductVendor> pvTable;
    public TableColumn<ProductVendor, Number> pvIDCol;
    public TableColumn<ProductVendor, Number> pIDCol;
    public TableColumn<ProductVendor, Number> vIDCol;
    public TableColumn<ProductVendor, Number> oIDCol;

    public void initialize(){


    }

    public void editProductVendor(){

    }

    public void saveProductVendorChanges(){

    }

    //if a product vendor entry is deleted, is can't be restored
    // since it was created when the Order Line was made
    public void deleteProductVendor(){

    }



}
