package javaFX;

import hibernate.AdminOperations;
import hibernate.ServerOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAdminWindowController implements Initializable {

    @FXML
    private GridPane addAdminWindow;

    @FXML
    private TextField adminNameTextField;

    @FXML
    private TextField adminLinkTextField;

    @FXML
    private ColorPicker adminColorPicker;

    @FXML
    private HBox cancelButton;

    @FXML
    private HBox addNewAdminButton;

    public String chosenServerName = BaseWindowController.choosenServer;
    public String chosenAdminName = ManageAdminWindowController.chosenAdminName;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("ManageServers.fxml"));
            addAdminWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
        String adminName = adminNameTextField.getText();
        String adminLink = adminLinkTextField.getText();
        String adminColor = adminColorPicker.toString();
        System.out.println(adminColor);

        AdminOperations adminOperations = new AdminOperations();

        try {
            if (adminName.isEmpty() & adminLink.isEmpty() & adminColor.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            }
            else if (chosenAdminName.equals(initialURL) && adminLink.equals(initialUsername) && adminColorPicker.equals(initialPassword))
            {
                updateStatus.setText("You haven't changed any parameters!");
                updateStatus.setFill(Color.RED);
            } else if (!databaseURL.equals(initialURL)) {
                config.setConfigURL(databaseURL);
                databaseUrlTextField.setText(config.getDatabaseUrl());
                updateStatus.setText("Config updated");
                updateStatus.setFill(Color.GREEN);
            }
            if (!databaseUsername.equals(initialUsername)) {
                adminOperations.updateAdminLinkRecordByName(adminName, adminLink, adminColor, chosenServerName);
                usernameTextField.setText(config.getDatabaseUsername());
                updateStatus.setText("Config updated");
                updateStatus.setFill(Color.GREEN);
            }
            if (!databasePassword.equals(initialPassword)) {
                config.setConfigPassword(databasePassword);
                passwordTextField.setText(config.getDatabasePassword());
                updateStatus.setText("Config updated");
                updateStatus.setFill(Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateStatus.setText("Something went terribly wrong!");
            updateStatus.setFill(Color.RED);
        }
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminNameTextField != null : "fx:id=\"adminNameTextField\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert adminLinkTextField != null : "fx:id=\"adminLinkTextField\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert adminColorPicker != null : "fx:id=\"adminColorPicker\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert addNewAdminButton != null : "fx:id=\"addNewAdminButton\" was not injected: check your FXML file 'AddAdmin.fxml'.";
    }
}