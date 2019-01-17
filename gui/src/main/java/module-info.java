module transferit.gui {

    exports transferit.gui to javafx.graphics;
    opens transferit.gui.controller to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
}