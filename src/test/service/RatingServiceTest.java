package src.test.java.sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RatingService.class)
public class RatingServiceTest {
    private final RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void resetRatings() {
        ratingService.reset();
        assertEquals(0, ratingService.getAverageRating("Plumber"));
    }

    @Test
    public void setAndGetRating() {
        ratingService.reset();
        Date date = new Date();

        ratingService.setRating(new Rating("Plumber", "Me", 5, date));
        int rating = ratingService.getRating("Plumber", "Me");

        assertEquals(5, rating);
    }

    @Test
    public void getAverageRatingForPlumber() {
        ratingService.reset();
        Date date = new Date();

        ratingService.setRating(new Rating("Plumber", "Me", 5, date));
        ratingService.setRating(new Rating("Plumber", "Real Me", 3, date));
        ratingService.setRating(new Rating("Plumber", "Me Again", 4, date));

        int averageRating = ratingService.getAverageRating("Plumber");

        assertEquals(4, averageRating);
    }
}
