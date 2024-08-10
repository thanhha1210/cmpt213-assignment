package sfu.cmpt213.as3.logic;
/**
 * A class represents a cell on the game grid, with attributes to store its content and visit status.
 * @Author Irene Luu
 * @version 1
 */
public class Cell {

    private boolean visted;
    private String content;

    public Cell() {
        this.visted = false;
        this.content = "EMPTY";
    }

    public boolean isVisted() {
        return visted;
    }
    public void setVisited(boolean visted) {
        this.visted = visted;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
