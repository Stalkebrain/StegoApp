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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import static org.stego.stego.HistoView.hisogramm;
import static org.stego.stego.Main.imageOne;
import static org.stego.stego.Main.imageTwo;

public class MainController {
    @FXML
    public Pane paneTwo;
    @FXML
    private Button chooseImg;
    @FXML
    private Button addStego;
    @FXML
    private Button visualAttack;
    @FXML
    private Button showHistogram;
    @FXML
    private Button tableXi;

    int[] histogram;
    FXMLLoader loader;


//    @FXML
//    private void analysis(ActionEvent event) throws IOException {
//        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
//        int sliderValue1 = (int) this.slider1.getValue();
//        ImageAnalysis.Data[] dataArray = ImageAnalysis.AnalisisImage(bufferedImage,sliderValue1);
//
//        ObservableList<ImageAnalysis.Data> data = FXCollections.observableArrayList(dataArray);
//
//        TableColumn<ImageAnalysis.Data, Integer> partColumn = new TableColumn<>("Часть");
//        partColumn.setCellValueFactory(new PropertyValueFactory<>("part"));
//
//        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumn = new TableColumn<>("Степень свободы");
//        uniqueShadesColumn.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));
//
//        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumn = new TableColumn<>("Хи-квадрат");
//        chiSquaredColumn.setCellValueFactory(new PropertyValueFactory<>("chiSquared"));
//
//        TableColumn<ImageAnalysis.Data, Double> pValueColumn = new TableColumn<>("P-значение");
//        pValueColumn.setCellValueFactory(new PropertyValueFactory<>("pValue"));
//
//        Table.setItems(data);
//        Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        Table.getColumns().setAll(partColumn, uniqueShadesColumn, chiSquaredColumn, pValueColumn);
//    }


    @FXML
    public void initialize() {
        chooseImg.setOnAction(event -> {
           paneTwo.getChildren().clear();
           loader = new FXMLLoader(getClass().getResource("page1.fxml"));
           try {
               paneTwo.getChildren().add(loader.load());
           } catch (IOException e) {
               e.printStackTrace();
           }
        });
        addStego.setOnAction(event -> {
            paneTwo.getChildren().clear();
            loader = new FXMLLoader(getClass().getResource("page2.fxml"));
            try {
                paneTwo.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        visualAttack.setOnAction(event -> {
            paneTwo.getChildren().clear();
            loader = new FXMLLoader(getClass().getResource("page3.fxml"));
            try {
                paneTwo.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showHistogram.setOnAction(event -> {
            paneTwo.getChildren().clear();
            loader = new FXMLLoader(getClass().getResource("page4.fxml"));
            try {
                paneTwo.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tableXi.setOnAction(event -> {
            paneTwo.getChildren().clear();
            loader = new FXMLLoader(getClass().getResource("page5.fxml"));
            try {
                paneTwo.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });








//        button3.setOnAction(event -> {
//            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(Cock11.getImage(), null);
//            int[] histogram = ImageHistogram.hisogramm(bufferedImage);
//            if (histogram != null) {
//                try {
//                    showHistogramWindow(histogram);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void showHistogramWindow(int[] histogram) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("histogramview.fxml"));
//        Stage stage = new Stage();
//
//        stage.setTitle("Гистограмма");
//        stage.setWidth(1920);  // Шир// ина окна
//        stage.setHeight(1080);
//        stage.setResizable(false);
//
//        stage.setScene(new Scene(loader.load()));
//        HistogramController controller = loader.getController();
//        controller.displayHistogram(histogram);
//        stage.show();
  }
}
