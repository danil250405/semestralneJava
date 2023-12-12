package animations;

import javafx.scene.image.ImageView;

public class Hover {
    public static void hoverIcons(ImageView icon){
        icon.setOnMouseEntered(event -> icon.setStyle("-fx-background-color: #F39C63"));

        // Возвращаем начальный стиль при выходе мыши
        icon.setOnMouseExited(event -> icon.setStyle("-fx-background-color: BLACK"));

    }
}
