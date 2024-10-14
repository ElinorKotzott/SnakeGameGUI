import java.util.List;

public class Game {
    private Runnable runnable;
    private int height;
    private int width;
    private int moveSpeed;
    private static final int LEFT_ARROW = 37;
    private static final int RIGHT_ARROW = 39;
    private static final int UP_ARROW = 38;
    private static final int DOWN_ARROW = 40;


    public Game(Runnable runnable, int height, int width, int moveSpeed) {
        this.runnable = runnable;
        this.height = height;
        this.width = width;
        this.moveSpeed = moveSpeed;
    }

    public void moveSnake(int moveDirection, List<SnakeComponent> snakeComponentsList, int rectWidth, int rectHeight) {


        if (moveDirection == LEFT_ARROW && snakeComponentsList.get(0).getX() > -1) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setX(snakeComponentsList.get(0).getX() - (rectWidth + 4));
            runnable.run();
        }


        if (moveDirection == UP_ARROW && snakeComponentsList.get(0).getY() > -1) {

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setY(snakeComponentsList.get(0).getY() - (rectHeight + 4));
            runnable.run();
        }


        if (moveDirection == DOWN_ARROW && snakeComponentsList.get(0).getY() < height-1) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setY(snakeComponentsList.get(0).getY() + rectHeight + 4);
            runnable.run();
        }
        if (moveDirection == RIGHT_ARROW && snakeComponentsList.get(0).getX() < width ) {

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setX(snakeComponentsList.get(0).getX() + rectWidth + 4);
            runnable.run();
        }


    }


}


// wenn der snake head Koordinaten erreicht, die außerhalb des Spielfelds liegen, also: negative in x oder y- Richtung oder
// größere als length und width des Spielfeldes, dann ist das Spiel verloren - clear the list, methode für jpanel, die Strings annimmt - game over wird angezeigt
// spieler verliegt auch, wenn die snake sich selbst berührt - also wenn der snake head dieselbe position hat wie eines der Körperteile. wenn
// die position gleich ist, dann trotzdem noch repaint und dann das spiel beenden