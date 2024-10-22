package SnakeGame;

public class TravelDirection {
   private Direction travelDirection;

   public TravelDirection (Direction travelDirection) {
       this.travelDirection = travelDirection;
   }

    public Direction getTravelDirection() {
        return travelDirection;
    }

    public void setTravelDirection(Direction travelDirection) {
        this.travelDirection = travelDirection;
    }
}
