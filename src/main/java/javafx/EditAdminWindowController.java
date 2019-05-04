package javafx;

import hibernate.Admin;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAdminWindowController implements Initializable {

    @FXML
    private BorderPane editAdminWindow;

    @FXML
    private TextField adminNameTextField;

    @FXML
    private TextField adminStaticLinkTextField;

    @FXML
    private TextField adminDynamicLinkTextField;

    @FXML
    private ColorPicker adminColorPicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editAdminButton;

    @FXML
    private Text updateStatus;

    private Server chosenServer;
    private Admin chosenAdmin = ChartsWindowController.chosenAdmin;

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        ((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
        String newAdminName = adminNameTextField.getText();
        String newAdminLink = adminStaticLinkTextField.getText()+adminDynamicLinkTextField.getText();
        Color c = adminColorPicker.getValue();
        String newAdminColor = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());

        try {
            if (newAdminName.isEmpty() || adminDynamicLinkTextField.getText().isEmpty()) {
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

    @FXML
    protected void handleCheckLink() {
        BaseWindow.hostServices.showDocument(adminStaticLinkTextField.getText()+adminDynamicLinkTextField.getText());
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminNameTextField != null : "fx:id=\"adminNameTextField\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert adminColorPicker != null : "fx:id=\"adminColorPicker\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        assert editAdminButton != null : "fx:id=\"editAdminButton\" was not injected: check your FXML file 'EditAdmin.fxml'.";
        chosenServer = BaseWindowController.chosenServer;

        adminNameTextField.setText(chosenAdmin.getAdminName());

        //Fix no css on text
        updateStatus.setId("textElement");
        adminDynamicLinkTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            //Allow only numbers in dynamic link text
            adminDynamicLinkTextField.setText(adminDynamicLinkTextField.getText().replaceAll("\\D+",""));
        });

        String[] adminSplitLink = chosenAdmin.getAdminLink().split("player=");
        adminStaticLinkTextField.setText(adminSplitLink[0]+"player=");
        adminDynamicLinkTextField.setText(adminSplitLink[1]);
        adminColorPicker.setValue(Color.valueOf("#" + chosenAdmin.getAdminColor()));
    }
}