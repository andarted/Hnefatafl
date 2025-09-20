package org.andarted.hnefatafl.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

class RendererSprites implements IRender{
	
	private BufferedImage spriteSheet;
	private final int tileWidth;
	private final int tileHeight;
	
	private final static String DEFAULT_STRITESHEET_PATH = "/sprites/spriteSheet00.png"; 
	
	// private Map <SquareType, Point> tileCoordOnSpriteSheet;
	
	public RendererSprites(String sheetResourcePath, int tileWidth, int tileHeight) throws IOException {
		System.out.println("Pfad: " + getClass().getResource("/sprites/spriteSheet00.png"));
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		try(InputStream is = getClass().getResourceAsStream(DEFAULT_STRITESHEET_PATH)){
			if (is == null) {
				throw new IOException("SpriteSheet nicht gefunden: " + DEFAULT_STRITESHEET_PATH);
			}
		this.spriteSheet = ImageIO.read(is);
		}
		
	}

	@Override
	public void renderCell(Graphics2D g, int row, int col, int cellSize, SquareType square, PieceType piece,
			boolean highlight) {
		// koordinaten am board
		int x = col*cellSize;
		int y = row*cellSize;
		
		// koordinaten im sprite sheet
		int sy = square.getSpriteSheetPosX()*tileWidth;
		int sx = square.getSpriteSheetPosY()*tileHeight;
		
		// draw square
		g.drawImage(spriteSheet, x, y, x + cellSize, y + cellSize, sx, sy, sx + tileWidth, sy + tileHeight, null);
		
		// draw Piece
		if (piece != null && piece != PieceType.NOBODY) {
			int py = piece.getSpriteSheetPosX()*tileWidth;
			int px = piece.getSpriteSheetPosY()*tileHeight;
			g.drawImage(spriteSheet, x, y, x + cellSize, y + cellSize, px, py, px + tileWidth, py + tileHeight, null);
			System.out.println(row+","+col+" -- SQUARE|PIECE: "+square+" ("+sx+"/"+sy+") | "+piece+" ("+px+"/"+py+")");
		}
		
		// highlight
		if (highlight) {
			g.setColor(new Color(255, 255, 0, 80));
			g.fillRect(x, y, cellSize, cellSize);
		}
		
	}

	@Override
	public void setSquare(Graphics2D g, int row, int col, SquareType square) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPiece(Graphics2D g, int row, int col, PieceType piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBoardBorder() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Point screenToGrid(int screenX, int screenY, int cellSize) {
		int col = screenX / cellSize;
		int row = screenY / cellSize;
		return new Point (col, row);
	}

}
