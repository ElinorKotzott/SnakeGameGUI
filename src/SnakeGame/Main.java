package SnakeGame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame jframe = new JFrame("EllysSnakeGame");
        Board board = new Board(50, 100);

        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.add(board);
        jframe.pack();
        jframe.setVisible(true);

    }
}
