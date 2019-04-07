package javafx;

import hibernate.DBOperations;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddServerWindowController implements Initializable {

    @FXML
    private BorderPane addServerWindow;

    @FXML
    private TextField serverNameTextField;

    @FXML
    private TextField serverLinkTextField;

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
        String serverLink = serverLinkTextField.getText();
        System.out.println(serverName);

        if (serverName.isEmpty() || serverLink.isEmpty()) {
            updateStatus.setText("Parameters can't be blank!");
            updateStatus.setFill(Color.RED);
        } else {
            try {
                DBOperations.createServerRecord(serverName, serverLink);
                updateStatus.setText("Server " + serverName + " was succesfully created!");
                updateStatus.setFill(Color.GREEN);
            } catch (Exception e) {
                e.printStackTrace();
                updateStatus.setText("Something went terribly wrong!");
                updateStatus.setFill(Color.RED);
            }
        }
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