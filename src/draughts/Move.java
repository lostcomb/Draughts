package draughts;

import java.awt.*;

/**
 * A class to represent a Move in the game.
 */

public class Move {
    
    /**
     * The Piece to be moved.
     */
    public final Piece piece;
    
    /**
     * The coordinates of the destination.
     */
    public final Point destination;
    
    /**
     * Constructs a new Move object.
     *
     * @param piece the Piece to be moved.
     * @param x the x coordinate of the destination.
     * @param y the y coordinate of the destination.
     */
    public Move(Piece piece, int x, int y) {
        this.piece = piece;
        destination = new Point(x, y);
    }
    
    /**
     * Returns a String representing a Move.
     *
     * @return a String representing a Move.
     */
    @Override
    public String toString() {
        return "Move: " + piece.toString() + " (" + destination.getX() + ", " + destination.getY() + ")";
    }
    
}