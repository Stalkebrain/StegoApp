package org.stego.stego;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class HistogramController {

    @FXML
    private BarChart<String, Number> histogramChart;

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
}
