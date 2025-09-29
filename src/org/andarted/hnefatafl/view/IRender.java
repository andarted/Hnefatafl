package org.andarted.hnefatafl.view;

import java.awt.Graphics2D;
import java.awt.Point;

interface IRender {
	
	void renderCell(Graphics2D g, int row, int col, int cellSize, SquareAppearance square, PieceAppearance piece, boolean selected);
	Point screenToGrid(int screenX, int screenY, int cellSize);
	
	/*
	void setSquare(Graphics2D g, int row, int col, SquareAppearance square);
	void setPiece(Graphics2D g, int row, int col, PieceAppearance piece);
	void setBoardBorder();
	void showMouseHoverIndicator(int row, int col);
	void clearMouseHoverIndicator();
	*/
}