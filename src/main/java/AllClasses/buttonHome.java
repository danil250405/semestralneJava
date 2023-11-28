package AllClasses;

import com.example.semestralnejava.WindowManager;
import javafx.scene.image.ImageView;

public class buttonHome {
    public static void buttonHomePressed(ImageView imageBtn){
        System.out.println("Image Clicked!");

        WindowManager.showWindow("app.fxml", imageBtn);

    }
}
