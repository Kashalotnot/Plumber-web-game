package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public enum PipeDirection {
    NORTH(1),
    EAST(2),
    SOUTH(3),
    WEST(4);

    private final int number;
    PipeDirection(int number){
        this.number = number;
    }
    public PipeDirection rotate(){
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            default -> NORTH;
        };
    }

    public int getNumber() {
        return number;
    }
}
