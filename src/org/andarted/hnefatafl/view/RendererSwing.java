package org.andarted.hnefatafl.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

class RendererSwing implements IRender {

    @Override
    public void renderCell(Graphics2D g, int row, int col, int cellSize, SquareType square, PieceType piece, boolean highlight) {
        int x = col * cellSize;
        int y = row * cellSize;

        // Hintergrund
        g.setColor(square.getColor());
        g.fillRect(x, y, cellSize, cellSize);

        // Optional: Selektion hervorheben
        if (highlight) {
            g.setColor(new Color(255,255,0, 80));
            g.fillRect(x, y, cellSize, cellSize);
        }

        // Gitterlinien
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize);

        // Spielfigur als Kreis
        if (piece != null && piece != PieceType.NOBODY) {
            g.setColor(piece.getColor());
            g.fillOval(x + cellSize/4, y + cellSize/4, cellSize/2, cellSize/2);

            // Optional: Zeichen für Figurentyp
            g.setColor(Color.BLACK);
            String symbol = piece.getSymbol();
            g.drawString(symbol, x + cellSize/2 - 4, y + cellSize/2 + 5);
        }
    }


    @Override
    public void setSquare(Graphics2D g, int row, int col, SquareType square) {}
    @Override
    public void setPiece(Graphics2D g, int row, int col, PieceType piece) {}
    @Override
    public void setBoardBorder() {}
    // @Override
    // public void showHighlights() {}

	@Override
	public Point screenToGrid(int screenX, int screenY, int cellSize) {
		int col = screenX / cellSize;
		int row = screenY / cellSize;
		return new Point (col, row);
	}
}