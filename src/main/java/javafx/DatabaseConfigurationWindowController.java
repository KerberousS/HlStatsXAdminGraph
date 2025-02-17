package javafx;

import hibernate.ConfigOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseConfigurationWindowController implements Initializable {

    @FXML
    private GridPane databaseConfigWindow;

    @FXML
    private TextField databaseUrlTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateConfiguration;

    @FXML
    private Text updateStatus;

    private String initialURL;
    private String initialUsername;
    private String initialPassword;

    private String baseWindowFXMLFile = "BaseWindow.fxml";

    @FXML
    protected void handleCancelButton(ActionEvent event) {
        this.changeScene(baseWindowFXMLFile, event);
    }

    @FXML
    protected void handleUpdateConfigButton(ActionEvent event) {
        String databaseURL = databaseUrlTextField.getText();
        String databaseUsername = usernameTextField.getText();
        String databasePassword = passwordTextField.getText();

        ConfigOperations config = new ConfigOperations();
        try {
            if (databaseURL.isEmpty() || databaseUsername.isEmpty() || databasePassword.isEmpty()) {
                updateStatus.setText("Parameters can't be blank!");
                updateStatus.setFill(Color.RED);
            }
            else if (databaseURL.equals(initialURL) && databaseUsername.equals(initialUsername) && databasePassword.equals(initialPassword))
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
                config.setConfigUsername(databaseUsername);
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

        ConfigOperations config = new ConfigOperations();
        databaseUrlTextField.setText(config.getDatabaseUrl());
        usernameTextField.setText(config.getDatabaseUsername());
        passwordTextField.setText(config.getDatabasePassword());

        initialURL = databaseUrlTextField.getText();
        initialUsername = usernameTextField.getText();
        initialPassword = passwordTextField.getText();
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