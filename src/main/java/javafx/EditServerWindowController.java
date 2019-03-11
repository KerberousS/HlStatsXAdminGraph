package javafx;

import hibernate.DBOperations;
import hibernate.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditServerWindowController implements Initializable {

    @FXML
    private GridPane editServerWindow;

    @FXML
    private TextField serverNameTextField;

    @FXML
    private TextField serverURLTextField;

    @FXML
    private HBox cancelbutton;

    @FXML
    private HBox addnewserverbutton;

    @FXML
    private Text updateStatus;

    private Server chosenServer;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("servers/ManageServers.fxml"));
            editServerWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } catch (Exception e) { //TODO: CHECK IF THIS EVEN WORKS CAUSE I THINK ITS BROKEN, THE SAME THING HAPPENS IN NEW SERVER WINDOW
            e.printStackTrace();
            updateStatus.setText("Something went terribly wrong!");
            updateStatus.setFill(Color.RED);
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert serverNameTextField != null : "fx:id=\"serverNameTextField\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert addnewserverbutton != null : "fx:id=\"addnewserverbutton\" was not injected: check your FXML file 'basewindow.fxml'.";
        //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE

        chosenServer = ManageServerWindowController.chosenServer;
        serverNameTextField.setText(chosenServer.getServerName());
        serverURLTextField.setText(chosenServer.getServerHlstatsLink());
    }
}