package javafx;

import hibernate.DBOperations;
import hibernate.Server;
import hibernate.TestJDBCConnection;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseWindowController implements Initializable {

    @FXML
    private BorderPane baseWindow;

    @FXML
    private Text databaseStatus;

    @FXML
    private StackPane dropdownStackPane;

    @FXML
    private ComboBox<String> serverDropdown;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Text updateStatus;

    @FXML
    private Button chooseServerButton;

    static Server chosenServer;
    private List<Server> serversList;

    private String databaseConfigurationFXMLFile = "DatabaseConfiguration.fxml";
    private String manageServersFXMLFile = "servers/ManageServers.fxml";
    private String adminsFXMLFile = "charts/Charts.fxml";

    @FXML
    protected void handleManageServerButton(ActionEvent event) {
        this.changeScene(manageServersFXMLFile, event);
    }

    @FXML
    protected void handleChooseServerButton(ActionEvent event) {
        if (serverDropdown.getSelectionModel().getSelectedItem() == null) {
            updateStatus.setText("Please choose a server first!");
            updateStatus.setFill(Color.RED);
        } else {
            int serverIndex = serverDropdown.getSelectionModel().getSelectedIndex();
            chosenServer = serversList.get(serverIndex);
            this.changeScene(adminsFXMLFile, event);
        }
    }

    @FXML
    protected void handleConfigureDatabaseButton(ActionEvent event) {
        this.changeScene(databaseConfigurationFXMLFile, event);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverDropdown != null : "fx:id=\"serverDropdown\" was not injected: check your FXML file 'BaseWindow.fxml'.";

        updateStatus.setId("textElement");
        databaseStatus.setId("textElement");

        serverDropdown.setDisable(true);
        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(addServersList.progressProperty());
        progressIndicator.setVisible(true);

        checkDBConnection.restart();
        addServersList.restart();

        addServersList.setOnSucceeded(e -> {
            serverDropdown.setDisable(false);
            progressIndicator.setVisible(false);
                });
    }

    private void changeScene(String windowFXMLFile, ActionEvent event) {
        Parent window = null;
        try {
            window = FXMLLoader.load(getClass().getResource(windowFXMLFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = null;
        if (window != null) {
            scene = new Scene(window);
        } else {
            System.out.println("Something went terribly wrong, window is null");
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private Service addServersList = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    serversList = DBOperations.displayServerRecords();

                    //Initialize server list for server dropdown

                    if (serversList.isEmpty()) {
                        updateStatus.setText("There are no servers added, add a new server");
                        updateStatus.setFill(Color.BLUE);
                    } else {
                        for (Server s : serversList)
                            serverDropdown.getItems().add(s.getServerName());
                    }
                    return null;
                }
            };
        }
    };

    private Service checkDBConnection = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    //Set database status
                    TestJDBCConnection testDB = new TestJDBCConnection();
                    String status = testDB.TestConnection();
                    if (status.equals("DB Status: Connected")) {
                        databaseStatus.setText(status);
                        databaseStatus.setFill(Color.GREEN);
                        chooseServerButton.setDisable(false);
                    } else {
                        databaseStatus.setText(status);
                        databaseStatus.setFill(Color.RED);
                        chooseServerButton.setDisable(true);
                    }
                    return null;
                }
            };
        }
    };
}