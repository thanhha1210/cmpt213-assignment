package sfu.cmpt213.as3.logic;

/**
 * Main class to run the game, handling game initialization, processing game logic, and checking game conditions.
 * @Author Irene Luu
 * @version 1
 */
import java.util.List;
import sfu.cmpt213.as3.ui.DisplayOutput;
import sfu.cmpt213.as3.ui.UserInteraction;
import static sfu.cmpt213.as3.ui.UserInteraction.askInitialPosition;

public class TokimonFinder {

    public static void main(String[] args) {
        int tokiNum = 10;
        int fokiNum = 5;
        boolean cheatMode = false;

        if (args.length > 3) {
            System.out.println("Error: Please enter from 0 to 3 arguments");
            System.exit(-1);
        }
        for (String arg : args) {
            if (arg.contains("--numToki=")) {
                tokiNum = Integer.parseInt(arg.substring(10));
            }
            else if (arg.contains("--numFoki=")) {
                fokiNum = Integer.parseInt(arg.substring(10));
            }
            else if (arg.contains("--cheat")) {
                cheatMode = true;
            }
            else {
                System.out.println("Error: Invalid argument!");
                System.exit(-1);
            }
        }
        checkValidArgument(tokiNum, fokiNum);
        Game game = new Game(tokiNum, fokiNum, cheatMode);
        String initPos = askInitialPosition();
        Trainer trainer = initializeTrainer(initPos);
        game.updateTrainerMove(trainer);
        if (checkGameCondition(game, trainer)) {
            System.exit(0);
        }
        processGame(game, trainer);
    }

    private static Trainer initializeTrainer(String initPos) {
        String rowStr = initPos.substring(0, 1);
        String colStr = initPos.substring(1);
        int row = (int)(rowStr.charAt(0)) - 'A' + 1;
        int col = Integer.parseInt(colStr);
        Trainer temp = new Trainer(row, col);
        return temp;
    }

    private static void checkValidArgument(int tokiNum, int fokiNum) {
        if (fokiNum < 5 || tokiNum < 5) {
            System.out.println("Error: The number of fokimon and tokimon has to be at least 5!");
            System.exit(-1);
        }
        if (tokiNum + fokiNum > 100) {
            System.out.println("Error: The number of both tokimon and tokimon has to be at most 100!");
            System.exit(-1);
        }
    }

    private static void processGame(Game game, Trainer trainer) {
        game.updateTrainerMove(trainer);
        DisplayOutput.displayGrid(game, trainer);
        DisplayOutput.displayStatus(game, trainer);

        while (true) {
            String choice = UserInteraction.takeTrainerInput().toUpperCase();
            boolean check = false;
            if (choice.equals("2")) {
                if (trainer.getSpellNum() > 0) {
                    check = true;
                    trainer.useSpell(game);
                }
                else {
                    System.out.println("The trainer has used all spells. Let's try again!");
                }
            }
            else if (choice.equals("A") || choice.equals("D") || choice.equals("W") || choice.equals("S")) {
                check = trainer.makeMove(choice);
            }
            else {
                System.out.println("Error: Invalid option. Let's try again!");
            }
            if (check) {
                game.updateTrainerMove(trainer);
                if (checkGameCondition(game, trainer)) {
                    break;
                }
                DisplayOutput.displayGrid(game, trainer);
                DisplayOutput.displayStatus(game, trainer);
            }
        }
    }

    private static boolean checkGameCondition(Game game, Trainer trainer) {
        if (game.getTokimons().size() == 0) {
            DisplayOutput.displayComplete(game, trainer);
            System.out.println("Hurray! Trainer has collected all the tokimons. You win!!");
            return true;
        }
        List<Character> fokimons = game.getFokimons();
        for (Character fok : fokimons) {
            if ((fok.getRow() == trainer.getRow()) && (fok.getCol() == trainer.getCol())) {
                DisplayOutput.displayComplete(game, trainer);
                System.out.println("Oops! Trainer faces a fokimon. You lose!!");
                return true;
            }
        }
        return false;
    }

}
