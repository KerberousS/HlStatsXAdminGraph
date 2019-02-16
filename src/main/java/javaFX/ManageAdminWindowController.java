package javaFX;

import hibernate.AdminOperations;
import hibernate.AdminOperations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageAdminWindowController implements Initializable {

    @FXML
    private GridPane manageAdminWindow;

    @FXML
    private ListView adminsList;

    @FXML
    private HBox closeButton;

    @FXML
    private Text Error;

    public String chosenServer = BaseWindowController.choosenServer;
    public static String chosenAdminName;

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        try {
            Parent adminsWindow = FXMLLoader.load(getClass().getResource("Admins.fxml"));
            manageAdminWindow.getChildren().setAll(adminsWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void handleAddNewAdminButton(ActionEvent event) {
        try {
            Parent addAdminWindow = FXMLLoader.load(getClass().getResource("AddAdmin.fxml"));
            manageAdminWindow.getChildren().setAll(addAdminWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
        try {
            if (adminsList.getSelectionModel().getSelectedItem() == null) {
                Error.setText("Please choose an admin first!");
                Error.setFill(Color.RED);
            }
            else {
                chosenAdminName = adminsList.getSelectionModel().getSelectedItem().toString();
                Parent editAdminWindow = FXMLLoader.load(getClass().getResource("EditAdmin.fxml"));
                manageAdminWindow.getChildren().setAll(editAdminWindow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDeleteAdminButton(ActionEvent event) {
        if (adminsList.getSelectionModel().getSelectedItem() == null) {
            Error.setText("Please choose an Admin first!");
        } else {
            chosenAdminName = adminsList.getSelectionModel().getSelectedItem().toString();
            //TODO: CONFIRM WINDOW
            AdminOperations adminOperations = new AdminOperations();
            adminOperations.deleteRecordByName(chosenAdminName, chosenServer);
            
            //Initialize admins list
            List ListviewAdmins = AdminOperations.displayRecords(chosenServer);
            adminsList.setItems(FXCollections.observableArrayList(ListviewAdmins.toArray()));
        }
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminsList != null : "fx:id=\"adminsList\" was not injected: check your FXML file 'ManageAdmins.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'ManageAdmins.fxml'.";

        //Initialize admins list
        List ListviewAdmins = AdminOperations.displayRecords(chosenServer);
        adminsList.setItems(FXCollections.observableArrayList(ListviewAdmins.toArray()));
    }
}