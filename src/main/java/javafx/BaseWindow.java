package javafx;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class BaseWindow extends Application {

    public static void main(String[] args) {
        Application.launch(BaseWindow.class, args);
    }

        @Override
        public void start(Stage stage) {
            //Base settings
            try {
                Parent baseWindow = FXMLLoader.load(getClass().getResource("BaseWindow.fxml"));

                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                //set Stage boundaries to visible bounds of the main screen
                stage.setX(primaryScreenBounds.getMinX());
                stage.setY(primaryScreenBounds.getMinY());
                stage.setWidth(primaryScreenBounds.getWidth());
                stage.setHeight(primaryScreenBounds.getHeight());
                stage.setMaximized(true);

                //Initialize
                Scene scene = new Scene(baseWindow);
                stage.setScene(scene);
                stage.show();
                stage.setTitle("HlStatsX Admin Graph");

                //Add host services
                stage.getProperties().put("hostServices", this.getHostServices());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }