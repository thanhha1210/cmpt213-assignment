package sfu.cmpt213.as3.logic;
/**
 * A class represents the game state, including the grid, Tokimons, and Fokimons.
 * @Author Irene Luu
 * @version 1
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Character> tokimons = new ArrayList<Character>();
    private List<Character> fokimons = new ArrayList<Character>();
    private Cell[][] grid;

    public Game(int tokiNum, int fokiNum, boolean cheatMode) {
        this.grid = new Cell[11][11];
        initializeGrid(cheatMode);
        populateTokimons(tokiNum);
        populateFokimons(fokiNum);
    }

    private void initializeGrid(boolean cheatMode) {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                this.grid[row][col] = new Cell();
                if (cheatMode) {
                    grid[row][col].setVisited(true);
                }
            }
        }
    }

    private void populateTokimons(int tokiNum) {
        Random rand = new Random();
        while (tokimons.size() < tokiNum) {
            int row = rand.nextInt(10) + 1;
            int col = rand.nextInt(10) + 1;
            if (grid[row][col].getContent().equals("EMPTY")) {
                grid[row][col].setContent("TOKIMON");
                tokimons.add(new Character(row, col));
            }
        }
    }
    private void populateFokimons(int fokiNum) {
        Random rand = new Random();
        while (fokimons.size() < fokiNum) {
            int row = rand.nextInt(10) + 1;
            int col = rand.nextInt(10) + 1;
            if (grid[row][col].getContent().equals("EMPTY")) {
                grid[row][col].setContent("FOKIMON");
                fokimons.add(new Character(row, col));
            }
        }
    }
    public List<Character> getTokimons() {
        return new ArrayList<>(tokimons);
    }
    public List<Character> getFokimons() {
        return new ArrayList<>(fokimons);
    }
    public Cell[][] getGrid() {
        Cell[][] gridCopy = new Cell[11][11];
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                gridCopy[row][col] = new Cell();
                gridCopy[row][col].setVisited(grid[row][col].isVisted());
                gridCopy[row][col].setContent(grid[row][col].getContent());
            }
        }
        return gridCopy;
    }

    public void setTokimons(List<Character> tokimons) {
        this.tokimons = tokimons;
    }
    public void setFokimons(List<Character> fokimons) {
        this.fokimons = fokimons;
    }
    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public void updateTrainerMove(Trainer trainer) {
        int row = trainer.getRow();
        int col = trainer.getCol();
        grid[row][col].setVisited(true);
        for (int i = 0; i < tokimons.size(); i++) {
            Character tokimon = tokimons.get(i);
            if (tokimon.getRow() == row && tokimon.getCol() == col) {
                tokimons.remove(tokimon);
                break;
            }
        }

    }
}
