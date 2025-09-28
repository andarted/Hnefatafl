package org.andarted.hnefatafl.view;

// import org.andarted.hnefatafl.common.SquareType;
// import org.andarted.hnefatafl.common.PieceType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.andarted.hnefatafl.model.SquareType;

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
	public void renderCell(Graphics2D g, int row, int col, int cellSize, SquareAppearance square, PieceAppearance piece,
			boolean highlight) {
		// koordinaten am board
		int x = col*tileWidth;
		int y = row*tileWidth;
		
		// koordinaten im sprite sheet
		Point spritePos = square.spritePos();
		int sPosY = spritePos.x * tileWidth; 
		int sPosX = spritePos.y * tileHeight;
	
		
		// draw square
		g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, sPosX, sPosY, sPosX + tileWidth, sPosY + tileHeight, null);
		
		// highlight
		if (highlight) {
			SquareAppearance mouseHover = SquareTypeAppearanceMapper.getAppearance(SquareType.MOUSE_HOVER);
			int syH = mouseHover.spritePos.x * tileWidth;
			int sxH = mouseHover.spritePos.x * tileWidth;
			g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, sxH, syH, sxH + tileWidth, syH + tileHeight, null);
			/*
			g.setColor(new Color(255, 255, 0, 80));
			g.fillRect(x, y, cellSize, cellSize);
			*/
		}
		
		// draw Piece
		if (piece != null) {
			// SquareAppearance mouseHover = SquareTypeAppearanceMapper.getAppearance(SquareType.MOUSE_HOVER);
			int py = piece.spritePos().x * tileWidth;
			int px = piece.spritePos().y * tileHeight;
			g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, px, py, px + tileWidth, py + tileHeight, null);
			// System.out.println(row+","+col+" -- SQUARE|PIECE: "+square+" ("+sx+"/"+sy+") | "+piece+" ("+px+"/"+py+")");
		}
	}


	@Override
	public void setBoardBorder() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Point screenToGrid(int screenX, int screenY, int cellSize) {
		int col = screenX / tileWidth;
		int row = screenY / tileWidth;
		return new Point (col, row);
	}

	@Override
	public void showMouseHoverIndicator(int row, int col) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearMouseHoverIndicator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSquare(Graphics2D g, int row, int col, SquareAppearance square) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPiece(Graphics2D g, int row, int col, PieceAppearance piece) {
		// TODO Auto-generated method stub
		
	}

}
