package javaFX;

import getInfo.SummarizeTime;
import hibernate.Server;
import hibernate.ServerOperations;
import hibernate.TestJDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseWindowController implements Initializable {

    @FXML
    private GridPane basewindow;

    @FXML
    private Text databaseStatus;

    @FXML
    private ComboBox<Object> serverDropdown;

    @FXML
    private HBox chooseServerButton;

    @FXML
    private Text updateStatus;

    public static String choosenServer;

    @FXML
    protected void handleManageServerButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("Servers/ManageServers.fxml"));
            basewindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleChooseServerButton(ActionEvent event) {
        try {
            if (serverDropdown.getSelectionModel().getSelectedItem() == null) {
                updateStatus.setText("Please choose a server first!");
                updateStatus.setFill(Color.RED);
            } else {
                choosenServer = serverDropdown.getSelectionModel().getSelectedItem().toString();
                Parent adminsWindow = FXMLLoader.load(getClass().getResource("Admins/Admins.fxml"));
                basewindow.getChildren().setAll(adminsWindow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleConfigureDatabaseButton(ActionEvent event) {
        try {
            Parent databaseConfigWindow = FXMLLoader.load(getClass().getResource("DatabaseConfiguration.fxml"));
            basewindow.getChildren().setAll(databaseConfigWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
       assert serverDropdown != null : "fx:id=\"serverDropdown\" was not injected: check your FXML file 'BaseWindow.fxml'.";

       //Set database status
        TestJDBCConnection testDB = new TestJDBCConnection();
        String status = testDB.TestConnection();
        if (status=="Connection Established"){
            databaseStatus.setText("Database status: " + status);
            databaseStatus.setFill(Color.GREEN);
        }
        else {
            databaseStatus.setText("Database status: " + status);
            databaseStatus.setFill(Color.RED);
        }

        try {
            //Initialize server list for server dropdown
            List ListviewServers = ServerOperations.displayRecords();
            serverDropdown.setItems(FXCollections.observableArrayList(ListviewServers.toArray()));
            System.out.println(ListviewServers);
        } catch (Exception e) {

        }
    }
}