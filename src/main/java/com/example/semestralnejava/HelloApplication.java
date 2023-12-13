package com.example.semestralnejava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Library");
        primaryStage.setScene(new Scene(root, 700, 400));
        Image anotherIcon = new Image("file:pngegg.png");
        primaryStage.getIcons().add(anotherIcon);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        int finalI = i;
        executorService.submit(() -> launchApplication(args, finalI));
        i++;
        while (true) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine().trim();


            if (command.equals("open") || i == 0) {
                int finalI1 = i;
                executorService.submit(() -> launchApplication(args, finalI1));
                i++;
            } else if (command.equals("close")) {
                // Закрываем все окна и завершаем работу приложения
                executorService.shutdown();
                Platform.exit();
                System.exit(0);
            }

        }
    }

    private static void launchApplication(String[] args, int instanceNumber) {
        Platform.runLater(() -> {
            try {
                HelloApplication app = new HelloApplication();
                Stage stage = new Stage();
                app.start(stage);
                stage.setTitle("Library - Instance " + instanceNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
