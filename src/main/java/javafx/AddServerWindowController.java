package javafx;

import hibernate.DBOperations;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddServerWindowController implements Initializable {

    @FXML
    private BorderPane addServerWindow;

    @FXML
    private TextField serverNameTextField;

    @FXML
    private TextField serverDynamicLinkTextField;

    @FXML
    private TextField serverStaticLinkTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addNewServerButton;

    @FXML
    private Text updateStatus;

    private String manageServersFXMLFile = "servers/ManageServers.fxml";
    @FXML
    protected void handleCancelButton(ActionEvent event) {
        this.changeScene(manageServersFXMLFile, event);
    }

    @FXML
    protected void handleAddNewServerButton(ActionEvent event) {
        String serverName = serverNameTextField.getText();
        String serverDynamicLink = serverDynamicLinkTextField.getText();

        if (serverDynamicLink.contains("hlstats.php")) {
            String[] fixDynamicLink = serverDynamicLink.split("/hlstats.php");
            serverDynamicLinkTextField.setText(fixDynamicLink[0]);
        }
        if (serverName.isEmpty() || serverDynamicLink.isEmpty()) {
            updateStatus.setText("Parameters can't be blank!");
            updateStatus.setFill(Color.RED);
        } else {
            try {
                DBOperations.createServerRecord(serverName, serverDynamicLink+serverStaticLinkTextField.getText());
                updateStatus.setText("Server " + serverName + " was succesfully created!");
                updateStatus.setFill(Color.GREEN);
            } catch (Exception e) {
                e.printStackTrace();
                String errorMessage = ExceptionUtils.getRootCause(e).toString();
                if (errorMessage.contains("exists")) {
                    String[] em1 = errorMessage.split("Detail:");
                    updateStatus.setText("Something went terribly wrong! " + em1[1]);
                    updateStatus.setFill(Color.RED);
                } else {
                    updateStatus.setText("Something went terribly wrong! " + errorMessage);
                    updateStatus.setFill(Color.RED);
                }
            }
        }
    }

    @FXML
    protected void handleCheckLink(ActionEvent e) {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        HostServices hostServices = (HostServices)stage.getProperties().get("hostServices");
        hostServices.showDocument(serverDynamicLinkTextField.getText());
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'AddServer.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddServer.fxml'.";
        assert addNewServerButton != null : "fx:id=\"addNewServerButton\" was not injected: check your FXML file 'AddServer.fxml'.";

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