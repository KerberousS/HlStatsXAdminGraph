package javafx;

import hibernate.DBOperations;
import hibernate.Server;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageServerWindowController implements Initializable {

    @FXML
    private BorderPane manageServerWindow;

    @FXML
    private ListView<String> serverList;

    @FXML
    private Button closeButton;

    @FXML
    private Button addNewServerButton;

    @FXML
    private Text Error;

    public static Server chosenServer;
    private List<Server> serversList;

    //FXML FILES
    private String baseWindowFXMLFile = "BaseWindow.fxml";
    private String addServerFXMLFile = "servers/AddServer.fxml";
    private String editServerFXMLFile = "servers/EditServer.fxml";

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        this.changeScene(baseWindowFXMLFile, event);
    }


    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        this.changeScene(addServerFXMLFile, event);
    }

    @FXML
    protected void handleEditServerButton(ActionEvent event) {
            if (serverList.getSelectionModel().getSelectedItem() == null) {
                Error.setText("Please choose a server first!");
            }
            else {
                int serverIndex = serverList.getSelectionModel().getSelectedIndex();
                chosenServer = serversList.get(serverIndex);

                this.changeScene(editServerFXMLFile, event);
            }
    }

    @FXML
    protected void handleDeleteServerButton(ActionEvent event) {
        if (serverList.getSelectionModel().getSelectedItem() == null) {
            Error.setText("Please choose a server first!");
        } else {
            int serverIndex = serverList.getSelectionModel().getSelectedIndex();
            chosenServer = serversList.get(serverIndex);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete server");
            alert.setContentText("Are you sure you want to delete server \"" + chosenServer.getServerName() + "\"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBOperations.deleteServerRecord(chosenServer.getServerID());

                //Initialize server list for server dropdown
                serverList.getItems().clear();
                addServersList.restart();
            } else {
                // Do nothing
            }
        }
    }



    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverList != null : "fx:id=\"serverList\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert closeButton != null : "fx:id=\"closebutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addNewServerButton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

        addServersList.restart();
    }

    Service addServersList = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    serversList = DBOperations.displayServerRecords();

                    //Initialize server list for server dropdown

                    if (serversList.isEmpty()) {
                        Error.setText("There are no servers added, add a new server");
                        Error.setFill(Color.BLUE);
                    } else {
                        for (Server s : serversList)
                            serverList.getItems().add(s.getServerName());
                    }
                    return null;
                }
            };
        }
    };

    private void changeScene(String windowFXMLFile, ActionEvent event) {
        Parent window = null;
        try {
            window = FXMLLoader.load(getClass().getResource(windowFXMLFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(window);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}