package sfu.cmpt213.as3.logic;
/**
 * A class represents a character (Tokimon or Fokimon) on the grid with specific coordinates.
 * @Author Irene Luu
 * @version 1
 */

public class Character {
    private final int row;
    private final int col;
    private boolean isRevealed = false;

    Character(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public boolean isRevealed() {
        return isRevealed;
    }
    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    @Override
    public String toString() {
        return ((char) (row + 'A' - 1)) + "" + col;
    }

}
