package sk.tuke.gamestudio.game.plumber.Core.Field;

import sk.tuke.gamestudio.game.plumber.Core.Pipes.Exit;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.PipeDirection;

public class PipeConnectionUtils {

    public static boolean isConnected(int x1, int y1, int x2, int y2, Pipe[][] field) {
        if (!areNeighbors(x1, y1, x2, y2)) {
            return false;
        }

        Pipe pipe1 = field[y1][x1];
        Pipe pipe2 = field[y2][x2];

        return areConnected(pipe1, pipe2, x1, y1, x2, y2);
    }
    private static boolean areNeighbors(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1;
    }

    private static boolean areConnected(Pipe pipe1, Pipe pipe2, int x1, int y1, int x2, int y2) {
        if (pipe1 == null || pipe2 == null) return false;

        PipeDirection requiredDirectionFromPipe1 = null;
        PipeDirection requiredDirectionFromPipe2 = null;

        if (x1 == x2) {
            // Pipes are vertically aligned.
            if (y1 < y2) {
                requiredDirectionFromPipe1 = PipeDirection.SOUTH;
                requiredDirectionFromPipe2 = PipeDirection.NORTH;
            } else {
                requiredDirectionFromPipe1 = PipeDirection.NORTH;
                requiredDirectionFromPipe2 = PipeDirection.SOUTH;
            }
        } else if (y1 == y2) {
            // Pipes are horizontally aligned.
            if (x1 < x2) {
                requiredDirectionFromPipe1 = PipeDirection.EAST;
                requiredDirectionFromPipe2 = PipeDirection.WEST;
            } else {
                requiredDirectionFromPipe1 = PipeDirection.WEST;
                requiredDirectionFromPipe2 = PipeDirection.EAST;
            }
        }

        return pipeHasCorrectExit(pipe1, requiredDirectionFromPipe1) && pipeHasCorrectExit(pipe2, requiredDirectionFromPipe2);
    }
    private static boolean pipeHasCorrectExit(Pipe pipe, PipeDirection requiredDirection){
        for (Exit exit : pipe.getExits()) {
            if (exit.getDirection() == requiredDirection) {
                exit.connect();
                return true;
            }
        }
        return false;
    }
}
