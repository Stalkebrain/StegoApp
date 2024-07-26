package org.stego.stego;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.stego.stego.Main.imageTwo;

public class Page5Controller {
    @FXML
    public Button button;
    @FXML
    public TableView<ImageAnalysis.Data> table;
    @FXML
    public TableView<ImageAnalysis.Data> table1;
    @FXML
    public TableView<ImageAnalysis.Data> table2;
    @FXML
    public Slider slider;

    @FXML
    public void initialize() throws IOException {
        analysis();
        button.setOnAction(event -> {
            if (imageTwo != null) {
                try {
                    analysis();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void analysis() throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageTwo, null);
        int sliderValue1 = (int) this.slider.getValue();
        ImageAnalysis.Data[] dataArray = ImageAnalysis.AnalisisImage(bufferedImage, sliderValue1);

        ObservableList<ImageAnalysis.Data> data = FXCollections.observableArrayList(dataArray);

        // Настройка таблицы для красного канала
        TableColumn<ImageAnalysis.Data, Integer> partColumnRed = new TableColumn<>("Часть");
        partColumnRed.setCellValueFactory(new PropertyValueFactory<>("part"));

        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumnRed = new TableColumn<>("Степень свободы");
        uniqueShadesColumnRed.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));

        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumnRed = new TableColumn<>("Хи-квадрат (Красный)");
        chiSquaredColumnRed.setCellValueFactory(new PropertyValueFactory<>("chiSquaredRed"));

        TableColumn<ImageAnalysis.Data, Double> pValueColumnRed = new TableColumn<>("P-значение (Красный)");
        pValueColumnRed.setCellValueFactory(new PropertyValueFactory<>("pValueRed"));

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().setAll(partColumnRed, uniqueShadesColumnRed, chiSquaredColumnRed, pValueColumnRed);

        // Настройка таблицы для зеленого канала
        TableColumn<ImageAnalysis.Data, Integer> partColumnGreen = new TableColumn<>("Часть");
        partColumnGreen.setCellValueFactory(new PropertyValueFactory<>("part"));

        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumnGreen = new TableColumn<>("Степень свободы");
        uniqueShadesColumnGreen.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));

        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumnGreen = new TableColumn<>("Хи-квадрат (Зеленый)");
        chiSquaredColumnGreen.setCellValueFactory(new PropertyValueFactory<>("chiSquaredGreen"));

        TableColumn<ImageAnalysis.Data, Double> pValueColumnGreen = new TableColumn<>("P-значение (Зеленый)");
        pValueColumnGreen.setCellValueFactory(new PropertyValueFactory<>("pValueGreen"));

        table1.setItems(data);
        table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table1.getColumns().setAll(partColumnGreen, uniqueShadesColumnGreen, chiSquaredColumnGreen, pValueColumnGreen);

        // Настройка таблицы для синего канала
        TableColumn<ImageAnalysis.Data, Integer> partColumnBlue = new TableColumn<>("Часть");
        partColumnBlue.setCellValueFactory(new PropertyValueFactory<>("part"));

        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumnBlue = new TableColumn<>("Степень свободы");
        uniqueShadesColumnBlue.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));

        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumnBlue = new TableColumn<>("Хи-квадрат (Синий)");
        chiSquaredColumnBlue.setCellValueFactory(new PropertyValueFactory<>("chiSquaredBlue"));

        TableColumn<ImageAnalysis.Data, Double> pValueColumnBlue = new TableColumn<>("P-значение (Синий)");
        pValueColumnBlue.setCellValueFactory(new PropertyValueFactory<>("pValueBlue"));

        table2.setItems(data);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.getColumns().setAll(partColumnBlue, uniqueShadesColumnBlue, chiSquaredColumnBlue, pValueColumnBlue);
    }
}
