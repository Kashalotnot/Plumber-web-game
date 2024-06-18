package sk.tuke.gamestudio.server.Controller;


import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.plumber.CUI.PipeRenderer;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameScore;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameSettings;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Exit;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.PipeDirection;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/plumber")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PlumberController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;
    private boolean hint = false;
    private int score = 0;
    private Field field = new Field();
    private boolean isGened = false; // Indicates if the game field has been generated
    private long startTime; // Start time of the current game session

    // Handles new game initialization
    @RequestMapping("/new")
    public String newGame(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/plumber/login";
        }
        // Store score from previous game session
        if(score != 0) {

             scoreService.addScore(new Score("Plumber", (String) session.getAttribute("user"), score, new Date(), getCurrentTimeSec()));
        }
        // Reset game variables for a new game session
        score = 0;
        GameSettings.setLevelNum(1);
        hint = false;
        field = new Field();
        startTime = System.currentTimeMillis();
        field.generate();
        System.out.println(GameSettings.getLevelNum());
        isGened = true;


        return "redirect:/plumber";
    }

    @GetMapping("/score")
    public String score() {
        return "score";
    }

    // Sets hint mode to true
    @RequestMapping("/hint")
    public String setHintTrue(HttpSession session){
        if (!isLoggedIn(session)) {
            return "redirect:/plumber/login";
        }

        hint = true;
        return "plumber";
    }
    @RequestMapping
    public String plumber( @RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model, HttpSession session, Integer rating) {
        if (!isLoggedIn(session)) {
            return "redirect:/plumber/login";
        }
        // Generate new field if not already generated
        if(!isGened){
            isGened = true;
            startTime = System.currentTimeMillis();
            field.generate();
        }

        String user = (String) session.getAttribute("user");


        // Handle user rating input
        if(rating != null){
            ratingService.setRating(new Rating("Plumber", user, rating, new Date()));
        }
        // Rotate clicked pipe
        if(row != null && column != null && field.getField()[row][column] != null) {
            field.getField()[row][column].rotate();
        }
        int userRating = ratingService.getRating("Plumber", user);


        if(field.isSolved()) {
            int timeTaken = getCurrentTimeSec(); // Calculate time taken to solve the level
            calculateScore(hint, timeTaken); // Calculate score with hint usage and time taken
            GameSettings.setLevelNum(GameSettings.getLevelNum() + 1);
            hint = false;
            field.updateNewField();
            field.generate();
        }
        model.addAttribute("scores", scoreService.getTopScores("Plumber"));
        model.addAttribute("userRating", userRating);
        System.out.println(userRating);
        model.addAttribute("user", user);
        return "plumber";
    }

    public int getCurrentTimeSec(){
        System.out.println(System.currentTimeMillis() - startTime/1000);
        return (int)(System.currentTimeMillis() - startTime) / 1000;

    }
    public String getHtmlField() {

        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");

        for (int row = 0; row < field.getRows(); row++) {
            sb.append("<tr>\n");
            //System.out.printf("%2d " + "|", i);
            for (int column = 0; column < field.getColumns(); column++) {

                //PipeRenderer.renderPipe(field.getField()[i][j], false, i, field.getRows()); //for now hint is false
                sb.append("<td>\n");
                sb.append("<a href='/plumber?row=").append(row).append("&column=").append(column).append(" '>\n");
                sb.append("<img src='/images/").append(getImageName(field.getField()[row][column])).append(row == 0 && column == 0 ? "_valve" : "").append(".png'>\n");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
           // System.out.println("|");
        }

        sb.append("</table>\n");
        return sb.toString();

    }

    // Generates HTML stars rating representation
    public String getAverageRating() {
        StringBuilder sb = new StringBuilder();
        int averageRating = ratingService.getAverageRating("Plumber");
        for (int i = 0; i < 5; i++) {
            sb.append(averageRating > i ? "<img src='/images/Stars/star_red.png' class='h-6 w-6 mb-2'>" : "<img src='/images/Stars/star_empty.png' class='h-6 w-6 mb-2'>");
        }
        return sb.toString();
    }

    // Determines image name based on pipe type and exits configuration
    private String getImageName(Pipe pipe){
        if(pipe == null) return "empty";
        String path = "";
        if(hint && pipe.isInCorrectPath()) path = "hint/";

        Exit[] exits = pipe.getExits();
        if (exits[0].getDirection() == PipeDirection.EAST && exits[1].getDirection() == PipeDirection.WEST) {
            path += "EastWest";
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.SOUTH) {
            path += "NorthSouth";
        } else if (exits[0].getDirection() == PipeDirection.EAST && exits[1].getDirection() == PipeDirection.SOUTH) {
            path +=  "SouthEast";
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.WEST) {
            path += "NorthWest";
        } else if (exits[0].getDirection() == PipeDirection.SOUTH && exits[1].getDirection() == PipeDirection.WEST) {
            path +=  "SouthWest";
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.EAST) {
            path += "NorthEast";
        }

        return path;

    }
    private boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }
    public void calculateScore(boolean isHintUsed, int timeTakenInSeconds){
        int levelBaseScore = GameSettings.getLevelNum() * 5;
        score += (int) (levelBaseScore * (isHintUsed ? 0.2 : 1) * (1.0 - timeTakenInSeconds / 100.0));
    }


}


