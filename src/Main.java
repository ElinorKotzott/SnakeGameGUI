import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //SudokuMap [][] map = new SudokuMap [9][9];


        JFrame jframe = new JFrame("EllysSnakeGame");
        Board board = new Board(300, 400, 3);


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(board);
        jframe.pack();
        jframe.setVisible(true);


    }
}
