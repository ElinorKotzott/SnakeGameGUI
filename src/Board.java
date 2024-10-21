import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board extends JPanel {


    private int height;
    private int width;
    private int moveSpeed;
    private int numberOfComponents = 8;
    private List<SnakeComponent> snakeComponentsList = new ArrayList<>();
    private Game game;
    private int moveDirection = KeyEvent.VK_LEFT;
    int rectSizeInPixels = 10;
    private Timer movementTimer;
    private int delay;
    boolean swapEndGameColor;
    int endGameColorSwapCounter;
    private TravelDirection travelDirection = new TravelDirection(Direction.LEFT);
    private int appleX;
    private int appleY;
    private boolean noAppleThere = true;


    public Board(int height, int width, int speed) {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        this.height = height;
        this.width = width;
        this.moveSpeed = speed;
        game = new Game(this::repaint, height, width, moveSpeed, travelDirection);


        for (int i = 0; i < numberOfComponents; i++) {
            snakeComponentsList.add(i, new SnakeComponent(50 + i * 10, 50));
            if (i == 0) {
                snakeComponentsList.get(i).setHead(true);
            }
        }


        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        // is moveDirection in the if even needed
                        if (moveDirection == KeyEvent.VK_RIGHT || Objects.equals(travelDirection.getTravelDirection(), Direction.RIGHT)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (moveDirection == KeyEvent.VK_LEFT || Objects.equals(travelDirection.getTravelDirection(), Direction.LEFT)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        if (moveDirection == KeyEvent.VK_DOWN || Objects.equals(travelDirection.getTravelDirection(), Direction.DOWN)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (moveDirection == KeyEvent.VK_UP || Objects.equals(travelDirection.getTravelDirection(), Direction.UP)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_DOWN;
                        break;
                }

            }


        });

        if (moveSpeed == 1) {
            delay = 300;
        } else if (moveSpeed == 2) {
            delay = 200;
        } else {
            delay = 120;
        }

        movementTimer = new Timer(delay, e -> {
            game.moveSnake(moveDirection, snakeComponentsList, rectSizeInPixels);
            repaint();
        });
        movementTimer.start();
    }


    // drei verschiedene Speed Optionen: 1, 2, 3 - je nachdem, was gewählt wird, wird die Bewegungsgeschwindigkeit der Snake angepasst.


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (game.isGameOver()) {
            if (endGameColorSwapCounter == 16) {
                System.exit(0);
            }
            g.setColor(Color.gray);
            String str = "Game over";
            Font font = new Font("Arial", Font.BOLD, 140);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            int x = (width - metrics.stringWidth(str)) / 2;
            int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();
            g.drawString(str, x, y);

            if (swapEndGameColor) {
                g.setColor(Color.cyan);
            } else {
                g.setColor(Color.red);
            }
            swapEndGameColor = !swapEndGameColor;
            endGameColorSwapCounter++;
        } else {
            g.setColor(Color.green);
        }


        int i;

        for (i = 0; i < snakeComponentsList.size(); i++)
            if (snakeComponentsList.get(i).isHead()) {
                if (!game.isGameOver()) {
                    g.setColor(Color.yellow);
                }
                g.fillOval(snakeComponentsList.get(i).getCoord().getX(), snakeComponentsList.get(i).getCoord().getY(), rectSizeInPixels, rectSizeInPixels);
                if (snakeComponentsList.get(i).checkIfAppleIsEaten(appleX, appleY)) {
                    g.setColor(Color.green);
                    snakeComponentsList.add(new SnakeComponent(appleX, appleY));
                    if (snakeComponentsList.size() == width / 10 * height / 10) {
                        snakeComponentsList.clear();
                        g.setColor(Color.yellow);
                        String str = "Congrats, you won!";
                        Font font = new Font("Arial", Font.BOLD, 100);
                        g.setFont(font);
                        FontMetrics metrics = g.getFontMetrics(font);
                        int x = (width - metrics.stringWidth(str)) / 2;
                        int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();
                        g.drawString(str, x, y);
                    }
                    noAppleThere = true;

                }
                if (!game.isGameOver()) {
                    g.setColor(Color.green);
                }
            } else {
                g.fillOval(snakeComponentsList.get(i).getCoord().getX(), snakeComponentsList.get(i).getCoord().getY(), rectSizeInPixels, rectSizeInPixels);
            }

        if (noAppleThere) {
            createNewApple();
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, rectSizeInPixels, rectSizeInPixels);
            noAppleThere = false;
        } else {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, rectSizeInPixels, rectSizeInPixels);
        }


    }
// instead: create the appleCoordinatesList outside of this method. use a copy of the list to subtract
// the position of the snake when it eats an apple to determine apple spawn options

    public void createNewApple() {
        List<Coord> appleCoordinatesList = new ArrayList<>();
        for (int i = 0; i < width / 10; i++) {
            for (int j = 0; j < height / 10; j++) {
                appleCoordinatesList.add(new Coord(i * 10, j * 10));
            }
        }
// alternative solution:
//
//        appleCoordinatesList.removeIf(a ->
//                snakeComponentsList.stream()
//                        .map(SnakeComponent::getCoord)
//                        .anyMatch(coord -> coord.equals(a))
//        );

        for (int i = 0; i < appleCoordinatesList.size(); i++) {
            for (int j = 0; j < snakeComponentsList.size(); j++) {
                if (appleCoordinatesList.get(i).equals(snakeComponentsList.get(j).getCoord())) {
                    appleCoordinatesList.remove(i);
                }
            }
        }

        appleX = appleCoordinatesList.get((int) ((Math.random() * (appleCoordinatesList.size())))).getX();
        appleY = appleCoordinatesList.get((int) ((Math.random() * (appleCoordinatesList.size())))).getY();

    }
}

// apples sometimes spawn on snake
// head turns green when snake eats an apple
// apples can spawn outside of map
// small map sizes are not possible
// game over message has to be scaled according to map size
