module org.stego.stego {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires commons.math3;


    opens org.stego.stego to javafx.fxml;
    exports org.stego.stego;
}