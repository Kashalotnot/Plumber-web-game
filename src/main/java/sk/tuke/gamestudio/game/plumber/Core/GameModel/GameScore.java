package sk.tuke.gamestudio.game.plumber.Core.GameModel;

public class GameScore {
        private int maxTimeBonus = 100;
        private int score;


        public GameScore() {
            this.score = 0;
        }
        public void resetScore(){this.score = 0;}

    public void addScore(int points) {
            this.score += points;
        }

        public int getScore() {
            return this.score;
        }

    public void calculateScore(boolean isHintUsed, int timeTakenInSeconds){
        int levelBaseScore = GameSettings.getLevelNum() * 5;
        score += (int) (levelBaseScore * (isHintUsed ? 0.2 : 1) * (1.0 - timeTakenInSeconds / 100.0));
    }
}
