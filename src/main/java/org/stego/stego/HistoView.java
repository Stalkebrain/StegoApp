package org.stego.stego;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class HistoView {

    public static int[] hisogramm(BufferedImage image) {
        // Проверка размеров изображения
        if (image.getWidth() != 512 || image.getHeight() != 512) {
            System.out.println("Изображение должно быть размером 512x512 пикселей.");
            return null;
        }

        // Создание гистограммы
        int[] histogram = new int[256];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int brightness = new Color(image.getRGB(i, j)).getRed();
                histogram[brightness]++;
            }
        }
        return histogram;
    }

    public static void displayHistogram(int[] histogram, BarChart<String, Number> histogramChart) {
        histogramChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < histogram.length; i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(i), histogram[i]);
            series.getData().add(data);
        }
        histogramChart.getData().add(series);
        histogramChart.setLegendVisible(false); // Убираем легенду
        histogramChart.setStyle("-fx-background-color: #2d2d2d;");

        // Устанавливаем стиль для полосок
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #2d2d2d;");
        }
    }
}
