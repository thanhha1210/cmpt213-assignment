package cmpt213.asn4.memorygame.game;

/**
 * Board is a class responsible for creating and managing the game board
 * for the Memory Matching Game. It initializes the board with a shuffled
 * set of card pairs.
 *
 * @Author Irene Luu
 * @Version 01
 */

import java.util.Random;

public class Board {
    // hold the grid value
    private Card[][] grid;

    public Board() {
        grid = new Card[4][4];
        shuffleGrid();
    }

    public Card[][] getGrid() {
        return grid;
    }

    // create the random board
    private void shuffleGrid() {
        Random rand = new Random();
        int row, col, count;
        for (int k = 1; k <= 8; k++) {
            count = 0;
            do {
                row = rand.nextInt(4);
                col = rand.nextInt(4);
                if (grid[row][col] == null) {
                    grid[row][col] = new Card(k);
                    count++;
                }
            } while (count < 2);
        }
    }


}
