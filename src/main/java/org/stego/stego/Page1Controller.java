package org.stego.stego;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

import static org.stego.stego.Main.imageOne;

public class Page1Controller {
    @FXML
    public Button button1;
    @FXML
    public ImageView imageView;
    @FXML
    public void initialize(){
        if(imageOne != null){
            imageView.setImage(imageOne);
        }
    }
    @FXML
    private void downloadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageOne = image;
        }
    }
}
