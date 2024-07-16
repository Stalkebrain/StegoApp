package org.stego.stego;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import static org.stego.stego.HistoView.hisogramm;

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
    public Slider slider;
    @FXML
    public Button buttonOk;
    @FXML
    public TextField textField1;
    @FXML
    public TextArea Textflow;
    @FXML
    public Button buttonTable;
    @FXML
    public TableView Table;
    @FXML
    public Slider slider1;
    @FXML
    private Button button3;

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
    }
    @FXML
    private void visualAttack(ActionEvent event) {
        // Проверяем, есть ли изображение в Cock11
        if (Cock11.getImage() != null) {
            // Преобразуем изображение из ImageView в BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);

            // Выполняем визуальную атаку
            BufferedImage visualAttackImage = VisualAttack.visualizeLSB(bufferedImage);

            // Устанавливаем результат в другой ImageView
            Cock1.setImage(SwingFXUtils.toFXImage(visualAttackImage, null));
        }
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
        Textflow.clear();
        Textflow.setText(extractedText);
    }
    @FXML
    private void analysis(ActionEvent event) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
        int sliderValue1 = (int) this.slider1.getValue();
        ImageAnalysis.Data[] dataArray = ImageAnalysis.AnalisisImage(bufferedImage,sliderValue1);

        ObservableList<ImageAnalysis.Data> data = FXCollections.observableArrayList(dataArray);

        TableColumn<ImageAnalysis.Data, Integer> partColumn = new TableColumn<>("Часть");
        partColumn.setCellValueFactory(new PropertyValueFactory<>("part"));

        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumn = new TableColumn<>("Степень свободы");
        uniqueShadesColumn.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));

        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumn = new TableColumn<>("Хи-квадрат");
        chiSquaredColumn.setCellValueFactory(new PropertyValueFactory<>("chiSquared"));

        TableColumn<ImageAnalysis.Data, Double> pValueColumn = new TableColumn<>("P-значение");
        pValueColumn.setCellValueFactory(new PropertyValueFactory<>("pValue"));

        Table.setItems(data);
        Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Table.getColumns().setAll(partColumn, uniqueShadesColumn, chiSquaredColumn, pValueColumn);
    }
    @FXML
    public void initialize() {
        button3.setOnAction(event -> {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
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
