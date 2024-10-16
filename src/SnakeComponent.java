public class SnakeComponent {
    private boolean isHead;
    private int x;
    private int y;

    public SnakeComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SnakeComponent() {

    }

    public boolean checkIfSnakeEatsItself(SnakeComponent s) {
        return (s.getX() == x && s.getY() == y);
    }

    public boolean checkIfAppleIsEaten(int appleX, int appleY) {
return (appleX == x && appleY == y);
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
