package org.stego.stego;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
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
    public Button visual;
    @FXML
    public Button histo;

    int[] histogram;
    @FXML
    private void downloadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.gif", "*.bmp"));
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
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock.getImage(), null);
        BufferedImage visualAttackImage = VisualAttack.visualizeLSB(bufferedImage);
        Cock1.setImage(SwingFXUtils.toFXImage(visualAttackImage, null));
    }

    @FXML
    private void histoDisp(ActionEvent event) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock.getImage(), null);
        histogram=hisogramm(bufferedImage);
        //System.out.println(Arrays.toString(histogram));
        HistogramDisplay histogramDisplay = new HistogramDisplay();
        histogramDisplay.displayHisto(histogram);
    }
}
