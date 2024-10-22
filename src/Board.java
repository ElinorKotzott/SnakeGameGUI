import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Board extends JPanel {


    private int height;
    private int width;
    private int numberOfComponents = 47;
    private List<SnakeComponent> snakeComponentsList = new ArrayList<>();
    private Game game;
    private int moveDirection = KeyEvent.VK_LEFT;
    int rectSizeInPixels = 10;
    private Timer movementTimer;
    boolean swapEndGameColor;
    int gameOverTickCounter;
    private TravelDirection travelDirection = new TravelDirection(Direction.LEFT);
    private int appleX;
    private int appleY;
    private boolean noAppleThere = true;
    private boolean gameWin;


    public Board(int height, int width) {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        this.height = height;
        this.width = width;
        game = new Game(this::repaint, height, width, travelDirection);


        for (int i = 0; i < numberOfComponents; i++) {
            snakeComponentsList.add(i, new SnakeComponent(width, 0));
            if (i == 0) {
                snakeComponentsList.get(i).setHead(true);
            }
        }


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        if (Objects.equals(travelDirection.getTravelDirection(), Direction.RIGHT)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (Objects.equals(travelDirection.getTravelDirection(), Direction.LEFT)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        if (Objects.equals(travelDirection.getTravelDirection(), Direction.DOWN)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Objects.equals(travelDirection.getTravelDirection(), Direction.UP)) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_DOWN;
                        break;
                }

            }


        });

        int delay = 200;
        movementTimer = new Timer(delay, e -> {
            game.moveSnake(moveDirection, snakeComponentsList, rectSizeInPixels, gameWin);
            repaint();
        });
        movementTimer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);

        if (game.isGameOver()) {
            if (gameOverTickCounter == 16) {
                System.exit(0);
            }
            paintLoserMessage(g);
            if (swapEndGameColor) {
                g.setColor(Color.cyan);
            } else {
                g.setColor(Color.red);
            }
            swapEndGameColor = !swapEndGameColor;
            gameOverTickCounter++;
        }

        if (gameWin) {
            if (gameOverTickCounter == 16) {
                System.exit(0);
            }
            paintWinnerMessage(g);
            gameOverTickCounter++;
            return;
        }

        for (int i = 0; i < snakeComponentsList.size(); i++)
            if (snakeComponentsList.get(i).isHead() && !game.isGameOver()) {
                g.setColor(Color.yellow);

                if (snakeComponentsList.get(i).checkIfAppleIsEaten(appleX, appleY)) {
                    snakeComponentsList.add(new SnakeComponent(snakeComponentsList.get(snakeComponentsList.size() - 1).getCoord().getX(), snakeComponentsList.get(snakeComponentsList.size() - 1).getCoord().getY()));
                    if (snakeComponentsList.size() == width / 10 * height / 10 - 1) {
                        snakeComponentsList.clear();
                        gameWin = true;
                        return;
                    }
                    noAppleThere = true;

                }
                g.fillOval(snakeComponentsList.get(i).getCoord().getX(), snakeComponentsList.get(i).getCoord().getY(), rectSizeInPixels, rectSizeInPixels);
                if (!game.isGameOver()) {
                    g.setColor(Color.green);
                }
            } else {
                g.fillOval(snakeComponentsList.get(i).getCoord().getX(), snakeComponentsList.get(i).getCoord().getY(), rectSizeInPixels, rectSizeInPixels);
            }

        if (!gameWin) {
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

    }

    public void paintWinnerMessage(Graphics g) {
        g.setColor(Color.white);
        String winnerMessage = "Congrats, you won!";
        Font font = new Font("Arial", Font.BOLD, width / 7);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (width - metrics.stringWidth(winnerMessage)) / 2;
        int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(winnerMessage, x, y);
    }

    public void paintLoserMessage(Graphics g) {
        g.setColor(Color.gray);
        String str = "Game over";
        Font font = new Font("Arial", Font.BOLD, width / 7);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (width - metrics.stringWidth(str)) / 2;
        int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(str, x, y);
    }

    public void createNewApple() {
        List<Coord> appleCoordinatesList = new ArrayList<>();
        for (int i = 0; i < width / 10; i++) {
            for (int j = 0; j < height / 10; j++) {
                appleCoordinatesList.add(new Coord(i * 10, j * 10));
            }
        }
        for (int i = 0; i < appleCoordinatesList.size(); i++) {
            for (int j = 0; j < snakeComponentsList.size(); j++) {
                if (appleCoordinatesList.get(i).equals(snakeComponentsList.get(j).getCoord())) {
                    appleCoordinatesList.remove(i);
                    i--;
                    break;
                }
            }
        }

        Random r = new Random();
        Coord randomCoord = appleCoordinatesList.get((r.nextInt(appleCoordinatesList.size())));
        appleX = randomCoord.getX();
        appleY = randomCoord.getY();
    }
}

