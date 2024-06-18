package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public class RandomPipe extends Pipe {

    public RandomPipe(Exit[] exits) {
        super(exits);
    }
    public static Exit[] getRandomExits(){
        return Math.random() < 0.5 ? StraightPipe.getRandomExits() : CornerPipe.getRandomExits();
    }
}
