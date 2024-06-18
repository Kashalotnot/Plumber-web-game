package sk.tuke.gamestudio.game.plumber.Commands;

import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;

public class RotatePipeCommand implements Command {
    @Override
        public String execute(Field field, int[] params) {
            Pipe selectedPipe = field.getField()[params[0]][params[1]];
            if (selectedPipe == null) {
                return  "No pipe on this position!";
            }
            selectedPipe.rotate();
           return "Rotated!";
        }

}
