package org.stego.stego;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.image.BufferedImage;

import static org.stego.stego.Main.imageTwo;

public class Page6Controller {
    @FXML
    private TableView<RSAnalysis.RSData> rsTable;
    @FXML
    private TableColumn<RSAnalysis.RSData, String> channelColumn;
    @FXML
    private TableColumn<RSAnalysis.RSData, String> valueColumn;
    @FXML
    private Button rsButton;

    private ObservableList<RSAnalysis.RSData> rsDataList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Настройка колонок таблицы
        channelColumn.setCellValueFactory(new PropertyValueFactory<>("channel"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        channelColumn.setResizable(false);
        valueColumn.setResizable(false);

        // Установка начального списка данных
        rsTable.setItems(rsDataList);
        rsTable.setVisible(false);
        // Установка обработчика нажатия на кнопку
        rsButton.setOnAction(event -> updateTable());
    }

    private void updateTable() {
        // Показываем таблицу
        rsTable.setVisible(true);

        // Очищаем существующие данные
        rsDataList.clear();

        // Получаем данные и заполняем таблицу
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageTwo, null);
        if(bufferedImage == null) {

        }
        try {
            // Получаем новые данные
            RSAnalysis.rsMetod(bufferedImage, rsDataList);

            // Ограничиваем количество строк до 4
            if (rsDataList.size() > 4) {
                rsDataList.subList(4, rsDataList.size()).clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
