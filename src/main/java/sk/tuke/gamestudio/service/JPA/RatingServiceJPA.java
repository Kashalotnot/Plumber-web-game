package sk.tuke.gamestudio.service.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.exceptions.RatingException;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            Rating r = entityManager.createQuery("select s from Rating s where s.game = :game and s.player =: player", Rating.class)
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .getSingleResult();
            r.setRating(rating.getRating());
            r.setRatedOn(rating.getRatedOn());
        } catch (NoResultException | RatingException ex) {
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating =  entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                .setParameter("game", game).getSingleResult();
        return averageRating != null ? averageRating.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            return entityManager.createNamedQuery("Rating.getRating", Integer.class).setParameter("game", game).setParameter("player", player).getSingleResult();

        }
    catch (Exception ex){
        return 0;
        }
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    }
}
