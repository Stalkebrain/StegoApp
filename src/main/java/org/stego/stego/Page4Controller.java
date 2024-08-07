package org.stego.stego;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.stego.stego.Main.imageTwo;

public class Page4Controller {
    @FXML
    private BarChart<String, Number> barChar;
    @FXML
    private Button button1;
    @FXML
    private Button buttonDraw;
    @FXML
    private ChoiceBox<String> channelChoiceBox;

    private BufferedImage bufferedImage;
    private int[] histogramRed;
    private int[] histogramGreen;
    private int[] histogramBlue;
    private int[] histogramChoose;

    @FXML
    public void initialize() throws IOException {
        bufferedImage = SwingFXUtils.fromFXImage(imageTwo, null);
        histogramRed = ImageHistogram.getHistogram(bufferedImage, ImageHistogram.ColorChannel.RED);
        histogramGreen = ImageHistogram.getHistogram(bufferedImage, ImageHistogram.ColorChannel.GREEN);
        histogramBlue = ImageHistogram.getHistogram(bufferedImage, ImageHistogram.ColorChannel.BLUE);

        // Устанавливаем начальное значение в ChoiceBox
        channelChoiceBox.setValue("Красный");

        // Отрисовываем гистограмму для начального канала
        showHistogramOnPane(histogramRed);
        histogramChoose = histogramRed;

        // Обработчик кнопки "Отрисовать"
        buttonDraw.setOnAction(event -> {
            String selectedChannel = channelChoiceBox.getValue();
            switch (selectedChannel) {
                case "Красный":
                    showHistogramOnPane(histogramRed);
                    histogramChoose = histogramRed;
                    break;
                case "Зелёный":
                    showHistogramOnPane(histogramGreen);
                    histogramChoose = histogramGreen;
                    break;
                case "Синий":
                    showHistogramOnPane(histogramBlue);
                    histogramChoose = histogramBlue;
                    break;
            }
        });

        // Обработчик кнопки "Открыть гистограмму в новом окне"
        button1.setOnAction(event -> {
            if (histogramChoose != null) {
                try {
                    showHistogramWindow(histogramChoose);
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
        stage.setWidth(800);
        stage.setHeight(600);

        stage.setScene(new Scene(loader.load()));
        HistogramController controller = loader.getController();
        controller.displayHistogram(histogram);
        stage.show();
    }

    private void showHistogramOnPane(int[] histogram) {
        barChar.getData().clear(); // Очищаем существующие данные
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < histogram.length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), histogram[i]));
        }
        barChar.getData().add(series);
        barChar.setLegendVisible(false); // Убираем легенду
        barChar.setStyle("-fx-background-color: #2d2d2d;");

        // Устанавливаем стиль для полосок
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: black;");
        }
    }

}
