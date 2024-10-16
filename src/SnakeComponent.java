public class SnakeComponent {
    private boolean isHead;
    private Coord coord;


    public SnakeComponent(int x, int y) {
        coord = new Coord(x, y);
    }

    public boolean checkIfSnakeEatsItself(SnakeComponent s) {
        return (s.getCoord().getX() == this.getCoord().getX() && s.getCoord().getY() == this.getCoord().getY());
    }

    public boolean checkIfAppleIsEaten(int appleX, int appleY) {
        return (appleX == this.getCoord().getX() && appleY == this.getCoord().getY());
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public Coord getCoord() {
        return coord;
    }
}
