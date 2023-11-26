package AllClasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {
    public static void showWindow(String window, Node node) {
        node.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowManager.class.getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
