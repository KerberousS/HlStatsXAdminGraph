package javaFX;

import getInfo.colorOperations;
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAdminWindowController implements Initializable {

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

    @FXML
    private Text updateStatus;

    public String chosenServerName = BaseWindowController.choosenServer;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("Admins/Admins.fxml"));
            addAdminWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleAddNewAdminButton(ActionEvent event) {
        String adminName = adminNameTextField.getText();
        String adminLink = adminLinkTextField.getText();
        Color c = adminColorPicker.getValue();
        String adminColor = colorOperations.colorToHex(c);


        System.out.println(adminColor);

        try {
            if (adminName.isEmpty() & adminLink.isEmpty() & adminLink.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            } else {
                AdminOperations adminOperations = new AdminOperations();
                adminOperations.createAdminRecord(adminName, adminLink, adminColor, chosenServerName);

                updateStatus.setText("Admin " + adminName + " was succesfully created!");
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