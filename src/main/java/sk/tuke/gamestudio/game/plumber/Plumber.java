package sk.tuke.gamestudio.game.plumber;

import sk.tuke.gamestudio.game.plumber.CUI.CUI;
import sk.tuke.gamestudio.game.plumber.Core.Field.Field;
import sk.tuke.gamestudio.service.*;

public class Plumber {
    public static void main(String[] args) {
        Field field = new Field();
        CUI ui = new CUI();
        ui.play(field);
    }
}