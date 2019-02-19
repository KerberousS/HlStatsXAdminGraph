package javaFX;

import hibernate.Admin;
import hibernate.AdminOperations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminsWindowController implements Initializable {

    @FXML
    private GridPane adminsWindow;

    @FXML
    private Text adminText;

    @FXML
    private TableView<Admin> adminsList;

    @FXML
    private Text Error;
    
    @FXML
    private TableColumn columnAdminName;

    @FXML
    private TableColumn columnAdminLink;

    @FXML
    private TableColumn columnAdminColor;

    public static String choosenServer;
    public static String chosenAdminName;
    public static String choosenAdminLink;
    public static String choosenAdminColor;

//    @FXML
//    protected void handleSumTimeButton(ActionEvent event) {
//        String adminID = adminIDTextField.getText();
//
//        SummarizeTime st = new SummarizeTime();
//        String sumTime = st.sumTimeString(adminID);
//        actiontarget.setFill(Color.FIREBRICK);
//        actiontarget.setText(sumTime);
//    }

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        try {
            Parent baseWindow = FXMLLoader.load(getClass().getResource("BaseWindow.fxml"));
            adminsWindow.getChildren().setAll(baseWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleAddNewAdminButton(ActionEvent event) {
        try {
            Parent addAdminWindow = FXMLLoader.load(getClass().getResource("Admins/AddAdmin.fxml"));
            adminsWindow.getChildren().setAll(addAdminWindow);
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
                Admin admin = adminsList.getSelectionModel().getSelectedItem();
                chosenAdminName = admin.getAdminName();
                choosenAdminLink = admin.getAdminLink();
                choosenAdminColor = admin.getAdminColor();
                Parent editAdminWindow = FXMLLoader.load(getClass().getResource("Admins/EditAdmin.fxml"));
                adminsWindow.getChildren().setAll(editAdminWindow);
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
            Admin admin = adminsList.getSelectionModel().getSelectedItem();
            chosenAdminName = admin.getAdminName();
            //TODO: CONFIRM WINDOW
            AdminOperations adminOperations = new AdminOperations();
            adminOperations.deleteRecordByName(chosenAdminName, choosenServer);

            //Initialize admins list
            adminsList.getItems().clear();
            adminsList.getItems().addAll(AdminOperations.displayFullRecords(choosenServer));
        }
    }

    @FXML
    protected void handleChartsButton(ActionEvent event) {
        try {
            if (adminsList.getItems().isEmpty()) {
                Error.setText("Please add admins first!");
                Error.setFill(Color.RED);
            } else {
                Parent chartWindow = FXMLLoader.load(getClass().getResource("Charts/Charts.fxml"));
                adminsWindow.getChildren().setAll(chartWindow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminsList != null : "fx:id=\"adminsList\" was not injected: check your FXML file 'Admins.fxml'.";

        columnAdminName.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminName"));
        columnAdminLink.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminLink"));
        columnAdminColor.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminColor"));

        adminText.setText("Choose admins from the list: ");
        choosenServer = BaseWindowController.choosenServer;
        //Initialize server list for server dropdown

        adminsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        adminsList.getItems().addAll(AdminOperations.displayFullRecords(choosenServer));
    }
}
