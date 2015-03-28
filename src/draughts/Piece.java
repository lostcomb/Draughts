package draughts;

/**
 * A class to represent a Piece in the game.
 */

public class Piece {

    private final Colour colour;
    private int x;
    private int y;
    private boolean king;
    
    /**
     * Constructs a new Piece object.
     *
     * @param colour the Colour of the Piece.
     * @param x the x coordinate of the Piece.
     * @param y the y coordinate of the Piece.
     */
    public Piece(Colour colour, int x, int y) {
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.king = false;
    }
    
    /**
     * Returns the Colour of the Piece.
     *
     * @return the Colour of the Piece.
     */
    public Colour getColour() {
        return colour;
    }
    
    /**
     * Returns the x coordinate of the Piece.
     *
     * @return the x coordinate of the Piece.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y coordinate of the Piece.
     *
     * @return the y coordinate of the Piece.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Sets the x coordinate of the Piece.
     *
     * @param x the new x coordinate of the Piece.
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Sets the y coordinate of the Piece.
     *
     * @param y the new y coordinate of the Piece.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returns true if the Piece is a king.
     *
     * @return true if the Piece is a king.
     */
    public boolean isKing() {
        return king;
    }
    
    /**
     * Sets whether the Piece is a king.
     *
     * @param king the boolean representing whether
     * the Piece is a king.
     */
    public void setKing(boolean king) {
        this.king = king;
    }
    
    /**
     * Returns a String representing a Piece.
     *
     * @return a String representing a Piece.
     */
    @Override
    public String toString() {
        return "[" + colour.toString() + ", " + x + ", " + y + ", " + king + "]";
    }
    
}