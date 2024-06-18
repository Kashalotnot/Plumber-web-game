package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.plumber.CUI.CUI;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.service.*;
import sk.tuke.gamestudio.service.REST.CommentServiceRestClient;
import sk.tuke.gamestudio.service.REST.RatingServiceRestClient;
import sk.tuke.gamestudio.service.REST.ScoreServiceRestClient;


@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class GamestudioApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GamestudioApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(CUI ui, Field field) {
        return args -> ui.play(field);
    }

    @Bean
    public CUI CUI() {
        return new CUI();
    }

    @Bean
    public Field field() {
        return new Field();
    }
    @Bean
    public RestTemplate restTemplate(){ return new RestTemplate();}

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService(){return new RatingServiceRestClient();}

    @Bean
    public CommentService commentService(){ return new CommentServiceRestClient();}


}