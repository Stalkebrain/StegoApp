package org.stego.stego;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HistogramController {
    public HistogramController(BarChart<String, Number> histogramChart) {
        this.histogramChart = histogramChart;
    }

    public HistogramController(){}

    @FXML
    private BarChart<String, Number> histogramChart;

    @FXML
    private void handleSaveButtonAction() {
        Stage stage = (Stage) histogramChart.getScene().getWindow();
        saveHistogramToFile(stage);
    }

    public void displayHistogram(int[] histogram) {
        histogramChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < histogram.length; i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(i), histogram[i]);
            series.getData().add(data);
        }
        histogramChart.getData().add(series);
        histogramChart.setStyle("-fx-background-color: white;");
        histogramChart.setLegendVisible(false); // Убираем легенду

        // Устанавливаем стиль для полосок
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: black;");
        }
    }

    public void saveHistogramToFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить гистограмму");

        // Устанавливаем фильтр для выбора только файлов PNG
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG файлы (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалоговое окно для сохранения файла
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            WritableImage image = histogramChart.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
