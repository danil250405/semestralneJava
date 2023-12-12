package AllClasses;

import com.example.semestralnejava.Controller;
import com.example.semestralnejava.WindowManager;
import javafx.scene.image.ImageView;

public class buttonsImages extends Controller {
    public static void buttonHomePressed(ImageView imageBtn){
        System.out.println("Image Clicked!");
        if (authorizedUser.getUsername().equals("admin"))
        WindowManager.showWindow("app.fxml", imageBtn);
        else WindowManager.showWindow("appForUser.fxml", imageBtn);
    }
    public static void buttonReturnToLogInPressed(ImageView imageView){
        WindowManager.showWindow("hello-view.fxml", imageView);
    }
}
