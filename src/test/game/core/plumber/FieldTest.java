package src.test.java.sk.tuke.gamestudio.game.core.plumber;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameSettings;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
@SpringBootTest(classes = Field.class)
public class FieldTest {
    private Field field;

        @BeforeEach
        public void setUp() {
            this.field = new Field();
        }

        @Test
        public void testFieldSizeBasedOnLevel() {

            setAndTestFieldSizeForLevel(1);
            setAndTestFieldSizeForLevel(5);
            setAndTestFieldSizeForLevel(10);
            setAndTestFieldSizeForLevel(10000);
        }

        private void setAndTestFieldSizeForLevel(int level) {
            GameSettings.setLevelNum(level);
            field.updateNewField();

            int expectedSize = field.mapValue(level);
            if(level == 10000) expectedSize = 15;
            assertEquals(expectedSize, field.getRows());
            assertEquals(expectedSize, field.getColumns());

        }

        @Test
        public void testFieldSolve() {
            GameSettings.setLevelNum(1);
            field.updateNewField();
            field.generateWithoutRotation();
            assertTrue(field.isSolved());

            GameSettings.setLevelNum(10);
            field.updateNewField();
            field.generateWithoutRotation();
            assertTrue(field.isSolved());

            GameSettings.setLevelNum(10000);
            field.updateNewField();
            field.generateWithoutRotation();
            assertTrue(field.isSolved());

            //============================
            GameSettings.setLevelNum(1);
            field.updateNewField();
            field.generate();
            assertFalse(field.isSolved());

            GameSettings.setLevelNum(5);
            field.updateNewField();
            field.generate();
            assertFalse(field.isSolved());
        }

    @Test
    public void testFieldClear() {
        field.generate();
        field.clearField();
        Pipe[][] clearedField = field.getField();

        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getColumns(); j++) {
                assertNull(clearedField[i][j]);
            }
        }
    }
}
