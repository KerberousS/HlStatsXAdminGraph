package javafx;

import hibernate.DBOperations;
import hibernate.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageServerWindowController implements Initializable {

    @FXML
    private GridPane manageServerWindow;

    @FXML
    private ListView<String> serverList;

    @FXML
    private HBox closeButton;

    @FXML
    private HBox addNewServerButton;

    @FXML
    private Text Error;

    public static Server chosenServer;
    private List<Server> serversList = DBOperations.displayServerRecords();
    //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        try {
            Parent baseWindow = FXMLLoader.load(getClass().getResource("BaseWindow.fxml"));
            manageServerWindow.getChildren().setAll(baseWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        try {
            Parent addServerWindow = FXMLLoader.load(getClass().getResource("servers/AddServer.fxml"));
            manageServerWindow.getChildren().setAll(addServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEditServerButton(ActionEvent event) {
        try {
            if (serverList.getSelectionModel().getSelectedItem() == null) {
                Error.setText("Please choose a server first!");
            }
            else {
                int serverIndex = serverList.getSelectionModel().getSelectedIndex();
                chosenServer = serversList.get(serverIndex);

                Parent addServerWindow = FXMLLoader.load(getClass().getResource("servers/EditServer.fxml"));
                manageServerWindow.getChildren().setAll(addServerWindow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDeleteServerButton(ActionEvent event) {
        if (serverList.getSelectionModel().getSelectedItem() == null) {
            Error.setText("Please choose a server first!");
        } else {
            int serverIndex = serverList.getSelectionModel().getSelectedIndex();
            chosenServer = serversList.get(serverIndex);

            //TODO: Add CONFIRM WINDOW!

            DBOperations.deleteServerRecord(chosenServer.getServerID());

            //Initialize server list for server dropdown
            serverList.getItems().clear();
            serverList.getItems().addAll(DBOperations.displayServerRecords());
        }
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverList != null : "fx:id=\"serverList\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert closeButton != null : "fx:id=\"closebutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addNewServerButton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

        if (serversList.isEmpty()) {
            Error.setText("There are no servers added, add a new server");
            Error.setFill(Color.BLUE);
        } else {
            for (Server s : serversList)
                serverList.getItems().add(s.getServerName());
        }
    }
}