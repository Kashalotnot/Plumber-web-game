package sk.tuke.gamestudio.game.plumber.CUI;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameScore;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    static protected Object handleInput(String input, int rows, int columns) throws Exception {
        Pattern pattern = Pattern.compile("\\s*([Hh?])\\s*|\\s*(\\d+)\\s+(\\d+)\\s*");

        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            if (matcher.group(1) != null) {

                char letter = matcher.group(1).charAt(0);
                return new char[] {letter};
            } else {

                int firstNumber = Integer.parseInt(matcher.group(2));
                int secondNumber = Integer.parseInt(matcher.group(3));

                if (!(firstNumber < rows)) {
                    throw new Exception("The first number must be less than " + rows + ".");
                }

                if (!(secondNumber < columns)) {
                    throw new Exception("The second number must be less than " + columns + ".");
                }
                return new int[] {firstNumber, secondNumber};
            }
        } else {
            throw new Exception("Input format is incorrect. Please enter 'H', 'h', 'R', or 'r', or two numbers separated by space.");
        }
    }
    protected static boolean askForContinue(Scanner scanner) {
        while (true) {
            System.out.println("Wanna continue? (Y/N)");
            String input = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(input)) {
                return true;
            } else if ("N".equals(input)) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no.");
            }
        }
    }
    protected static int handleMenuInput(Scanner scanner) throws Exception {
        System.out.print("Your choice: ");
        String input = scanner.nextLine();

        Pattern pattern = Pattern.compile("^\\s*(1|2|3|4)\\s*$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new Exception("Invalid input. Please enter a number between 1 and 4.");
        }
    }

}
