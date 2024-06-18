package sk.tuke.gamestudio.game.plumber.Core.Field;

import sk.tuke.gamestudio.game.plumber.Core.Pipes.Exit;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;

public class FieldValidator {
    private final int rows;
    private final int columns;

    public FieldValidator(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
    public boolean isSolved(Pipe[][] field, boolean[][] visited) {
        // Use depth-first search to check if the field is connected
        return isConnectedDFS(0, 0, visited, field);
    }

    // Method to perform depth-first search
    private boolean isConnectedDFS(int x, int y, boolean[][] visited, Pipe[][] field) {
        // Check if the current position is out of bounds or already visited
        if (x < 0 || x >= columns + 1 || y < 0 || y >= rows || visited[y][x]) {
            return false;
        }
        // Mark the current position as visited
        visited[y][x] = true;
        // Get the pipe at the current position
        Pipe pipe = field[y][x];
        // Check if the current position is the destination
        if (x == columns - 1 + 1 && y == rows - 1) {
            return true;
        }
        // Get the exits of the current pipe
        Exit[] exits = pipe.getExits();

        for (Exit exit : exits) {
            int newX = x;
            int newY = y;
            // Move to the next position based on the exit's direction
            switch (exit.getDirection()) {
                case NORTH:
                    newY--;
                    break;
                case SOUTH:
                    newY++;
                    break;
                case EAST:
                    newX++;
                    break;
                case WEST:
                    newX--;
                    break;
            }
            // Check if the next position is a valid connection and perform depth-first search from there
            if (isValidConnection(x, y, newX, newY, field) && isConnectedDFS(newX, newY, visited, field)) {
                return true;
            }
        }
        return false;
    }
    boolean isValidConnection(int x1, int y1, int x2, int y2, Pipe[][] field) {
        // Check if the next position is within bounds and connected to the current position
        return x2 >= 0 && x2 < columns + 1 && y2 >= 0 && y2 < rows && PipeConnectionUtils.isConnected(x1, y1, x2 ,y2, field);
    }
}

