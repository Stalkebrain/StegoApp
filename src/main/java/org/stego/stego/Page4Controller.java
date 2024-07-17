package org.stego.stego;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.stego.stego.Main.imageTwo;

public class Page4Controller {
    @FXML
    public BarChart barChar;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;

    @FXML
    public void initialize() {
        button1.setOnAction(event -> {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageTwo, null);
            int[] histogram = ImageHistogram.hisogramm(bufferedImage);
            if (histogram != null) {
                try {
                    showHistogramWindow(histogram);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showHistogramWindow(int[] histogram) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("histogramview.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Гистограмма");
        stage.setWidth(800);  // Шир// ина окна
        stage.setHeight(600);

        stage.setScene(new Scene(loader.load()));
        HistogramController controller = loader.getController();
        controller.displayHistogram(histogram);
        stage.show();

    }
}
