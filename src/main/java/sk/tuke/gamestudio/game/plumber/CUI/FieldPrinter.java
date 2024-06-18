package sk.tuke.gamestudio.game.plumber.CUI;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
import sk.tuke.gamestudio.service.ScoreService;

import java.text.SimpleDateFormat;
import java.util.List;

public class FieldPrinter {
    private static final String GREEN_COLOR = "\u001B[32m";
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String BLUE_COLOR = "\u001B[34m";
    private static final String RED_COLOR = "\u001B[31m";

    public static void printField(Field field, boolean hint) {
        Pipe[][] pipes = field.getField();
        int rows = pipes.length;
        int columns = pipes[0].length - 1;
        printTopBorder(columns);
        printFieldContent(pipes, rows, columns, hint);
        printBottomBorder(columns);
        printColumnNumbers(columns);
    }

    private static void printTopBorder(int columns) {
        System.out.print("    ");
        System.out.print(GREEN_COLOR);
        for (int j = 0; j < columns; j++) {
            System.out.print("+---");
        }
        System.out.println("+" + RESET_COLOR);
    }

    private static void printFieldContent(Pipe[][] pipes, int rows, int columns, boolean hint) {
        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d " + (i == 0 ? BLUE_COLOR : GREEN_COLOR) + "|", i);
            for (int j = 0; j < columns; j++) {
                PipeRenderer.renderPipe(pipes[i][j], hint, i, rows);
            }
            System.out.println("|" + RESET_COLOR);
        }
    }

    private static void printBottomBorder(int columns) {
        System.out.print("    ");
        System.out.print(GREEN_COLOR);
        for (int j = 0; j < columns; j++) {
            System.out.print("+---");
        }
        System.out.println("+" + RESET_COLOR);
    }

    private static void printColumnNumbers(int columns) {
        System.out.print("     ");
        for (int j = 0; j < columns; j++) {
            System.out.printf("%-4d", j);
        }
        System.out.println();
    }
    public static void printHelp(){
        System.out.println("\033[32m" + // ANSI green text color start
                "HELP\n\n" +
                "Rules:\n" +
                "Connect all pipes to allow water flow from start to end. Rotate pipes to form a continuous path.\n" +
                "Win by creating an unbroken pipeline.\n\n" +
                "Controls:\n" +
                "- ?: Show help.\n" +
                "- H: Get a hint.\n\n" +
                "Plan your moves and enjoy the game!" +
                "\033[0m");
    }
    public static void printTopScores(ScoreService scoreService){
        List<Score> topScores = scoreService.getTopScores("Plumber");
        if(topScores.isEmpty()){
            System.out.println("So empty... Play and be first!");
            return;
        }
        int place = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Adjust the format as needed

        for (Score score : topScores) {
            String dateOnly = dateFormat.format(score.getPlayedOn());
            System.out.println((place == 1 ? RED_COLOR : BLUE_COLOR) + place + BLUE_COLOR +  ". " + score.getPlayer() + " " + score.getPoints() + " " + dateOnly + RESET_COLOR);
            place++;
        }
    }
}