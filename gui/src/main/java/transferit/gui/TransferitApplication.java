package transferit.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class TransferitApplication extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        var s = new StageImpl("/css/welcome-style.css", "/fxml/welcome.fxml");
        s.show();

    }
}
