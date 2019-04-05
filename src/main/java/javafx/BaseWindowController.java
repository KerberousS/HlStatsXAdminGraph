package javafx;

import hibernate.DBOperations;
import hibernate.Server;
import hibernate.TestJDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
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
    private BorderPane baseWindow;

    @FXML
    private Text databaseStatus;

    @FXML
    private ComboBox<String> serverDropdown;

    @FXML
    private Text updateStatus;

    @FXML
    private Button chooseServerButton;

    public static Server chosenServer;
    private List<Server> serversList;

    private String databaseConfigurationFXMLFile = "DatabaseConfiguration.fxml";
    private String manageServersFXMLFile = "servers/ManageServers.fxml";
    private String adminsFXMLFile = "admins/Admins.fxml";

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
    //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE

    @FXML
    protected void handleConfigureDatabaseButton(ActionEvent event) {
            this.changeScene(databaseConfigurationFXMLFile, event);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverDropdown != null : "fx:id=\"serverDropdown\" was not injected: check your FXML file 'BaseWindow.fxml'.";

        serversList = DBOperations.displayServerRecords();

        //Set database status
        TestJDBCConnection testDB = new TestJDBCConnection();
        String status = testDB.TestConnection();
        if (status == "Database connection Established") {
            databaseStatus.setText(status);
            databaseStatus.setFill(Color.GREEN);
            chooseServerButton.setDisable(false);
        } else {
            databaseStatus.setText(status);
            databaseStatus.setFill(Color.RED);
            chooseServerButton.setDisable(true);
        }
            //Initialize server list for server dropdown


        if (serversList.isEmpty()) {
            updateStatus.setText("There are no servers added, add a new server");
            updateStatus.setFill(Color.BLUE);
        } else {
            for (Server s : serversList)
            serverDropdown.getItems().add(s.getServerName());
        }
    }

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