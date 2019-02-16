package javaFX;

import hibernate.AdminOperations;
import hibernate.ServerOperations;
import hibernate.TestJDBCConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private ListView adminsList;

    @FXML
    private ComboBox<Object> serverDropdown;

    @FXML
    private HBox chooseServerButton;

    public static String choosenServer;

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
    protected void handleManageAdminsButton(ActionEvent event) {
        try {
            Parent manageAdminsWindow = FXMLLoader.load(getClass().getResource("ManageAdmins.fxml"));
            adminsWindow.getChildren().setAll(manageAdminsWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleGenerateSimpleSumChartButton(ActionEvent event) {
        //@TODO: Generate charts and show in preview window
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
       assert adminsList != null : "fx:id=\"adminsList\" was not injected: check your FXML file 'Admins.fxml'.";

         //Initialize server list for server dropdown
        List admins = AdminOperations.displayRecords(BaseWindowController.choosenServer);

        serverDropdown.setItems(FXCollections.observableArrayList(admins.toArray()));
        System.out.println(admins);
    }
}