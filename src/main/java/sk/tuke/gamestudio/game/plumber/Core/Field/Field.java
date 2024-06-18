
package sk.tuke.gamestudio.game.plumber.Core.Field;

import sk.tuke.gamestudio.game.plumber.Core.GameModel.GameSettings;
import sk.tuke.gamestudio.game.plumber.Core.Pipes.Pipe;

public class Field {
    // 2D array representing the field
    private Pipe[][] field;
    private int rows;
    private int columns;

    // 2D array to keep track of visited cells
    boolean[][] visited;
    private FieldValidator validator;
    private FieldGenerator generator;
    private FieldManipulator manipulator;

    private int levelNum;
    private final int MAX_FIELD_SIZE = 15;
    public Field() {
        this.levelNum = GameSettings.getLevelNum();
        initializeFieldComponents();
    }

    private void initializeFieldComponents() {
        this.rows = mapValue(levelNum);
        this.columns = mapValue(levelNum);
        this.validator = new FieldValidator(rows, columns);
        this.manipulator = new FieldManipulator(rows, columns);
        this.field = new Pipe[rows][columns + 1];
        this.visited = new boolean[rows][columns + 1];
        this.generator = new FieldGenerator(rows, columns);
    }
    // Method to map the level number to a value for rows and columns
    public int mapValue(int x) {
        int calculatedValue = (int)(3 + Math.floor((x - 1) * 8.0 / 14));
        return Math.min(calculatedValue, MAX_FIELD_SIZE);
    }
    public void generate(){
        do {
            clearField();
        } while (!generator.tryGenerate(field, visited));
        manipulator.rotateRandomly(field);
       manipulator.fillEmpty(field);
    }
    public void generateWithoutRotation(){
        do {
            clearField();
        } while (!generator.tryGenerate(field, visited));
        manipulator.fillEmpty(field);
    }
    public boolean isSolved() {
        boolean[][] visited = new boolean[rows][columns + 1];
        return validator.isSolved(field, visited);
    }
    public void clearField(){
        manipulator.clearField(field);
    }
    public Pipe[][] getField() {
        return field;
    }
    public void updateNewField() {
        this.levelNum = GameSettings.getLevelNum();
        initializeFieldComponents();
    }
    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}

