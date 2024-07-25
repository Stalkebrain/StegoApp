package org.stego.stego;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

import static org.stego.stego.Main.imageOne;
import static org.stego.stego.Main.imageTwo;
import static org.stego.stego.Main.sladerV;

public class Page2Controller {
    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public Slider slider1;
    @FXML
    public TextField textField;
    @FXML
    public TextArea textArea;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public void initialize(){
        if(imageOne != null){
            imageView1.setImage(imageOne);
        }
        if(imageTwo != null){
            imageView2.setImage(imageTwo);
        }
    }
    @FXML
    private void stegoPlus(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView1.getImage(), null);
        float slider = (float) this.slider1.getValue()/100;
        sladerV = (float) slider1.getValue()/100;
        String textf = textField.getText();
        imageView2.setImage(SwingFXUtils.toFXImage(FullImageSteganography.stegoPlusF(bufferedImage, textf, slider), null));
        imageTwo = imageView2.getImage();
    }
    @FXML
    private void extractF(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView2.getImage(), null);
        float sliderValue = sladerV;
        String extractedText = FullImageSteganography.extractFM(bufferedImage, sliderValue);

        // Очистка текущего текста и добавление нового
        textArea.clear();
        textArea.setText(extractedText);
    }
}
