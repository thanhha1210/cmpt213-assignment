package sfu.cmpt213.as3.logic;

/**
 * A class represents the trainer in the game, capable of moving and using spells.
 * @Author Irene Luu
 * @version 1
 */

import sfu.cmpt213.as3.ui.UserInteraction;
import java.util.List;
import java.util.Random;

public class Trainer {
    private int spellNum = 3;
    private int row;
    private int col;


    public Trainer(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }

    public int getSpellNum() {
        return spellNum;
    }

    public boolean makeMove(String dir) {
        boolean check = false;
        if (((row == 1) && dir.equals("W")) || ((row == 10) && dir.equals("S")) || ((col == 1) && dir.equals("A")) || ((col == 10) && dir.equals("D")))
        {
            System.out.println("Invalid move. The trainer will be out of grid. Let's try again!");
            return check;
        }
        check = true;
        switch (dir) {
            case "A" -> this.setCol(col - 1);
            case "D" -> this.setCol(col + 1);
            case "S" -> this.setRow(row + 1);
            case "W" -> this.setRow(row - 1);
        }
        return check;
    }

    public void useSpell(Game game) {
        String spell = "";
        spell = UserInteraction.getSpellChoice();
        while (!spell.equals("1") && !spell.equals("2") && !spell.equals("3")) {
            System.out.println("Invalid choice for spell: Let's try again!");
            spell = UserInteraction.getSpellChoice();
        }
        switch (spell) {
            case "1" -> {
                Random rand = new Random();
                this.row = rand.nextInt(10) + 1;
                this.col = rand.nextInt(10) + 1;
            }
            case "2" -> {
                List<Character> tokimons = game.getTokimons();
                Random rand = new Random();
                Character tokimon;
                do {
                    tokimon = tokimons.get(rand.nextInt(tokimons.size()));
                } while (tokimon.isRevealed());

                tokimon.setRevealed(true);
                System.out.println("Reveal: There is a tokimon at position " + tokimon);
                game.setTokimons(tokimons);
            }
            case "3" -> {
                List<Character> fokimons = game.getFokimons();
                Cell[][] grid = game.getGrid();

                Random rand = new Random();
                int index = rand.nextInt(fokimons.size());
                Character fokimon = fokimons.get(index);
                grid[fokimon.getRow()][fokimon.getCol()].setContent("EMPTY");
                fokimons.remove(index);

                game.setFokimons(fokimons);
                game.setGrid(grid);
            }
        }
        spellNum--;
    }
}
