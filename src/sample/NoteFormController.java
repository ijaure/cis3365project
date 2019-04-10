package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;

public class NoteFormController {
    public ComboBox<NoteType> noteTypeList;
    public ObservableList<NoteType> noteTypeData = FXCollections.observableArrayList();

    public TextField noteDescription = new TextField();

    public void initialize() {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Connection c;

        //these try/catch statements load data into the appropriate drop-down lists
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url);
            String SQL = "SELECT * from NOTE_TYPE";
            ResultSet rs = c.createStatement().executeQuery(SQL);

            while (rs.next()) {
                NoteType vStatus = new NoteType(); //make a new Note type object

                //assign an ID and Status Name from the database
                vStatus.note_type_id.set(rs.getInt("NOTE_TYPE_ID"));
                vStatus.note_type_name.set(rs.getString("NOTE_TYPE_NAME"));

                noteTypeData.add(vStatus); //add these to an observable list
            }
            noteTypeList.setItems(noteTypeData); //set the ComboBox values to the observable list
            c.close();
        } catch (Exception e) { //catch any exceptions
            e.printStackTrace();
            System.out.println("Error on Building Client Status Data");
        }

    }
    public void addNote() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();

        if(noteDescription.getText().trim().isEmpty()||
            noteTypeList.getSelectionModel().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Values");
                alert.setHeaderText("There Are Missing Values");
                alert.setContentText("Please check that all fields are complete before submitting.");
                alert.showAndWait();

            }
        else{
            String Note_Description=noteDescription.getText();
            Integer Note_Type= noteTypeList.getSelectionModel().getSelectedItem().getNote_type_id();


            String sqlStatement="INSERT INTO NOTE"+"(NOTE_DESCRIPTION,FK_NOTE_TYPE_ID)"+"VALUES('" + Note_Description +"','"+Note_Type +"')";
            //If statement for validations before submission

            stmt.executeUpdate(sqlStatement);

            conn.close();
        }
        }
    }

