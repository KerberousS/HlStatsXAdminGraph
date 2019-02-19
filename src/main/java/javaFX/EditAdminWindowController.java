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

public class EditAdminWindowController implements Initializable {

    @FXML
    private GridPane editAdminWindow;

    @FXML
    private TextField adminNameTextField;

    @FXML
    private TextField adminLinkTextField;

    @FXML
    private ColorPicker adminColorPicker;

    @FXML
    private HBox cancelButton;

    @FXML
    private HBox editAdminButton;

    @FXML
    private Text updateStatus;

    public String chosenServerName = BaseWindowController.choosenServer;
    String initialAdminName = AdminsWindowController.chosenAdminName;
    String initialAdminHLID = AdminsWindowController.choosenAdminLink;
    String initialAdminColor = AdminsWindowController.choosenAdminColor;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        try {
            Parent manageServerWindow = FXMLLoader.load(getClass().getResource("Admins/Admins.fxml"));
            editAdminWindow.getChildren().setAll(manageServerWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
        String newAdminName = adminNameTextField.getText();
        String newAdminLink = adminLinkTextField.getText();
        Color c = adminColorPicker.getValue();
        String newAdminColor = colorOperations.colorToHex(c);

        AdminOperations adminOperations = new AdminOperations();
        //TODO: Add functional admin edit
        try {
            if (newAdminName.isEmpty() & newAdminLink.isEmpty() & newAdminColor.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            }
            else if (newAdminName.equals(initialAdminName) && newAdminLink.equals(initialAdminHLID) && newAdminColor.equals(initialAdminColor))
            {
                updateStatus.setText("You haven't changed any parameters!");
                updateStatus.setFill(Color.RED);
            } else if (!newAdminName.equals(initialAdminName)) {
                adminOperations.updateAdminNameRecordByName(initialAdminName, newAdminName, chosenServerName);
                updateStatus.setText("Admin updated");
                updateStatus.setFill(Color.GREEN);
            }
            if (!newAdminLink.equals(initialAdminHLID)) {
                adminOperations.updateAdminLinkRecordByName(initialAdminName, newAdminLink, chosenServerName);
                updateStatus.setText("Admin updated");
                updateStatus.setFill(Color.GREEN);
                System.out.println(newAdminLink);
                System.out.println(initialAdminHLID);
            }
            if (!newAdminColor.equals(initialAdminColor)) {
                adminOperations.updateAdminColorRecordByName(initialAdminName, newAdminColor, chosenServerName);
                updateStatus.setText("Admin updated");
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
        assert adminNameTextField != null : "fx:id=\"adminNameTextField\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert adminLinkTextField != null : "fx:id=\"adminLinkTextField\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert adminColorPicker != null : "fx:id=\"adminColorPicker\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert editAdminButton != null : "fx:id=\"editAdminButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        
        adminNameTextField.setText(AdminsWindowController.chosenAdminName);
        adminLinkTextField.setText(AdminsWindowController.choosenAdminLink);
        adminColorPicker.setValue(Color.valueOf("#" + AdminsWindowController.choosenAdminColor));
    }
}