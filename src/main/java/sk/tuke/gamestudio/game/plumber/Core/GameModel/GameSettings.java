package sk.tuke.gamestudio.game.plumber.Core.GameModel;

public class GameSettings {
    private static int levelNum = 1;

    public static int getLevelNum() {
        return levelNum;
    }

    public static void setLevelNum(int levelNum) {
        GameSettings.levelNum = levelNum;
    }
}
