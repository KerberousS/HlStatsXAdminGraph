package javaFX;

import hibernate.AdminOperations;
import hibernate.ServerOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddServerWindowController implements Initializable {

    @FXML
    private GridPane addServerWindow;

    @FXML
    private TextField serverNameTextField;

    @FXML
    private HBox cancelButton;

    @FXML
    private HBox addNewServerButton;

    @FXML
    private Text updateStatus;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("Servers/ManageServers.fxml"));
            addServerWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        String serverName = serverNameTextField.getText();
        System.out.println(serverName);

        try {
            if (serverName.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            } else {
                ServerOperations serverOperations = new ServerOperations();
                serverOperations.createServerRecord(serverName);

                updateStatus.setText("Server " + serverName + " was succesfully created!");
                updateStatus.setFill(Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateStatus.setText("Something went terribly wrong!");
            updateStatus.setFill(Color.RED);
        }
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'AddServer.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddServer.fxml'.";
        assert addNewServerButton != null : "fx:id=\"addNewServerButton\" was not injected: check your FXML file 'AddServer.fxml'.";

    }
}