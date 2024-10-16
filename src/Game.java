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
    private boolean gameOver;
    private TravelDirection travelDirection;

    public boolean isGameOver() {
        return gameOver;
    }

    public Game(Runnable runnable, int height, int width, int moveSpeed, TravelDirection travelDirection) {
        this.runnable = runnable;
        this.height = height;
        this.width = width;
        this.moveSpeed = moveSpeed;
        this.travelDirection = travelDirection;
    }

    public void moveSnake(int moveDirection, List<SnakeComponent> snakeComponentsList, int rectSizeInPixels) {
        if (gameOver) {
            return;
        }

        // put that into a method and also: rectWidth or height + 4 should be one singular variable
        // put runnable.run to the end

        if (moveDirection == LEFT_ARROW && snakeComponentsList.get(0).getCoord().getX() > -1) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getCoord().getX(), s.getCoord().getY()));
            }
            snakeComponentsList.get(0).getCoord().setX(snakeComponentsList.get(0).getCoord().getX() - (rectSizeInPixels));
            runnable.run();
            travelDirection.setTravelDirection(Direction.LEFT);
            if (snakeComponentsList.get(0).getCoord().getX() <= -1) {
                gameOver = true;
            }

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                if (snakeComponentsList.get(i).checkIfSnakeEatsItself(snakeComponentsList.get(0))) {
                    gameOver = true;
                }
            }
        }


        if (moveDirection == UP_ARROW && snakeComponentsList.get(0).getCoord().getY() > -1) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getCoord().getX(), s.getCoord().getY()));
            }
            snakeComponentsList.get(0).getCoord().setY(snakeComponentsList.get(0).getCoord().getY() - rectSizeInPixels);
            runnable.run();
            travelDirection.setTravelDirection(Direction.UP);
            if (snakeComponentsList.get(0).getCoord().getY() <= -1) {
                gameOver = true;
            }

            for (int i = 1; i < snakeComponentsList.size(); i++) {
                if (snakeComponentsList.get(i).checkIfSnakeEatsItself(snakeComponentsList.get(0))) {
                    gameOver = true;
                }
            }
        }


        if (moveDirection == DOWN_ARROW && snakeComponentsList.get(0).getCoord().getY() < height) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getCoord().getX(), s.getCoord().getY()));
            }
            snakeComponentsList.get(0).getCoord().setY(snakeComponentsList.get(0).getCoord().getY() + rectSizeInPixels);
            runnable.run();
            travelDirection.setTravelDirection(Direction.DOWN);
            if (snakeComponentsList.get(0).getCoord().getY() >= height) {
                gameOver = true;
            }
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                if (snakeComponentsList.get(i).checkIfSnakeEatsItself(snakeComponentsList.get(0))) {
                    gameOver = true;
                }
            }
        }

        if (moveDirection == RIGHT_ARROW && snakeComponentsList.get(0).getCoord().getX() < width) {
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
                snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getCoord().getX(), s.getCoord().getY()));
            }
            snakeComponentsList.get(0).getCoord().setX(snakeComponentsList.get(0).getCoord().getX() + rectSizeInPixels);
            runnable.run();
            travelDirection.setTravelDirection(Direction.RIGHT);
            if (snakeComponentsList.get(0).getCoord().getX() >= width) {
                gameOver = true;
            }
            for (int i = 1; i < snakeComponentsList.size(); i++) {
                if (snakeComponentsList.get(i).checkIfSnakeEatsItself(snakeComponentsList.get(0))) {
                    gameOver = true;
                }
            }
        }


    }


}


// wenn der snake head Koordinaten erreicht, die außerhalb des Spielfelds liegen, also: negative in x oder y- Richtung oder
// größere als length und width des Spielfeldes, dann ist das Spiel verloren - clear the list, methode für jpanel, die Strings annimmt - game over wird angezeigt
// spieler verliegt auch, wenn die snake sich selbst berührt - also wenn der snake head dieselbe position hat wie eines der Körperteile. wenn
// die position gleich ist, dann trotzdem noch repaint und dann das spiel beenden