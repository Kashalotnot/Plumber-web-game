package sk.tuke.gamestudio.game.plumber.CUI;

import sk.tuke.gamestudio.game.plumber.Core.Pipes.Exit;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.PipeDirection;

public class PipeRenderer {
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String HINT_COLOR = "\u001B[31m";
    private static final String GRAY_COLOR = "\u001B[90m";
    private static final String GREEN_COLOR = "\u001B[32m";
    private static final String BLUE_COLOR = "\u001B[34m";

    public static void renderPipe(Pipe pipe, boolean hint, int row, int totalRows) {
        if (pipe == null) {
            System.out.print(GRAY_COLOR + " .  " + GREEN_COLOR);
        } else {
            boolean isHint = hint && pipe.isInCorrectPath();
            String pipeColor = isHint ? HINT_COLOR : RESET_COLOR;
            System.out.print(pipeColor);
            printPipeShape(pipe);
            System.out.print(row == totalRows - 1 ? BLUE_COLOR : GREEN_COLOR);
        }
    }

    private static void printPipeShape(Pipe pipe) {
        Exit[] exits = pipe.getExits();
        if (exits[0].getDirection() == PipeDirection.EAST && exits[1].getDirection() == PipeDirection.WEST) {
            System.out.print(" -  ");
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.SOUTH) {
            System.out.print(" |  ");
        } else if (exits[0].getDirection() == PipeDirection.EAST && exits[1].getDirection() == PipeDirection.SOUTH) {
            System.out.print(" ┌  ");
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.WEST) {
            System.out.print(" ┘  ");
        } else if (exits[0].getDirection() == PipeDirection.SOUTH && exits[1].getDirection() == PipeDirection.WEST) {
            System.out.print(" ┐  ");
        } else if (exits[0].getDirection() == PipeDirection.NORTH && exits[1].getDirection() == PipeDirection.EAST) {
            System.out.print(" └  ");
        } else {
            System.out.print(" ?  ");
        }
    }
}