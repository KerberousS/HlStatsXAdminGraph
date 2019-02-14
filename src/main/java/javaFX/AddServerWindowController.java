package javaFX;

import hibernate.ServerOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox cancelbutton;

    @FXML
    private HBox addnewserverbutton;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("manageservers.fxml"));
            addServerWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        String serverName = serverNameTextField.getText();
        System.out.println(serverName);

        ServerOperations serverOperations = new ServerOperations();
        serverOperations.createServerRecord(serverName);
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addnewserverbutton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

    }
}