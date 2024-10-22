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

    public void moveSnake(int moveDirection, List<SnakeComponent> snakeComponentsList, int rectSizeInPixels, boolean gameWin) {
        if (gameOver || gameWin) {
            return;
        }

        if (moveDirection == LEFT_ARROW && snakeComponentsList.get(0).getCoord().getX() > -1) {
            moveSnakeComponents(snakeComponentsList, rectSizeInPixels, Direction.LEFT);
            if (snakeComponentsList.get(0).getCoord().getX() <= -1) {
                gameOver = true;
            }
        }


        if (moveDirection == UP_ARROW && snakeComponentsList.get(0).getCoord().getY() > -1) {
            moveSnakeComponents(snakeComponentsList, rectSizeInPixels, Direction.UP);
            if (snakeComponentsList.get(0).getCoord().getY() <= -1) {
                gameOver = true;
            }
        }


        if (moveDirection == DOWN_ARROW && snakeComponentsList.get(0).getCoord().getY() < height) {
            moveSnakeComponents(snakeComponentsList, rectSizeInPixels, Direction.DOWN);
            if (snakeComponentsList.get(0).getCoord().getY() >= height) {
                gameOver = true;
            }
        }

        if (moveDirection == RIGHT_ARROW && snakeComponentsList.get(0).getCoord().getX() < width) {
            moveSnakeComponents(snakeComponentsList, rectSizeInPixels, Direction.RIGHT);
            if (snakeComponentsList.get(0).getCoord().getX() >= width) {
                gameOver = true;
            }
        }
    }

    private void moveSnakeComponents(List<SnakeComponent> snakeComponentsList, int rectSizeInPixels, Direction d) {
        for (int i = 1; i < snakeComponentsList.size(); i++) {
            SnakeComponent s = snakeComponentsList.get(snakeComponentsList.size() - (i + 1));
            snakeComponentsList.set(snakeComponentsList.size() - (i), new SnakeComponent(s.getCoord().getX(), s.getCoord().getY()));
        }
        moveSnakeHead(snakeComponentsList, d, rectSizeInPixels);


        // should components two, three and four even be checked? They can't ever be eaten anyway
        travelDirection.setTravelDirection(d);
        for (int i = 1; i < snakeComponentsList.size(); i++) {
            if (snakeComponentsList.get(i).checkIfSnakeEatsItself(snakeComponentsList.get(0))) {
                gameOver = true;
            }
        }
        runnable.run();
    }

    private void moveSnakeHead(List<SnakeComponent> snakeComponentsList, Direction d, int rectSizeInPixels) {
        if (Direction.LEFT.equals(d)) {
            snakeComponentsList.get(0).getCoord().setX(snakeComponentsList.get(0).getCoord().getX() - (rectSizeInPixels));
        } else if (Direction.UP.equals(d)) {
            snakeComponentsList.get(0).getCoord().setY(snakeComponentsList.get(0).getCoord().getY() - rectSizeInPixels);
        } else if (Direction.DOWN.equals(d)) {
            snakeComponentsList.get(0).getCoord().setY(snakeComponentsList.get(0).getCoord().getY() + rectSizeInPixels);
        } else if (Direction.RIGHT.equals(d)) {
            snakeComponentsList.get(0).getCoord().setX(snakeComponentsList.get(0).getCoord().getX() + rectSizeInPixels);
        }
    }


}

