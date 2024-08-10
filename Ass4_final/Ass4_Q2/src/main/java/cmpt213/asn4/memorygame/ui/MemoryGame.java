package cmpt213.asn4.memorygame.ui;

/**
 * MemoryGame is the main entry point for the Memory Matching Game application.
 * It extends the JavaFX Application class and sets up the primary stage and scene
 * for the game UI.
 * @Author Irene Luu
 * @Version 01
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryGame extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameUI gameUI = new GameUI();
        Scene scene = new Scene(gameUI.createScene(), 600, 700);
        primaryStage.setTitle("Memory Matching Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
