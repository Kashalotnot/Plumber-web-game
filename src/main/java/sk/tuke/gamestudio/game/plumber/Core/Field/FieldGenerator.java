package sk.tuke.gamestudio.game.plumber.Core.Field;

import sk.tuke.gamestudio.game.plumber.Core.Pipes.*;

import java.util.Objects;


public class FieldGenerator {
    private final int rows;
    private final int columns;
    private final PipeFactory factory;
    private final int MAX_EXECUTION_TIME_IN_NANOSEC = 15000000;

    public FieldGenerator(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.factory = new PipeFactory();
    }

    // Method to try to generate the field
    protected boolean tryGenerate(Pipe[][] field, boolean[][] visited) {

        long startTime = System.nanoTime();
        visited = new boolean[rows][columns + 1];
        PipeFactory factory = new PipeFactory();
        // Set the first and last pipe
        field[0][0] = factory.createPipe("StraightPipe", StraightPipe.getEastWestExits());
        field[0][0].setInCorrectPath(true);
        field[rows - 1][columns] = factory.createPipe("StraightPipe", StraightPipe.getEastWestExits());
        visited[0][0] = true;

        int prevRow = 0;
        int prevColumn = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                if (row == 0 && column == 0) continue;
                do {
                    // Create a random pipe
                    field[row][column] = factory.createPipe("RandomPipe", RandomPipe.getRandomExits());
                    long stopTime = System.nanoTime();
                    // Check if execution time exceeded the maximum (field generation rules is too complex so this is a workaround to prevent infinite loop)
                    if (stopTime - startTime >= MAX_EXECUTION_TIME_IN_NANOSEC) return false;
                    // Check if the pipe is connected
                    if (PipeConnectionUtils.isConnected(column, row, prevColumn, prevRow, field)) {
                        if(row == rows - 1 && column == columns - 1){
                            if(!PipeConnectionUtils.isConnected(column, row, column + 1, row, field)) continue;
                            else break;
                        }
                        // Check if the pipe's exit is facing a wall or another pipe
                        if (isExitFacingWall(column, row, field)) {
                            continue;
                        }
                        if (isExitFacingPipe(column, row, visited, field)) continue;

                        break;
                    }
                } while (true);
                visited[row][column] = true;
                field[row][column].setInCorrectPath(true);
                prevRow = row;
                prevColumn = column;
                if (row == rows - 1 && column == columns - 1) return true;
                // Loop through the pipe's exits
                for (Exit exit : field[row][column].getExits()) {
                    if (!exit.isConnected()) {
                        // Move to the next pipe based on the exit's direction
                        switch ((exit.getDirection())) {
                            case WEST: {
                                column -= 2;

                                break;
                            }
                            case NORTH: {
                                row -= 1;
                                column--;
                                break;
                            }
                            case SOUTH: {
                                row += 1;
                                column--;
                                break;
                            }
                            default:
                                break;
                        }
                    }
                }
            }

        }
        return true;
    }
    private Exit findNotConnectedExit(int pipePosX, int pipePosY, Pipe[][] field){
        for (Exit exit : field[pipePosY][pipePosX].getExits()) {
            if (!exit.isConnected()) {
                return exit;
            }
        }
        return null;
    }
    boolean isExitFacingPipe(int pipePosX, int pipePosY, boolean[][] visited, Pipe[][] field) {
        Exit notConnectedExit = findNotConnectedExit(pipePosX, pipePosY, field);
        return switch (Objects.requireNonNull(notConnectedExit).getDirection()) {
            case EAST -> visited[pipePosY][pipePosX + 1];
            case NORTH -> visited[pipePosY - 1][pipePosX];
            case WEST -> visited[pipePosY][pipePosX - 1];
            case SOUTH -> visited[pipePosY + 1][pipePosX];
        };
    }

    boolean isExitFacingWall(int pipePosX, int pipePosY, Pipe[][] field) {
        Exit notConnectedExit = findNotConnectedExit(pipePosX, pipePosY, field);
        switch (Objects.requireNonNull(notConnectedExit).getDirection()) {
            case SOUTH: {
                if (pipePosY == rows - 1) return true;
                break;
            }
            case NORTH: {
                if (pipePosY == 0) return true;
                break;
            }
            case WEST: {
                if (pipePosX == 0) return true;
                break;
            }
            case EAST: {
                if (pipePosX == columns - 1) return true;
                break;
            }
            default:
                return false;

        }
        return false;
    }

}