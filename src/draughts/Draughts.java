package draughts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * A class to start a new game of English Draughts.
 * Rules for the game: <a href="http://en.wikipedia.org/wiki/English_draughts">http://en.wikipedia.org/wiki/English_draughts</a>
 */

public class Draughts implements Player, ActionListener {
    
    private BoardView board;
    private DraughtsModel model;
    private InputPDA pda;
    private BlockingQueue<Integer> queue;
    
    /**
     * Called to start the game.
     *
     * @param args the arguments provided by the user.
     */
    public static void main(String[] args) {
        try {
            Draughts draughts = new Draughts();
            SwingUtilities.invokeAndWait(draughts::run);
            draughts.startGame();
        } catch (Exception e) {
            System.err.println("Error starting game.");
            System.exit(1);
        }
    }
    
    /**
     * Create the window and draws the board.
     */
    public void run() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new BoardView();
        board.setActionListener(this);
        window.add(board);
        window.setTitle("Draughts");
        window.setResizable(false);
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }
    
    /**
     * Called when the user clicks on a square on the board.
     *
     * @param e the ActionEvent containing the coordinates 
     * of the square that was clicked.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click")) {
            Point point = (Point) e.getSource();
            try {
                queue.put(Integer.valueOf((int) point.getX()));
                queue.put(Integer.valueOf((int) point.getY()));
            } catch (InterruptedException exc) {
                System.err.println("Interrupted from putting something on the queue.");
            }
        }
    }
    
    /**
     * Starts a new game of English Draughts.
     */
    public void startGame() {
        queue = new ArrayBlockingQueue<Integer>(1024);
        model = new DraughtsModel(this);
        pda = new InputPDA(model);
        board.update(model.getPieces());
        board.setText(model.getCurrentPlayer().toString() + " Players turn.");
        model.start();
        board.update(model.getPieces());
        board.setText(model.getWinningMessage());
    }
    
    /**
     * Returns the Move selected by the user.
     *
     * @param validMoves the Set of valid Moves the user could take.
     * @return the Move selected by the user.
     */
    public Move notify(Set<Move> validMoves) {
        Colour currentPlayer = model.getCurrentPlayer();
        board.update(model.getPieces());
        board.setText(currentPlayer.toString() + " Players turn.");
        Move move = null;
        while(true) {
            try {
                Integer x = queue.take();
                Integer y = queue.take();
                pda.transition(currentPlayer, x, y);
                board.select(x, y);
                if (pda.isAccepted()) {
                    move = pda.createMove(currentPlayer);
                    if (contains(validMoves, move)) break;
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted from taking something from the queue.");
            }
        }
        pda.reset();
        board.select(-1, -1);
        return move;
    }
    
    // Returns true if the Set contains the Move.
    // @param moves the Set containing the Moves.
    // @param move the Move to be checked.
    // @return true if the Set contains the Move.
    private boolean contains(Set<Move> moves, Move move) {
        for (Move m : moves) {
            if (m.toString().equals(move.toString())) return true;
        }
        return false;
    }
    
}