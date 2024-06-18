package src.test.java.sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = ScoreService.class)

public class ScoreServiceTest {
    private final ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void reset() {
        scoreService.reset();

        assertEquals(0, scoreService.getTopScores("mines").size());
    }

    @Test
    public void addScore() {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("mines", "Jaro", 100, date));

        var scores = scoreService.getTopScores("mines");
        assertEquals(1, scores.size());
        assertEquals("mines", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void getTopScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("mines", "Jaro", 120, date));
        scoreService.addScore(new Score("mines", "Katka", 150, date));
        scoreService.addScore(new Score("tiles", "Zuzka", 180, date));
        scoreService.addScore(new Score("mines", "Jaro", 100, date));

        var scores = scoreService.getTopScores("mines");

        assertEquals(3, scores.size());

        assertEquals("mines", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getPlayer());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("mines", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(120, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("mines", scores.get(2).getGame());
        assertEquals("Jaro", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }
}