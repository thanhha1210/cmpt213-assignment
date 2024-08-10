package cmpt213.asn5.client.ui;

/**
 * Main application class for the Pokemon Card application.
 * Initializes and displays the main UI.
 * @Author Irene Luu
 * @version 01
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PokimonCard extends Application {
    @Override
    public void start(Stage stage) {
        MainUI mainUI = new MainUI();
        Scene scene = new Scene(mainUI.createScene(), 500, 600);
        stage.setTitle("Pokemon Card");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
