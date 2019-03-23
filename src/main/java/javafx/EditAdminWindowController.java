package javafx;

import getinfo.colorOperations;
import hibernate.Admin;
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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAdminWindowController implements Initializable {

    @FXML
    private BorderPane editAdminWindow;

    @FXML
    private TextField adminNameTextField;

    @FXML
    private TextField adminLinkTextField;

    @FXML
    private ColorPicker adminColorPicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editAdminButton;

    @FXML
    private Text updateStatus;

    private Server chosenServer;
    private Admin chosenAdmin;

    private String adminsFXMLFile = "admins/admins.fxml";

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        this.changeScene(adminsFXMLFile, event);
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
        String newAdminName = adminNameTextField.getText();
        String newAdminLink = adminLinkTextField.getText();
        Color c = adminColorPicker.getValue();
        String newAdminColor = colorOperations.colorToHex(c);

        //TODO: Add functional admin edit
        try {
            if (newAdminName.isEmpty() || newAdminLink.isEmpty() || newAdminColor.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            }
            else if (newAdminName.equals(chosenAdmin.getAdminName()) && newAdminLink.equals(chosenAdmin.getAdminLink()) && newAdminColor.equals(chosenAdmin.getAdminColor()))
            {
                updateStatus.setText("You haven't changed any parameters!");
                updateStatus.setFill(Color.RED);
            } else {
                DBOperations.updateAdminRecord(chosenAdmin.getAdminID(), newAdminName, newAdminColor, newAdminLink, chosenServer.getServerName());
                updateStatus.setText("Admin updated");
                updateStatus.setFill(Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateStatus.setText("Something went terribly wrong!");
            updateStatus.setFill(Color.RED);
        }
    }

    //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminNameTextField != null : "fx:id=\"adminNameTextField\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert adminLinkTextField != null : "fx:id=\"adminLinkTextField\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert adminColorPicker != null : "fx:id=\"adminColorPicker\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert editAdminButton != null : "fx:id=\"editAdminButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";

        chosenServer = BaseWindowController.chosenServer;
        chosenAdmin = AdminsWindowController.chosenAdmin;
        adminNameTextField.setText(chosenAdmin.getAdminName());
        adminLinkTextField.setText(chosenAdmin.getAdminLink());
        adminColorPicker.setValue(Color.valueOf("#" + chosenAdmin.getAdminColor()));
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