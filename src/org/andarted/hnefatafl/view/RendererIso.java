package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.common.SquareType;
import org.andarted.hnefatafl.common.PieceType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;


public class RendererIso implements IRender{
	
	private BufferedImage spriteSheet;
	// private GameBoard gameBoard;
	private final int tileWidth;
	private final int tileHeight;
	private final int tileWidthHalf;
	private final int tileHeightHalf;
	private final int screenOffsetX;
	private final int screenOffsetY;
	
	private final static String DEFAULT_STRITESHEET_PATH = "/sprites/spriteSheetIso00_64.png";
	
	/*
	private final int[] mouseListenerX = new int[] {
			7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0, 0,
			0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7
	};
	private final int[] mouseListenerY = new int[] {
			0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0
	};
	*/
	
	// private Map <SquareType, Point> tileCoordOnSpriteSheet;
	
	public RendererIso(String sheetResourcePath, int tileWidth, int tileHeight) throws IOException {
		System.out.println("Pfad: " + getClass().getResource("/sprites/spriteSheetIso00_64.png"));
		// this.gameBoard = gameBoard;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileWidthHalf = tileWidth / 2;
		this.tileHeightHalf = tileHeight / 2;
		
		// int boardSize = gameBoard.getBoardSize(); // <- NullPointerException 
		
		// Falls das Board noch nachgerichtet werden muss
		this.screenOffsetX = 8 * tileWidth; 
		this.screenOffsetY = tileHeight / 2;
		
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
		int isoX = (col - row) * (tileWidth / 2);
		int isoY = (col + row) * (tileHeight / 4);
		
		/*
		// koordinaten am board
		int isoX = (col+1 - row+1) * (tileWidth / 2); // col*cellSize;
		int isoY = (col+1 + row+1) * (tileHeight / 4); // row*cellSize;
		*/
		
		// Pieces vertikal verschieben (damit sie sauber auf die squares passen)	
		// int pieceOffsetY = y - tileHeightHalf;
		
		int x = isoX + screenOffsetX;
		int y = isoY + screenOffsetY;
		
		// koordinaten im sprite sheet
		int sy = square.getSpriteSheetPosX()*tileWidth;
		int sx = square.getSpriteSheetPosY()*tileHeight;
		
		// draw square
		g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileHeight, sx, sy, sx + tileWidth, sy + tileHeight, null);
		
		// highlight
		if (highlight) {
			int syH = SquareType.HIGHLIGHTED.getSpriteSheetPosX()*tileWidth;
			int sxH = SquareType.HIGHLIGHTED.getSpriteSheetPosY()*tileWidth;
			g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, sxH, syH, sxH + tileWidth, syH + tileHeight, null);
			/*
			g.setColor(new Color(255, 255, 0, 80));
			g.fillRect(x, y, cellSize, cellSize);
			*/
		}
		
		// draw Piece
		if (piece != null && piece != PieceType.NOBODY) {
			int py = piece.getSpriteSheetPosX()*tileWidth;
			int px = piece.getSpriteSheetPosY()*tileHeight;
			
			// hier die vertikalverschiebung der pieces
			int pieceOffsetY = y - tileHeightHalf - 2;
			
			// int piece
			g.drawImage(spriteSheet, x, pieceOffsetY, x + tileWidth, pieceOffsetY + tileHeight, px, py, px + tileWidth, py + tileHeight, null);
			
			// System.out.println(row+","+col+" -- SQUARE|PIECE: "+square+" ("+sx+"/"+sy+") | "+piece+" ("+px+"/"+py+")");
		}
		

		
	}
	
	// super fast and super precise MouseListener
	
	
	
	/*
	// magic Mauskoordinatentrasformation
	Point screenToGrid(int screenX, int screenY) {
		
		// Falls das Board noch nachgerichtet werden muss
		// int offsetX = 0;
		// int offsetY = 0;
		
		int relX = screenX - screenOffsetX;
		int relY = screenY - screenOffsetY;
		
		// Isometrische Transformation Umkehren
		double col = (relX / (double)tileWidthHalf + relY / (double)tileHeight) / 2.0;
		double row = (relY / (double)tileHeight - relX / (double)tileWidthHalf) / 2.0;
		
		return new Point((int)Math.floor(col), (int)Math.floor(row));
	}
	*/

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
	public Point screenToGrid(int screenX, int screenY, int tileSize) {
		
		// - - - !!! - - - NEEDS REPAIR - - - !!! - - - NEEDS REPAIR - - - !!! - - - NEEDS REPAIR - - - !!! - - -
		
		double offgesettetesScreenX = screenX - screenOffsetX; 
		double offgesettetesScreenY = screenY - screenOffsetY;
		
		double col = offgesettetesScreenX / (double)tileWidth + offgesettetesScreenY / (double)tileHeightHalf;
		double row = offgesettetesScreenY / (double)tileHeightHalf - offgesettetesScreenX / (double)tileWidth;

		return new Point ((int)Math.floor(col), (int)Math.ceil(row));
	}

	@Override
	public void showMouseHoverIndicator(int row, int col) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearMouseHoverIndicator() {
		// TODO Auto-generated method stub
		
	}

}
