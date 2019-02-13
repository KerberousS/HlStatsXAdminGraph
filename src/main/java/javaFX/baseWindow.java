package javaFX;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class baseWindow extends Application {
    public static void main(String[] args) {
        Application.launch(baseWindow.class, args);
    }

        @Override
        public void start(Stage primaryStage) {
            //Base settings
            try {
                Parent grid = FXMLLoader.load(getClass().getResource("baseWindow.fxml"));

                //Initialize
                Scene scene = new Scene(grid, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setTitle("HlStatsX Admin Graph");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }