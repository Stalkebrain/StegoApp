package org.stego.stego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    public static Image imageOne;
    public static Image imageTwo;
    public static Image imageThree;
    public static Float sladerV;
    public static ChoiceBox<String> channelV;
    public static int maxChars;
    //public static ImageView Cock11;

    //проверять переменную при загрузки компонента
    //если не пустая,то загрузить из переменной

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void showPage(int pageNumber) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("page" + pageNumber + ".fxml"));
        Parent root = loader.load(); // Get the root element
        Scene immediateScene = new Scene(root);
        stage.setTitle("Страница " + pageNumber);
        stage.setScene(immediateScene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        launch();
    }
}