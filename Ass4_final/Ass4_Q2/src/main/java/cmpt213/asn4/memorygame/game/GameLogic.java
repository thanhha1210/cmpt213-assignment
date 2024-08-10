package cmpt213.asn4.memorygame.game;

/**
 * HandelGameLogic is a class responsible for managing the game logic
 * of the Memory Matching Game. It handles the game board, move count,
 * and the logic for flipping cards and checking for matches.
 *
 * @Author Irene Luu
 * @Version 01
 */

public class GameLogic {
    private Board board;
    private Card prevCard;
    private int prevChildIndex;
    private int numMove;
    private int numRemain;


    public GameLogic() {
        board = new Board();
        prevCard = null;
        prevChildIndex = -1;
        numMove = 0;
        numRemain = 8;
    }

    public void resetGame() {
        board = new Board();
        prevCard = null;
        numMove = 0;
        numRemain = 8;
        prevChildIndex = -1;
    }

    public Board getBoard() {
        return board;
    }
    public int getNumMove() {
        return numMove;
    }
    public int getNumRemain() {
        return numRemain;
    }
    public int getPrevChildIndex() {
        return prevChildIndex;
    }


    public boolean handleFlip(int row, int col) {
        numMove++;
        Card[][] grid = board.getGrid();
        Card curCard = grid[row][col];
        boolean check = true;
        // first card
        if (numMove % 2 != 0) {
            prevChildIndex = row * 4 + col;
            prevCard = curCard;
            curCard.setRevealed(true);
        }
        // second card
        else {
            if (curCard.equals(prevCard)) {
                curCard.setRevealed(true);
                numRemain--;
            }
            else {
                prevCard.setRevealed(false);
                check = false;
            }
        }
        return check;   // check = false -> flip both cards
    }


}
