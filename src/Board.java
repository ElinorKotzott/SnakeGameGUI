import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {


    private int height;
    private int width;
    private int moveSpeed;
    private int numberOfComponents = 8;
    private List<SnakeComponent> snakeComponentsList = new ArrayList<>();
    private Game game;
    private int moveDirection = KeyEvent.VK_LEFT;
    int rectHeight = 10;
    int rectWidth = 10;
    private Timer movementTimer;
    private int delay;
    boolean swapEndGameColor;
    int endGameColorSwapCounter;


    public Board(int height, int width, int speed) {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        this.height = height;
        this.width = width;
        this.moveSpeed = speed;
        game = new Game(this::repaint, height, width, moveSpeed);

        //

        for (int i = 0; i < numberOfComponents; i++) {
            snakeComponentsList.add(i, new SnakeComponent());
            if (i == 0) {
                snakeComponentsList.get(i).setHead(true);
            }
            snakeComponentsList.get(i).setX(width * 6 / 10 + i * (rectWidth + 4));
            snakeComponentsList.get(i).setY(height / 2 - rectHeight / 2);
        }


        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        if (moveDirection == KeyEvent.VK_RIGHT) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (moveDirection == KeyEvent.VK_LEFT) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        if (moveDirection == KeyEvent.VK_DOWN) {
                            return;
                        }
                        moveDirection = KeyEvent.VK_UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (moveDirection == KeyEvent.VK_UP) {
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
            game.moveSnake(moveDirection, snakeComponentsList, rectWidth, rectHeight);
            repaint();
        });
        movementTimer.start();
    }


    // drei verschiedene Speed Optionen: 1, 2, 3 - je nachdem, was gewählt wird, wird die Bewegungsgeschwindigkeit der Snake angepasst.


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



        if (game.isGameOver()) {
            if (endGameColorSwapCounter == 10) {
                System.exit(0);
            }
            if(swapEndGameColor) {
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
                g.fillOval(snakeComponentsList.get(i).getX(), snakeComponentsList.get(i).getY(), rectWidth, rectHeight);
            } else {
                g.fillRect(snakeComponentsList.get(i).getX(), snakeComponentsList.get(i).getY(), rectWidth, rectHeight);
            }



    }


}
