package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public class PipeFactory {
    public Pipe createPipe(String type, Exit[] exits) {
        if ("StraightPipe".equalsIgnoreCase(type)) {
            return new StraightPipe(exits.clone());
        } else if ("CornerPipe".equalsIgnoreCase(type)) {
            return new CornerPipe(exits.clone());
        } else if("RandomPipe".equalsIgnoreCase(type)){
            return new RandomPipe(exits.clone());
        }
        else {
            throw new IllegalArgumentException("Invalid pipe type: " + type);
        }
    }
}
