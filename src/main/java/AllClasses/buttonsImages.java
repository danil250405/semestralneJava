package AllClasses;

import com.example.semestralnejava.Controller;
import com.example.semestralnejava.WindowManager;
import javafx.scene.control.Label;

public class buttonsImages extends Controller {
    public static void buttonHomePressed(Label label){
        System.out.println("Image Clicked!");
        if (authorizedUser.getUsername().equals("admin"))
        WindowManager.showWindow("app.fxml", label);
        else WindowManager.showWindow("appForUser.fxml", label);
    }
    public static void buttonReturnToLogInPressed(Label imageView){
        WindowManager.showWindow("hello-view.fxml", imageView);
    }
}
