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


public class RegionTableController {
    public ObservableList<Region> rData = FXCollections.observableArrayList();
    public TableView<Region> regTable;
    public TableColumn<Region, Number> rIDCol;
    public TableColumn<Region, String> rNameCol;


    public void initialize(){
        Connection c;

        rIDCol.setCellValueFactory(data -> data.getValue().region_idProperty());
        rNameCol.setCellValueFactory(data -> data.getValue().region_nameProperty());

        regTable.setEditable(true);

        try{
            c = DBClass.connect();
            String SQL = "Select * from REGION";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            while(rs.next()){
                Region r = new Region();

                //set the values for this new Vendor based on what's in the database
                r.region_id.set(rs.getInt("REGION_ID")); //columnLabel should match column name in database
                r.region_name.set(rs.getString("REGION_NAME"));

                rData.add(r); //add the new Vendor to an observable list
            }
            regTable.setItems(rData); //set the table items to the observable list's Vendors
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Region Data");
        }

    }

    public void editRegion(){
        rNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Region, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRegion_name(t.getNewValue())
        );

    }

    public void saveRegionChanges() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();

        if(regTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else{
            int row = regTable.getSelectionModel().getSelectedIndex(); //get the index of the selected row
            Integer currentID = (Integer) rIDCol.getCellObservableValue(row).getValue(); //collect the selected id

            String nameCell = (String) rNameCol.getCellObservableValue(row).getValue();

            PreparedStatement statement = c.prepareStatement("UPDATE REGION SET REGION_NAME = ? "
                    + "WHERE REGION_ID ="+currentID);

            // set the value of each question mark in the sql statement to the variables above
            // make sure these are in the correct order
            statement.setString(1, nameCell);
            statement.execute();
            c.close();
        }

    }

    public void deleteRegion() throws SQLException {
        //get the connection
        Connection c = DBClass.connect();
        Statement stmt = c.createStatement();

        if(regTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Row Selection");
            alert.setContentText("Please select a row in the table");
            alert.showAndWait();
        }
        else {

            int row = regTable.getSelectionModel().getSelectedIndex(); //get the index of the current selection
            Integer currentID = (Integer) rIDCol.getCellObservableValue(row).getValue(); //get the id of the selection

            //delete the vendor whose id matches the currently selected vendor's id
            String SQL = "DELETE FROM REGION WHERE REGION_ID =" + currentID;

            stmt.executeUpdate(SQL);
            rData.remove(regTable.getSelectionModel().getSelectedIndex()); //update the observable list
            regTable.setItems(rData); //update the tableview so the deletion shows immediately
            c.close();
        }
    }

    public void openNewTypeForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/New Type Form.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Region");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
