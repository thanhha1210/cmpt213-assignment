package cmpt213.asn4.memorygame.ui;

import cmpt213.asn4.memorygame.game.Card;
import cmpt213.asn4.memorygame.game.GameLogic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * GameUI is a class responsible for creating and managing the user interface
 * for the Memory Matching Game. It handles the display of the game board, the
 * new game button, and the move counter label.
 *
 * @Author Irene Luu
 * @Version 01
 */
public class GameUI {
    private final GameLogic gameLogic;
    private final GridPane gridPane;
    private final Button newGameButton;
    private final Label moveLabel;
    private final ImageAssignment imgAssignment;

    public GameUI() {
        gridPane = new GridPane();
        imgAssignment = new ImageAssignment();
        gameLogic = new GameLogic();
        newGameButton = new Button("New game");
        moveLabel = new Label("Moves: 0");
        createGridPane();
        newGameButton.setOnAction(event -> restartGame());
    }

    public VBox createScene() {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(10, gridPane, moveLabel, newGameButton);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void createGridPane() {
        Card[][] grid = gameLogic.getBoard().getGrid();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ImageView imgView = new ImageView();
                imgView.setFitHeight(110);
                imgView.setFitWidth(110);
                final int finalRow = row;
                final int finalCol = col;
                imgAssignment.assignNotRevealed(imgView);
                imgView.setOnMouseClicked(event -> flip(finalRow, finalCol));
                gridPane.add(imgView, col, row);
            }
        }
    }

    private void restartGame() {
        gameLogic.resetGame();
        moveLabel.setText("Moves: 0");
        gridPane.getChildren().clear();
        createGridPane();
    }

    private void flip(int row, int col) {
        Card curCard = (gameLogic.getBoard().getGrid())[row][col];
        if (!curCard.isRevealed()) {
            boolean check = gameLogic.handleFlip(row, col);
            ImageView imgView = (ImageView) gridPane.getChildren().get(row * 4 + col);
            imgAssignment.assignRevealed(imgView, curCard.getValue());
            if (!check) {
                gridPane.setDisable(true);  // prevent choosing card in the flip time
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                    imgAssignment.assignNotRevealed((ImageView) gridPane.getChildren().get(gameLogic.getPrevChildIndex()));
                    imgAssignment.assignNotRevealed((ImageView) gridPane.getChildren().get(row * 4 + col));
                    gridPane.setDisable(false); // allow to listener to click again
                }));
                timeline.play();
            }
            int numMove = gameLogic.getNumMove();
            moveLabel.setText(gameLogic.getNumRemain() == 0 ? ("Hurray. You win in " + numMove / 2 + " moves!") : ("Moves: " + numMove / 2));
        }
        if (gameLogic.getNumRemain() == 0) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                gridPane.setDisable(true);
                restartGame();
                gridPane.setDisable(false);
            }));
            timeline.play();
        }
    }
}
