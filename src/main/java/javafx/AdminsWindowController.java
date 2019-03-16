package javafx;

import hibernate.Admin;
import hibernate.DBOperations;
import hibernate.Server;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminsWindowController implements Initializable {

    @FXML
    private GridPane adminsWindow;

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

    private Server chosenServer;
    private List<Admin> adminsRecordsList;
    public static Admin chosenAdmin;
    public static List<Admin> selectedAdminsList;

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
            Parent addAdminWindow = FXMLLoader.load(getClass().getResource("admins/AddAdmin.fxml"));
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
                int adminIndex = adminsList.getSelectionModel().getSelectedIndex();
                chosenAdmin = adminsRecordsList.get(adminIndex);
                Parent editAdminWindow = FXMLLoader.load(getClass().getResource("admins/EditAdmin.fxml"));
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
                    Parent chartWindow = FXMLLoader.load(getClass().getResource("charts/charts.fxml"));
                    adminsWindow.getChildren().setAll(chartWindow);
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
        adminsList.getColumns().addAll(columnAdminSelect);
        adminText.setText("Choose admins from the list: ");
        //Initialize server list for server dropdown

        chosenServer = BaseWindowController.chosenServer;
        adminsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        adminsRecordsList = DBOperations.displayAdminRecords(chosenServer.getServerName());

        if (adminsRecordsList.isEmpty()) {
            Error.setText("There are no admins added, add a new admin");
            Error.setFill(Color.BLUE);
        } else {
            for (Admin a : adminsRecordsList) {
                adminsList.getItems().add(a);
            }
        }
    }

}
