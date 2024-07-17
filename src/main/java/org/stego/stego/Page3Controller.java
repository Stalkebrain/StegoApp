package org.stego.stego;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

import static org.stego.stego.Main.*;

public class Page3Controller {
    @FXML
    public Button button1;
    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public void initialize(){
        if(imageTwo != null){
            imageView1.setImage(imageTwo);
        }
        if(imageThree != null){
            imageView2.setImage(imageThree);
        }
    }
    @FXML
    private void visualAttack(ActionEvent event) {
        // Проверяем, есть ли изображение в Cock11
        if (imageView1.getImage() != null) {
            // Преобразуем изображение из ImageView в BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView1.getImage(), null);

            // Выполняем визуальную атаку
            BufferedImage visualAttackImage = VisualAttack.visualizeLSB(bufferedImage);

            // Устанавливаем результат в другой ImageView
            imageView2.setImage(SwingFXUtils.toFXImage(visualAttackImage, null));
            imageThree = imageView2.getImage();
        }
    }
}
