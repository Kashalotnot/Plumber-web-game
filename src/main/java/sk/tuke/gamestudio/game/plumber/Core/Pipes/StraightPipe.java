package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public class StraightPipe extends Pipe {
    private static final Exit[] EastWestExits = {new Exit(PipeDirection.EAST), new Exit(PipeDirection.WEST)};
    private static final Exit[] NorthSouthExits = {new Exit(PipeDirection.NORTH), new Exit(PipeDirection.SOUTH)};

    public StraightPipe(Exit[] exits) {
        super(deepCopyExits(exits));
    }

    private static Exit[] deepCopyExits(Exit[] originalExits) {
        Exit[] copiedExits = new Exit[originalExits.length];
        for (int i = 0; i < originalExits.length; i++) {
            copiedExits[i] = new Exit(originalExits[i].getDirection());
        }
        return copiedExits;
    }

    public static Exit[] getEastWestExits() {
        return deepCopyExits(EastWestExits);
    }

    public static Exit[] getNorthSouthExits() {
        return deepCopyExits(NorthSouthExits);
    }

    public static Exit[] getRandomExits() {
        return Math.random() < 0.5 ? deepCopyExits(EastWestExits) : deepCopyExits(NorthSouthExits);
    }
}
