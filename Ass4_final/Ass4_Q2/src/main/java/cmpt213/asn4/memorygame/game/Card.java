package cmpt213.asn4.memorygame.game;

/**
 * Card is a class representing an individual card in the Memory Matching Game.
 * Each card has a value and a state indicating whether it is revealed or not.
 *
 * @Author Irene Luu
 * @Version 01
 */

public class Card {
    private final int value;
    private boolean isRevealed;

    public Card(int value) {
        this.value = value;
        this.isRevealed = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }
    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    @Override
    public boolean equals(Object obj) {
        Card o = (Card) obj;
        return value == o.value;
    }

}
