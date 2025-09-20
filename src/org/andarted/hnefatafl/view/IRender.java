package org.andarted.hnefatafl.view;

import java.awt.Graphics2D;
import java.awt.Point;

interface IRender {
	
	void renderCell(Graphics2D g, int row, int col, int cellSize, SquareType square, PieceType piece, boolean selected);
	void setSquare(Graphics2D g, int row, int col, SquareType square);
	void setPiece(Graphics2D g, int row, int col, PieceType piece);
	void setBoardBorder();
	Point screenToGrid(int screenX, int screenY, int cellSize);

}