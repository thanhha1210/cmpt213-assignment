package sfu.cmpt213.as3.ui;

/**
 * A class handnles user inputs & interations
 * @Author Irene Luu
 * @version 1
 */

import java.util.Scanner;

public class UserInteraction {

    public static String askInitialPosition() {
        Scanner input = new Scanner(System.in);
        boolean check = false;
        String temp = "";
        while (!check) {
            System.out.println("Please enter the initial position for trainer(E.g: B5) : ");
            String initPos = input.nextLine().trim();

            if (initPos.length() < 2) {
                System.out.println("Invalid initial position. Let's try again!");
                continue;
            }
            String row = initPos.substring(0, 1);
            String col = initPos.substring(1);
            try {
                row = row.toUpperCase();
                int colInt = Integer.parseInt(col);
                if (row.charAt(0) < 'A' || row.charAt(0) > 'J' || colInt < 1 || colInt > 10)
                    throw new Exception();
                else {
                    temp = row + col;
                    break;
                }

            } catch (Exception e) {
                System.out.println("Invalid initial position. Let's try again!");
            }
        }
        return temp;
    }

    public static String takeTrainerInput() {
        Scanner input = new Scanner(System.in);
        boolean check = false;
        String choice="";
        while (!check) {
            System.out.println("Please enter either W, A, S, or D to move up, left, down, or right from current position or enter 2 to choose using spell: ");
            choice = input.nextLine().trim().toUpperCase();
            if (!choice.equals("2") && !choice.equals("A") && !choice.equals("D") && !choice.equals("W") && !choice.equals("S")) {
               System.out.println("Invalid input. Let's try again!");
            }
            else {
                check = true;
            }
        }
        return choice;
    }

    public static String getSpellChoice() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter 1, 2, 3 to choose option for spell: ");
        System.out.println("\t1. Jump the player to another grid location.");
        System.out.println("\t2. Randomly reveal the location of one of the Tokimons.");
        System.out.println("\t3. Randomly eliminate one of the Fokimons.");
        return input.nextLine().trim();
    }
}
