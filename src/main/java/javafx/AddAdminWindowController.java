package javafx;

import getinfo.colorOperations;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAdminWindowController implements Initializable {

    @FXML
    private BorderPane addAdminWindow;

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
    private Button addNewAdminButton;

    @FXML
    private Text updateStatus;

    private Server chosenServer;

    private String chartsFXMLFile = "charts/Charts.fxml";

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        this.changeScene(chartsFXMLFile, event);
    }

    @FXML
    protected void handleAddNewAdminButton(ActionEvent event) {
        String adminName = adminNameTextField.getText();
        String adminDynamicLink = adminDynamicLinkTextField.getText();
        Color c = adminColorPicker.getValue();
        String adminColor = colorOperations.colorToHex(c);

        try {
            if (adminName.isEmpty() || adminDynamicLink.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            } else {
                DBOperations.createAdminRecord(adminName, (adminStaticLinkTextField.getText()+adminDynamicLink), adminColor, chosenServer.getServerName());

                updateStatus.setText("Admin " + adminName + " was succesfully created!");
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
    protected void handleCheckLink(ActionEvent e) {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        HostServices hostServices = (HostServices)stage.getProperties().get("hostServices");
        hostServices.showDocument(adminStaticLinkTextField.getText()+adminDynamicLinkTextField.getText());
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminNameTextField != null : "fx:id=\"adminNameTextField\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert adminColorPicker != null : "fx:id=\"adminColorPicker\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddAdmin.fxml'.";
        assert addNewAdminButton != null : "fx:id=\"addNewAdminButton\" was not injected: check your FXML file 'AddAdmin.fxml'.";

        adminDynamicLinkTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            //Allow only numbers in dynamic link text
            adminDynamicLinkTextField.setText(adminDynamicLinkTextField.getText().replaceAll("\\D+",""));
        });

        chosenServer = BaseWindowController.chosenServer;
        adminStaticLinkTextField.setText(chosenServer.getServerHlstatsLink());
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