package sfu.cmpt213.as3.ui;
/**
 * A class handles the display of game elements such as the game grid and game status.
 * @Author Irene Luu
 * @version 1
 */
import sfu.cmpt213.as3.logic.Trainer;
import sfu.cmpt213.as3.logic.Cell;
import sfu.cmpt213.as3.logic.Game;

public class DisplayOutput {

    public static void displayGrid(Game aGame, Trainer aTrainer) {
        System.out.println("Game Grid: \n");
        Cell[][] grid = aGame.getGrid();

        System.out.println("\t   1 2 3 4 5 6 7 8 9 10");
        for (int row = 1; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (col == 0) {
                    System.out.print("\t " + (char)(row - 1+ 'A') + " ");
                } else {
                    if (!grid[row][col].isVisted()) {
                        System.out.print("~");
                    } else {
                        if (row == aTrainer.getRow() && col == aTrainer.getCol()) {
                            System.out.print("@");
                        }

                        if (grid[row][col].getContent().equals("TOKIMON")) {
                            System.out.print("$");
                        }
                        else if (grid[row][col].getContent().equals("FOKIMON")) {
                            System.out.print("X");
                        }
                        else {
                            System.out.print(" ");
                        }
                    }
                    if (row != aTrainer.getRow() || col != aTrainer.getCol()) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void displayComplete(Game aGame, Trainer aTrainer) {
        System.out.println("Game Grid: \n");
        Cell[][] grid = aGame.getGrid();
        System.out.println("\t   1 2 3 4 5 6 7 8 9 10");
        for (int row = 1; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (col == 0) {
                    System.out.print("\t" + (char)(row - 1 + 'A') + " ");
                }
                else {
                    if (row == aTrainer.getRow() && col == aTrainer.getCol()) {
                        System.out.print("@");
                    }

                    if (grid[row][col].getContent().equals("TOKIMON")) {
                        System.out.print("$");
                    }
                    else if (grid[row][col].getContent().equals("FOKIMON")) {
                        System.out.print("X");
                    }
                    else {
                        System.out.print(" ");
                    }
                    if (row != aTrainer.getRow() || col != aTrainer.getCol()) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void displayStatus(Game aGame, Trainer aTrainer) {
        System.out.println("Game Information: ");
        System.out.println("\tThe game has "  + aGame.getTokimons().size() + " Tokimons, and " + aGame.getFokimons().size() + " Fokimons left.");
        System.out.println("\tThe trainer has " + aTrainer.getSpellNum() + " spells left.");
    }
}
