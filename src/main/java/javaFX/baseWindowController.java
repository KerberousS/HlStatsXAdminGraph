package javaFX;

import getInfo.SummarizeTime;
import hibernate.Server;
import hibernate.ServerOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class baseWindowController implements Initializable {


    @FXML
    private Text actiontarget;

    @FXML
    private TextField adminIDTextField;

    @FXML
    private ChoiceBox<Object> serverDropdown;

    @FXML
    protected void handleSumTimeButton(ActionEvent event) {
        String adminID = adminIDTextField.getText();

        SummarizeTime st = new SummarizeTime();
        String sumTime = st.sumTimeString(adminID);
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText(sumTime);
    }

    @FXML
    protected void handleAddServerButton(ActionEvent event) {
        try {
            Parent grid = FXMLLoader.load(getClass().getResource("addserverwindow.fxml"));

            //Initialize
            Scene scene = new Scene(grid, 600, 400);
            Stage newserverstage = new Stage();
            newserverstage.setScene(scene);
            newserverstage.show();
            newserverstage.setTitle("Add a new server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert actiontarget != null : "fx:id=\"actiontarget\" was not injected: check your FXML file 'basewindow.fxml'.";
        assert serverDropdown != null : "fx:id=\"serverDropdown\" was not injected: check your FXML file 'basewindow.fxml'.";

        //Initialize server list for server dropdown
        List ListviewServers = ServerOperations.displayRecords();
        serverDropdown.setItems(FXCollections.observableArrayList(ListviewServers.toArray()));
    }
}