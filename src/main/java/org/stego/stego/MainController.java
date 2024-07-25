package org.stego.stego;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

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

    FXMLLoader loader;

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
    }
}
