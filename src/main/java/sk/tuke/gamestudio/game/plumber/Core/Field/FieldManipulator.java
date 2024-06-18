package sk.tuke.gamestudio.game.plumber.Core.Field;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.PipeFactory;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.RandomPipe;

public class FieldManipulator {
    private final int rows;
    private final int columns;

    public FieldManipulator(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }



    protected void fillEmpty(Pipe[][] field){
        PipeFactory factory = new PipeFactory();
        for(int row = 0; row < rows; row ++){
            for(int column = 0; column < columns; column ++){
                if(field[row][column] == null){
                    field[row][column] = Math.random() < 0.6 ? factory.createPipe("RandomPipe", RandomPipe.getRandomExits()) : null;
                }
            }
        }
    }
    protected void clearField(Pipe[][] field) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[i][j] = null;
            }
        }
    }
    protected void rotateRandomly(Pipe[][] field){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(field[i][j] == null) continue;
                for(int k  = 0 ; k < Math.floor(Math.random() * 2) + 1; k++){
                    field[i][j].rotate();
                }
            }
        }
    }

}
