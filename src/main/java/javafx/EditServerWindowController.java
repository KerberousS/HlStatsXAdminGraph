package javafx;

import hibernate.DBOperations;
import hibernate.Server;
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

public class EditServerWindowController implements Initializable {

    @FXML
    private BorderPane editServerWindow;

    @FXML
    private TextField serverNameTextField;

    @FXML
    private TextField serverURLTextField;

    @FXML
    private Button cancelbutton;

    @FXML
    private Button addnewserverbutton;

    @FXML
    private Text updateStatus;

    private Server chosenServer;

    private String manageServersFXMLFile = "servers/ManageServers.fxml";

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        this.changeScene(manageServersFXMLFile, event);
    }

    @FXML
    protected void handleEditServerButton(ActionEvent event) {
        String newServerName = serverNameTextField.getText();
        String newServerURL = serverURLTextField.getText();
        try {
            if (newServerName.isEmpty() || newServerURL.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            }
            else if (chosenServer.getServerName().equals(newServerName) && chosenServer.getServerHlstatsLink().equals(newServerURL))
            {
                updateStatus.setText("You haven't changed any parameters!");
                updateStatus.setFill(Color.RED);
            } else {
                DBOperations.updateServerRecord(chosenServer.getServerID(), newServerName, newServerURL);
                updateStatus.setText("Admin has been updated!");
                updateStatus.setFill(Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = ExceptionUtils.getRootCause(e).toString();
            if (errorMessage.contains("exists")){
                String[] em1 = errorMessage.split("Detail:");
                updateStatus.setText("Something went terribly wrong! " + em1[1]);
                updateStatus.setFill(Color.RED);
            } else {
                updateStatus.setText("Something went terribly wrong! " + errorMessage);
                updateStatus.setFill(Color.RED);
            }
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addnewserverbutton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";

        chosenServer = ManageServerWindowController.chosenServer;
        serverNameTextField.setText(chosenServer.getServerName());
        serverURLTextField.setText(chosenServer.getServerHlstatsLink());
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