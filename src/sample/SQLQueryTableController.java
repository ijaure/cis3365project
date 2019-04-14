package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SQLQueryTableController {


    public TableView<ObservableList<String>> dynamicTable;
    public ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();

    public TextArea queryInput;

    //initialize the tables
    public void queryButtonClicked() {
        //clear the columns and rows from the previous query
        dynamicTable.getColumns().clear();
        dynamicTable.getItems().clear();

        //Connect to Database
        Connection c;
        if(!queryInput.getText().trim().isEmpty()) {
            try {
                c = DBClass.connect();
                String SQL = queryInput.getText();
                ResultSet rs = c.createStatement().executeQuery(SQL);
                int index = rs.getMetaData().getColumnCount();
                //dynamically add table columns, so they are made based off database columns
                //Not sure if this method will make it harder to add data later
                for (int i = 0; i < index; i++) {
                    final int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    dynamicTable.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                    //add to observable list
                    while (rs.next()) {
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                            //Iterate Column
                            row.add(rs.getString(k));
                        }
                        //System.out.println("Row [1] added " + row);
                        tableData.add(row);
                    }
                    //add to tableview
                    dynamicTable.setItems(tableData);
                }
                c.close();
            } catch (Exception e) { //catch any exceptions
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Statement did not return a result set.");
                alert.setContentText("Please check query syntax. If this was a delete/insert/update query, it has been successfully submitted.");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("There Are Missing Values");
            alert.setContentText("Please check that all fields are complete before submitting.");
            alert.showAndWait();
        }

        }
}
