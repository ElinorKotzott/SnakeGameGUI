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
    private static final int W = 87;
    private static final int D = 68;
    private static final int A = 65;
    private static final int S = 83;


    public Game(Runnable runnable, int height, int width, int moveSpeed) {
        this.runnable = runnable;
        this.height = height;
        this.width = width;
        this.moveSpeed = moveSpeed;
    }

    public void moveSnake(int moveDirection, List<SnakeComponent> snakeComponentsList, int rectWidth, int rectHeight) {


        if (moveDirection == LEFT_ARROW) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setX(snakeComponentsList.get(0).getX() - (rectWidth + 4));
            runnable.run();
        }

        if (moveDirection == UP_ARROW) {

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setY(snakeComponentsList.get(0).getY() - (rectHeight + 4));
            runnable.run();
        }

// up: head moves up - y gets increased, x stays the same
        // second element gets old snake head values
        // it'S exactly the same as before: last element inherits values from the element before -  new instances of snakecomponent for each index are made
        // but: head gets a new value: Y is now - (rectHeight+4)

        if (moveDirection == DOWN_ARROW) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setY(snakeComponentsList.get(0).getY() + rectHeight + 4);
            runnable.run();
        }
        if (moveDirection == RIGHT_ARROW) {

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getX(), s.getY()));
            }
            snakeComponentsList.get(0).setX(snakeComponentsList.get(0).getX() + rectWidth + 4);
            runnable.run();
        }


        if (snakeComponentsList.get())

    }



}



// wenn der snake head Koordinaten erreicht, die außerhalb des Spielfelds liegen, also: negative in x oder y- Richtung oder
// größerer als length und width des Spielfeldes, dann ist das Spiel verloren - clear the list, methode für jpanel, die Strings annimmt - game over wird angezeigt