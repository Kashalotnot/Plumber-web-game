package src.test.java.sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = CommentService.class)
public class CommentServiceTest {
    private final CommentService commentService = new CommentServiceJDBC();

    @Test
    public void resetComments() {
        commentService.reset();
        List<Comment> comments = commentService.getComments("Plumber");
        assertEquals(0, comments.size());
    }

    @Test
    public void addAndGetComments() {
        commentService.reset();
        Date date = new Date();
        commentService.addComment(new Comment("Plumber", "Me", "THIS GAME IS SO GOOD, 5/5, BEST GAME I'VE EVER PLAYED!", date));

        List<Comment> comments = commentService.getComments("Plumber");
        assertEquals(1, comments.size());
        assertEquals("Plumber", comments.get(0).getGame());
        assertEquals("Me", comments.get(0).getPlayer());
        assertEquals("THIS GAME IS SO GOOD, 5/5, BEST GAME I'VE EVER PLAYED!", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }
}
