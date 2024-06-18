package sk.tuke.gamestudio.service.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        String averageRatingUrl = url + "/" + game;
        Integer averageRating = restTemplate.getForObject(averageRatingUrl, Integer.class);
        return Objects.requireNonNullElse(averageRating, 0);
    }

    @Override
    public int getRating(String game, String player){
        String ratingUrl = url + "/" + game + "/" + player;
        Integer rating = restTemplate.getForObject(ratingUrl, Integer.class);
        return Objects.requireNonNullElse(rating, 0);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
