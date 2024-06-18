package sk.tuke.gamestudio.game.plumber.Core.Pipes;

public class Exit {
    private boolean isConnected;
    private PipeDirection direction;
    public Exit(PipeDirection direction){
        isConnected = false;
        this.direction = direction;
    }

    public PipeDirection getDirection() {
        return direction;
    }

    public void setDirection(PipeDirection direction) {
        this.direction = direction;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect() {
        isConnected = true;
    }
}
