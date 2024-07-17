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
    public TableView table;
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

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().setAll(partColumn, uniqueShadesColumn, chiSquaredColumn, pValueColumn);
    }
}
