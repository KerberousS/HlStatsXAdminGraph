package javaFX;

import getInfo.SummarizeTime;
import hibernate.Server;
import hibernate.ServerOperations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class addserverwindowController implements Initializable {

//@TODO : Make this a manage server window not add

    @FXML
    private TextField serverNameTextField;

    @FXML
    private HBox cancelbutton;

    @FXML
    private HBox addnewserverbutton;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        String serverName = serverNameTextField.getText();
        Long serverID = Long.valueOf(1);

        ServerOperations serverOperations = new ServerOperations();
        serverOperations.createServerRecord(serverID, serverName);
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addnewserverbutton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

    }
}