package src.test.java.sk.tuke.gamestudio.game.core.plumber;

import org.junit.Test;
import sk.tuke.gamestudio.game.plumber.Core.Field.PipeConnectionUtils;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PipeConnectionUtils.class)
public class PipeConnectionUtilsTest {

    private final PipeFactory factory = new PipeFactory();
    @Test
   public void testCornerPipeConnection() {
        testCornerConnection(false, CornerPipe.getNorthEastExits(), CornerPipe.getSouthEastExits(), 0, 0, 1, 0);

        testCornerConnection(false, CornerPipe.getNorthEastExits(), CornerPipe.getNorthWestExits(), 0, 0, 0, 1);

        testCornerConnection(true, CornerPipe.getNorthEastExits(), StraightPipe.getEastWestExits(), 0, 0, 1, 0);

        testCornerConnection(false, CornerPipe.getNorthEastExits(), StraightPipe.getNorthSouthExits(), 0, 0, 0, 1);

    }

    private void testCornerConnection(boolean shouldConnect, Exit[] exits1, Exit[] exits2, int x1, int y1, int x2, int y2) {
        Pipe[][] field = new Pipe[2][2];
        field[y1][x1] = factory.createPipe("CornerPipe", exits1);
        field[y2][x2] = factory.createPipe("CornerPipe", exits2);

        if (shouldConnect) {
            assertTrue(PipeConnectionUtils.isConnected(x1, y1, x2, y2, field));
        } else {
            assertFalse(PipeConnectionUtils.isConnected(x1, y1, x2, y2, field));
        }
    }
    @Test
    public void testStraightPipeConnectionHorizontal() {
        Pipe[][] field = new Pipe[1][2];
        field[0][0] = factory.createPipe("StraightPipe", StraightPipe.getEastWestExits());
        field[0][1] = factory.createPipe("StraightPipe", StraightPipe.getEastWestExits());

        assertTrue(PipeConnectionUtils.isConnected(0, 0, 1, 0, field));
    }

    @Test
   public void testStraightPipeConnectionVertical() {
        Pipe[][] field = new Pipe[2][1];

        field[0][0] = factory.createPipe("StraightPipe", StraightPipe.getNorthSouthExits());
        field[1][0] = factory.createPipe("StraightPipe", StraightPipe.getNorthSouthExits());

        assertTrue(PipeConnectionUtils.isConnected(0, 0, 0, 1, field));
    }

    @Test
   public void testNoConnectionDueToNull() {
        Pipe[][] field = new Pipe[1][2];
        field[0][0] = factory.createPipe("StraightPipe", StraightPipe.getEastWestExits());
        field[0][1] = null;

        assertFalse(PipeConnectionUtils.isConnected(0, 0, 1, 0, field));
    }
}
