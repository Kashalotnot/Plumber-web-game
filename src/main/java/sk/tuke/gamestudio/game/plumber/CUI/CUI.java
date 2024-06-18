package sk.tuke.gamestudio.game.plumber.CUI;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.plumber.Commands.RotatePipeCommand;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameScore;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameSettings;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameState;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class CUI {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;
    private static boolean hint;
    Scanner scanner;
    GameScore score;
    public CUI(){
        hint  = false;
        scanner = new Scanner(System.in);
        score = new GameScore();
    }

    private GameState handleMenuState(Field field){
        PlumberMenu.printMenu(ratingService);
        int choice = 0;

        try {
            choice = InputHandler.handleMenuInput(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        switch (choice) {
            case 1:
                field.generate();
                return GameState.PLAYING;
            case 2:
                // Print the help and return to menu
                FieldPrinter.printHelp();
                scanner.nextLine();
                return GameState.MENU;
            case 3:
                // Print the top scores and return to menu
                FieldPrinter.printTopScores(scoreService);
                scanner.nextLine();
                return GameState.MENU;
            case 4:
                return GameState.EXIT;
            default:
                return GameState.MENU;
        }
    }
    private GameState handlePlayingState(Field field, long startTime){

        FieldPrinter.printField(field, hint);
        System.out.println("Input position of a pipe to rotate (e.g., '5 3'), where the first coordinate is less than " + (field.getRows() - 1) + " and the second is less than " + (field.getColumns() - 1) + ":");
        String input = scanner.nextLine();
        try {

            Object userInput = InputHandler.handleInput(input, field.getRows(), field.getColumns());
            if (userInput instanceof int[] inputPipePos) {

                RotatePipeCommand rotatePipeCommand = new RotatePipeCommand();
                System.out.println(rotatePipeCommand.execute(field, inputPipePos));
                return solvedHandle(field, startTime);

            } else if (userInput instanceof char[] command) {
                switch (command[0]){
                    case 'H': case 'h':{
                        hint = true;
                        return GameState.PLAYING;
                    }
                    case '?':{
                        FieldPrinter.printHelp();
                        scanner.nextLine();
                        return GameState.PLAYING;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return GameState.PLAYING;
        }
        return  GameState.EXIT;
    }
    protected void endGameInputHandle(Scanner scanner, GameScore score, long startTime){
        long stopTime = System.nanoTime();
        String ratingPattern = "^[1-5]$";

        System.out.print("Input Your name: ");
        String name = scanner.nextLine().trim();


        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please input your name: ");
            name = scanner.nextLine().trim();
        }
        System.out.println(startTime);
        System.out.println(stopTime);
        int time = (int) ((stopTime - startTime) / 1000000000);
        System.out.println(time);
        scoreService.addScore(new Score("Plumber", name, score.getScore(), new Date(), time));
        // Ask for rating
        System.out.print("Would you like to leave a rating (1-5)? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        int rating = 0;
        if ("yes".equals(response)) {
            String ratingInput;
            boolean validRating = false;

            while (!validRating) {
                System.out.print("Please enter your rating (1-5): ");
                ratingInput = scanner.nextLine().trim();

                if (ratingInput.matches(ratingPattern)) {
                    rating = Integer.parseInt(ratingInput);
                    validRating = true;
                } else {
                    System.out.println("Invalid rating. Please enter a number from 1 to 5.");
                }
            }
            ratingService.setRating(new Rating("Plumber", name, rating, new Date()));
        }

        // Ask for comment
        System.out.print("Would you like to leave a comment? (yes/no): ");
        response = scanner.nextLine().trim().toLowerCase();

        String comment = "";
        if ("yes".equals(response)) {
            System.out.print("Please enter your comment: ");
            comment = scanner.nextLine().trim();


            while (comment.isEmpty()) {
                System.out.print("Comment cannot be empty. Please input your comment: ");
                comment = scanner.nextLine().trim();
            }
            commentService.addComment(new Comment("Plumber", name, comment, new Date()));
        }


        System.out.println("Comments: ");
        List<Comment> comments= commentService.getComments("Plumber");
        if(comments.isEmpty()) System.out.println("No comment...");
        else{
            for(Comment entry: comments){
                System.out.println(entry.getPlayer() + ": " + entry.getComment() + " for " + entry.getGame() + " on "  +entry.getCommentedOn());
            }
        }
        System.out.println("Thank you for your opinion!");
    }

    private GameState solvedHandle(Field field, long startTime){
        if(field.isSolved()) {
            long stopTime = System.nanoTime();
            GameSettings.setLevelNum(GameSettings.getLevelNum() + 1);
            System.out.println("CONGRATS! Solved");
            score.calculateScore( hint, (int) (stopTime - startTime) / 1000000000);
            if(InputHandler.askForContinue(scanner)){
                hint = false;
                field.updateNewField();
                field.generate();

                //set score
            }
            else return GameState.EXIT;
        }
        return GameState.PLAYING;
    }

     public void play(Field field){
         long startTime = 0;
        hint = false;
        GameState currentState = GameState.MENU;

         while (currentState != GameState.EXIT) {
             switch (currentState) {
                 case MENU:
                    currentState = handleMenuState(field);
                     break;
                 case PLAYING:
                     startTime = System.nanoTime();
                    currentState = handlePlayingState(field, startTime);
                     break;
             }
         }
         endGameInputHandle(scanner, score, startTime);
         //ask for score and rating and comment
         scanner.close();
    }

}
