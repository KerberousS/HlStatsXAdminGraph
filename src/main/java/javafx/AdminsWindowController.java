package javafx;

import hibernate.Admin;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminsWindowController implements Initializable {

    @FXML
    private BorderPane adminsWindow;

    @FXML
    private Text adminText;

    @FXML
    private Text Error;

    @FXML
    private TableView<Admin> adminsList;
    @FXML
    private TableColumn columnAdminID;
    @FXML
    private TableColumn columnAdminName;
    @FXML
    private TableColumn columnAdminLink;
    @FXML
    private TableColumn columnAdminColor;
    @FXML
    private TableColumn columnAdminSelect;

    @FXML
    private StackPane listStackPane;

    private ProgressIndicator progressIndicator;

    private Server chosenServer;
    private List<Admin> adminsRecordsList;
    public static Admin chosenAdmin;
    public static List<Admin> selectedAdminsList;

    //FXML FILES
    private String baseWindowFXMLFile = "BaseWindow.fxml";
    private String addAdminFXMLFile = "admins/AddAdmin.fxml";
    private String editAdminFXMLFile = "admins/EditAdmin.fxml";
    private String chartsFXMLFile = "charts/charts.fxml";

    @FXML
    protected void handleCloseButton(ActionEvent event) {
        this.changeScene(baseWindowFXMLFile, event);
    }

    @FXML
    protected void handleAddNewAdminButton(ActionEvent event) {
        this.changeScene(addAdminFXMLFile, event);
    }

    @FXML
    protected void handleEditAdminButton(ActionEvent event) {
            if (adminsList.getSelectionModel().getSelectedItem() == null) {
                Error.setText("Please choose an admin first!");
                Error.setFill(Color.RED);
            }
            else {
                int adminIndex = adminsList.getSelectionModel().getSelectedIndex();
                chosenAdmin = adminsRecordsList.get(adminIndex);

                this.changeScene(editAdminFXMLFile, event);
            }
    }

    @FXML
    protected void handleDeleteAdminButton(ActionEvent event) {
        if (adminsList.getSelectionModel().getSelectedItem() == null) {
            Error.setText("Please choose an Admin first!");
        } else {
            int adminIndex = adminsList.getSelectionModel().getSelectedIndex();
            chosenAdmin = adminsRecordsList.get(adminIndex);

            //TODO: CONFIRM WINDOW

            DBOperations.deleteAdminRecord(chosenAdmin.getAdminID());

            //Initialize admins list
            adminsList.getItems().clear();
            adminsList.getItems().addAll(DBOperations.displayAdminRecords(chosenServer.getServerName()));
        }
    }

    @FXML
    protected void handleChartsButton(ActionEvent event) {
        List<Admin> adms = adminsList.getItems();
        selectedAdminsList = new ArrayList<>();
        try {
            if (adminsList.getItems().isEmpty()) {
                Error.setText("Please add admins first!");
                Error.setFill(Color.RED);
            } else {
                for (Admin a : adms) {
                    if (a.isSelected()) {
                        selectedAdminsList.add(a);
                    }
                }
                if (!selectedAdminsList.isEmpty()) {
                    this.changeScene(chartsFXMLFile, event);
                } else {
                    Error.setText("Please select admins first!");
                    Error.setFill(Color.RED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert adminsList != null : "fx:id=\"adminsList\" was not injected: check your FXML file 'admins.fxml'.";
        //TODO: GET EVERYTHING INTO NEW THREADS SO APP WONT FREEZE

        columnAdminID.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminID"));
        columnAdminName.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminName"));
        columnAdminLink.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminLink"));
        columnAdminColor.setCellValueFactory(new PropertyValueFactory<Admin, String>("adminColor"));
        columnAdminSelect.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Admin, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {
            Admin admin = arg0.getValue();
            admin.setSelected(false);
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(admin.isSelected());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> admin.setSelected(new_val));
            return new SimpleObjectProperty<>(checkBox);
        });
        //Initialize server list for server dropdown

        chosenServer = BaseWindowController.chosenServer;
        adminsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Set up progress bars
        progressIndicator = new ProgressIndicator(0);
        progressIndicator.progressProperty().bind(getAdmins.progressProperty());
        progressIndicator.setVisible(true);
        listStackPane.getChildren().add(progressIndicator);
        populateAdminsList();
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

    private void populateAdminsList() {
        getAdmins.restart();

        //On fail
        getAdmins.setOnFailed(e-> {
            Error.setText("Something went terribly wrong, give this piece of code to person responsible for this: " + getAdmins.getException().toString());
            progressIndicator.setVisible(false);
                });

        //On succeed
        getAdmins.setOnSucceeded(e-> {
            if (adminsRecordsList.isEmpty()) {
                adminsList.setPlaceholder(new Label( "There are no admins added, add a new admin"));
                progressIndicator.setVisible(false);
            } else {
                for (Admin a : adminsRecordsList) {
                    adminsList.getItems().add(a);
                    progressIndicator.setVisible(false);
                }
            }
        });
    }

    Service getAdmins = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    adminsRecordsList = DBOperations.displayAdminRecords(chosenServer.getServerName());
                    return null;
                }
            };
        }
    };
}
