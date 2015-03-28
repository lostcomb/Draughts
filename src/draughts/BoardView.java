package draughts;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

/**
 * A view to display the board and the pieces.
 */

public class BoardView extends JPanel implements MouseListener {
    
    private static final int maxY = 7;
    private static final int minY = 0;
    private static final int maxX = 7;
    private static final int minX = 0;
    private int border = 35;
    private int squareSize = 50;
    private int boardWidth = 8 * squareSize;
    private int boardHeight = 8 * squareSize;
    private BufferedImage king;
    private JLabel label;
    private Set<Piece> pieces;
    private ActionListener listener;
    private Point selected = new Point(-1, -1);
    
    /**
     * Constructs a new BoardView object.
     */
    public BoardView() {
        try {
            setPreferredSize(new Dimension(boardWidth + (2 * border), boardHeight + (2 * border)));
            pieces = new HashSet<Piece>();
            king = ImageIO.read(this.getClass().getResource("/king.png"));
            label = new JLabel("");
            add(label);
            addMouseListener(this);
        } catch (Exception e) {
            System.err.println("Error reading king file.");
            System.exit(1);
        }
    }
    
    /**
     * Draws the board and pieces.
     *
     * @param g0 the Graphics object to draw to.
     */
    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, boardWidth + (2 * border), boardHeight + (2 * border));
        g.setColor(Color.green.darker().darker());
        int inner = border - 2;
        g.fillRect(inner, inner, (boardWidth + 4), (boardHeight + 4));
        boolean white = true;
        for (int i = 0, j = border; i <= maxX; i++, j += squareSize) {
            for (int k = 0, l = border; k <= maxY; k++, l += squareSize) {
                if (white) g.setColor(Color.white);
                else g.setColor(Color.green.darker().darker());
                g.fillRect(j, l, squareSize, squareSize);
                white = !white;
                drawPiece(i, k, j, l, g);
            }
            white = !white;
        }
    }
    
    // Draws a Piece.
    // @param x the x coordinate of the Piece to be drawn.
    // @param y the y coordinate of the Piece to be drawn.
    // @param dX the x coordinate of the upper left corner of
    // the square that will contain the Piece.
    // @param dY the y coordinate of the upper left corner of
    // the square that will contain the Piece.
    // @param g the Graphics2D object to be drawn to.
    private void drawPiece(int x, int y, int dX, int dY, Graphics2D g) {
        Piece piece = getPiece(x, y);
        if (piece != null) {
            g.setColor(getColor(piece.getColour(), x, y));
            g.fillOval(dX + 5, dY + 5, squareSize - 10, squareSize - 10);
            if (piece.isKing()) g.drawImage(king, dX, dY, null);
        }
    }
    
    // Returns the correct Color for the Piece.
    // @param player the Colour of the current player.
    // @param x the x coordinate of the Piece.
    // @param y the y coordinate of the Piece.
    // @return the correct Color for the Piece.
    private Color getColor(Colour player, int x, int y) {
        int alpha = 255;
        if (x == (int) selected.getX() && y == (int) selected.getY()) alpha = 200;
        if (player.equals(Colour.Red)) return new Color(204, 0, 0, alpha);
        else return new Color(255, 250, 250, alpha);
    }
    
    // Returns the Piece with the specified coordinates.
    // @param x the x coordinate of the Piece.
    // @param y the y coordinate of the Piece.
    // @return the Piece with the specified coordinates.
    private Piece getPiece(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) return piece;
        }
        return null;
    }
    
    /**
     * Sets the alpha of the selected Piece.
     *
     * @param x the x coordinate of the selected Piece.
     * @param y the y coordinate of the selected Piece.
     */
    public void select(int x, int y) {
        selected = new Point(x, y);
        repaint();
    }
    
    /**
     * Updates the Set of Pieces.
     *
     * @param pieces the new Set of Pieces.
     */
    public void update(Set<Piece> pieces) {
        this.pieces = pieces;
        repaint();
    }
    
    /**
     * Sets the text of the JLabel in the view.
     *
     * @param message the message to be displayed in the JLabel.
     */
    public void setText(String message) {
        label.setText(message);
    }
    
    /**
     * Adds the specified ActionListener to receive when the user clicks on a square.
     * If listener listener is null, no action is performed.
     * 
     * @param listener the listener to be added to the view.
     */
    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }
    
    /**
     * Sends an ActionEvent to the specified ActionListener when the user clicks on a square.
     * 
     * @param e the MouseEvent containing the location of the click.
     */
    public void mouseClicked(MouseEvent e) {
        if (35 <= e.getX() && e.getX() < 435 && 35 <= e.getY() && e.getY() < 435) {
            int x = (int) Math.ceil(((e.getX() - 35) * 2) / 100);
            int y = (int) Math.ceil(((e.getY() - 35) * 2) / 100);
            if (listener != null) {
                listener.actionPerformed(new ActionEvent(new Point(x, y), 0, "click"));
            }
        }
    }
    
    /**
     * Unused method from the MouseListener interface.
     *
     * @param e the MouseEvent sent when the mouse button is released.
     */
    public void mouseReleased(MouseEvent e) {}
    
    /**
     * Unused method from the MouseListener interface.
     *
     * @param e the MouseEvent sent when the mouse button is pressed.
     */
    public void mousePressed(MouseEvent e) {}
    
    /**
     * Unused method from the MouseListener interface.
     *
     * @param e the MouseEvent sent when the mouse cursor enters the view.
     */
    public void mouseEntered(MouseEvent e) {}
    
    /**
     * Unused method from the MouseListener interface.
     *
     * @param e the MouseEvent sent when the mouse cursor leaves the view.
     */
    public void mouseExited(MouseEvent e) {}
    
}