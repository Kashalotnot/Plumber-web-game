package sk.tuke.gamestudio.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery(    name = "Rating.getAverageRating",
        query = "SELECT AVG(s.rating) FROM Rating s WHERE s.game=:game")
@NamedQuery(    name = "Rating.getRating",
        query = "SELECT s.rating FROM Rating s WHERE s.game=:game AND s.player=:player")
@NamedQuery(    name = "Rating.reset",
        query = "DELETE FROM Rating")
public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident; //identifikator
    private String game;

    private String player;

    private int rating;

    private Date ratedOn;
    public Rating(){}

    public Rating(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.rating = points;
        this.ratedOn = playedOn;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    public int getRating() {
        return rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
