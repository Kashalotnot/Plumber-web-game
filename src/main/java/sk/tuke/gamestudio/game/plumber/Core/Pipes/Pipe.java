package sk.tuke.gamestudio.game.plumber.Core.Pipes;

abstract public class Pipe {
   private final Exit[] exits;
   private  boolean isInCorrectPath;
   public Pipe(Exit[] exits){
        isInCorrectPath = false;
       this.exits = exits;
   }


    public Exit[] getExits() {
        return exits;
    }
    public void rotate(){
        for (Exit exit : exits) {
            exit.setDirection(exit.getDirection().rotate());
        }
        /// If the first exit's direction number is greater than the second one's, swap them
        if(exits[0].getDirection().getNumber() > exits[1].getDirection().getNumber()){
            Exit temp = exits[0];
            exits[0] = exits[1];
            exits[1] = temp;

        }
    }

    public boolean isInCorrectPath() {
        return isInCorrectPath;
    }

    public void setInCorrectPath(boolean inCorrectPath) {
        isInCorrectPath = inCorrectPath;
    }
}
