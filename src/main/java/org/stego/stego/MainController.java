package org.stego.stego;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.util.Arrays;

import static org.stego.stego.ImageHistogram.hisogramm;

public class MainController {
    @FXML
    public ImageView Cock;
    @FXML
    public ImageView Cock1;
    @FXML
    public ImageView Cock11;
    @FXML
    public Button visual;
    @FXML
    public Button histo;
    @FXML
    public Slider slider;
    @FXML
    public Button buttonOk;
    @FXML
    public TextField textField1;
    @FXML
    public TextFlow Textflow;

    int[] histogram;
    @FXML
    private void downloadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            Cock.setImage(image);
        }
        visual.setVisible(true);
        histo.setVisible(true);
    }
    @FXML
    private void visualAttack(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
        BufferedImage visualAttackImage = VisualAttack.visualizeLSB(bufferedImage);
        Cock1.setImage(SwingFXUtils.toFXImage(visualAttackImage, null));
    }

    @FXML
    private void histoDisp(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
        histogram=hisogramm(bufferedImage);
        //System.out.println(Arrays.toString(histogram));
        HistogramDisplay histogramDisplay = new HistogramDisplay();
        histogramDisplay.displayHisto(histogram);
    }

    @FXML
    private void stegoPlus(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock.getImage(), null);
        float slider = (float) this.slider.getValue();
        String textf = textField1.getText();
        Cock11.setImage(SwingFXUtils.toFXImage(FullImageSteganography.stegoPlusF(bufferedImage, textf, slider), null));
    }
    @FXML
    private void extractF(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
        float sliderValue = (float) this.slider.getValue();
        String extractedText = FullImageSteganography.extractFM(bufferedImage, sliderValue);

        // Очистка текущего текста и добавление нового
        Textflow.getChildren().clear();
        Textflow.getChildren().add(new Text(extractedText));
    }
}

