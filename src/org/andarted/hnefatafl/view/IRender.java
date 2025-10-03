package org.andarted.hnefatafl.view;

import java.awt.Graphics2D;
import java.awt.Point;

interface IRender {
	
	void renderCell(Graphics2D g, int row, int col, int cellSize, SquareAppearance square, PieceAppearance piece, boolean reachHighlight, boolean mouseHoverHighlight);
	Point screenToGrid(int screenX, int screenY, int cellSize);
	
}