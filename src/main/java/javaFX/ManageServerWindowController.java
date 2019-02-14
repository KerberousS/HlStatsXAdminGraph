package javaFX;

import getInfo.SummarizeTime;
import hibernate.Server;
import hibernate.ServerOperations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
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
import java.util.Set;

public class ManageServerWindowController implements Initializable {

    @FXML
    private GridPane manageServerWindow;

    @FXML
    private ListView serverList;

    @FXML
    private HBox closebutton;

    @FXML
    private HBox addnewserverbutton;

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        try {
            Parent baseWindow = FXMLLoader.load(getClass().getResource("baseWindow.fxml"));
            manageServerWindow.getChildren().setAll(baseWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        try {
            Parent addServerWindow = FXMLLoader.load(getClass().getResource("addserver.fxml"));
            manageServerWindow.getChildren().setAll(addServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEditServerButton(ActionEvent event) {
        try {
            Parent addServerWindow = FXMLLoader.load(getClass().getResource("editserver.fxml"));
            manageServerWindow.getChildren().setAll(addServerWindow);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDeleteServerButton(ActionEvent event) {
        ManageServerWindowController mswc = new ManageServerWindowController();
        String chosenServerName = mswc.getSelectedServer();

        ServerOperations serverOperations = new ServerOperations();
        serverOperations.deleteRecordByName(chosenServerName);
    }



    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverList != null : "fx:id=\"serverList\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert closebutton != null : "fx:id=\"closebutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addnewserverbutton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

        //Initialize server list for server dropdown
        List ListviewServers = ServerOperations.displayRecords();
        serverList.setItems(FXCollections.observableArrayList(ListviewServers.toArray()));
    }
    public String getSelectedServer() {
        String choosenServerName = serverList.getSelectionModel().getSelectedItem().toString();
        return choosenServerName;
    }
}