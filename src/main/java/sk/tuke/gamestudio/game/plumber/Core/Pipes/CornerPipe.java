package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public class CornerPipe extends Pipe {
    private static final Exit[] NorthEastExits = {new Exit(PipeDirection.NORTH), new Exit(PipeDirection.EAST)};
    private static final Exit[] NorthWestExits = {new Exit(PipeDirection.NORTH), new Exit(PipeDirection.WEST)};
    private static final Exit[] SouthEastExits = {new Exit(PipeDirection.EAST), new Exit(PipeDirection.SOUTH)};
    private static final Exit[] SouthWestExits = {new Exit(PipeDirection.SOUTH), new Exit(PipeDirection.WEST)};
        public CornerPipe(Exit[] exits) {
            super(deepCopyExits(exits));
        }

        private static Exit[] deepCopyExits(Exit[] originalExits) {
            Exit[] copiedExits = new Exit[originalExits.length];
            for (int i = 0; i < originalExits.length; i++) {
                copiedExits[i] = new Exit(originalExits[i].getDirection());
            }
            return copiedExits;
        }
    public static Exit[] getRandomExits() {
        double rand = Math.random();
        if (rand < 0.25) return deepCopyExits(NorthEastExits);
        else if (rand < 0.5) return deepCopyExits(NorthWestExits);
        else if (rand < 0.75) return deepCopyExits(SouthEastExits);
        else return deepCopyExits(SouthWestExits);
    }

    public static Exit[] getNorthEastExits() {
        return deepCopyExits(NorthEastExits);
    }

    public static Exit[] getNorthWestExits() {
        return deepCopyExits(NorthWestExits);
    }

    public static Exit[] getSouthEastExits() {
        return deepCopyExits(SouthEastExits);
    }

    public static Exit[] getSouthWestExits() {
        return deepCopyExits(SouthWestExits);
    }
}
