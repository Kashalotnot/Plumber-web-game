package sk.tuke.gamestudio.game.plumber.Commands;

import sk.tuke.gamestudio.game.plumber.Core.Field.Field;

public interface Command {
    String execute(Field field, int[] params);
}
