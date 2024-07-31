package org.stego.stego;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public Slider slider1;
    @FXML
    public Slider slider2;
    @FXML
    public CheckBox syncCheckBox;

    @FXML
    public void initialize() throws IOException {
        // Установка обработчика для кнопки
        updateTables();
        button.setOnAction(event -> {
            if (imageTwo != null) {
                try {
                    // Передаем значения слайдеров для обновления таблиц
                    updateTables();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Обработчик для чекбокса
        syncCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                synchronizeSliders();
            }
        });

        // Обработчики для слайдеров
        slider.valueProperty().addListener(createSliderChangeListener());
        slider1.valueProperty().addListener(createSliderChangeListener());
        slider2.valueProperty().addListener(createSliderChangeListener());
    }

    private ChangeListener<Number> createSliderChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (syncCheckBox.isSelected()) {
                synchronizeSliders();
            }
        };
    }

    private void synchronizeSliders() {
        double value = slider.getValue();
        slider1.setValue(value);
        slider2.setValue(value);
    }

    private void updateTables() throws IOException {
        // Получение значений слайдеров
        int sliderValueRed = (int) this.slider.getValue();
        int sliderValueGreen = (int) this.slider1.getValue();
        int sliderValueBlue = (int) this.slider2.getValue();

        // Обновляем таблицу для красного канала
        updateTable(table, sliderValueRed, "Red");

        // Обновляем таблицу для зеленого канала
        updateTable(table1, sliderValueGreen, "Green");

        // Обновляем таблицу для синего канала
        updateTable(table2, sliderValueBlue, "Blue");
    }

    private void updateTable(TableView<ImageAnalysis.Data> table, int sliderValue, String color) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageTwo, null);
        ImageAnalysis.Data[] dataArray = ImageAnalysis.AnalisisImage(bufferedImage, sliderValue, "Горизонтально"); // Всегда горизонтально

        ObservableList<ImageAnalysis.Data> data = FXCollections.observableArrayList(dataArray);

        // Настройка таблицы
        TableColumn<ImageAnalysis.Data, Integer> partColumn = new TableColumn<>("Часть");
        partColumn.setCellValueFactory(new PropertyValueFactory<>("part"));

        TableColumn<ImageAnalysis.Data, Integer> uniqueShadesColumn = new TableColumn<>("Степень свободы");
        uniqueShadesColumn.setCellValueFactory(new PropertyValueFactory<>("uniqueShades"));

        TableColumn<ImageAnalysis.Data, Double> chiSquaredColumn = new TableColumn<>("Хи-квадрат (" + color + ")");
        chiSquaredColumn.setCellValueFactory(new PropertyValueFactory<>("chiSquared" + color));

        TableColumn<ImageAnalysis.Data, Double> pValueColumn = new TableColumn<>("P-значение (" + color + ")");
        pValueColumn.setCellValueFactory(new PropertyValueFactory<>("pValue" + color));

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().setAll(partColumn, uniqueShadesColumn, chiSquaredColumn, pValueColumn);
    }
}
