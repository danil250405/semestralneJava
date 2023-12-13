package com.example.semestralnejava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file to create the GUI layout
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        // Set the stage title
        primaryStage.setTitle("Library");

        // Set the scene with the loaded layout and specify dimensions
        primaryStage.setScene(new Scene(root, 700, 400));

        // Show the stage
        primaryStage.show();
    }

    // Entry point for the JavaFX application
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
