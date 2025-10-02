package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.model.SquareType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class RendererIso implements IRender{
	
	private BufferedImage spriteSheet;
	private final int tileWidth;
	private final int tileHeight;
	private final int tileHeightHalf;
	private final int screenOffsetX;
	private final int screenOffsetY;
	
	private final static String DEFAULT_STRITESHEET_PATH = "/sprites/spriteSheetIso00_64.png";

	
	public RendererIso(String sheetResourcePath, int tileWidth, int tileHeight) throws IOException {
		System.out.println("Pfad: " + getClass().getResource("/sprites/spriteSheetIso00_64.png"));
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileHeightHalf = tileHeight / 2;
				
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
	public void renderCell(Graphics2D g, int row, int col, int cellSize, SquareAppearance square, PieceAppearance piece,
			boolean inReach, boolean isBeneathMouse) {
		
		
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
		Point spritePos = square.spritePos();
		int sPosY = spritePos.x * tileWidth; 
		int sPosX = spritePos.y * tileHeight;
		
		// draw square
		g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileHeight, sPosX, sPosY, sPosX + tileWidth, sPosY + tileHeight, null);
		
		// inReach highlight
		if (inReach) {
			SquareAppearance mouseHover = SquareTypeAppearanceMapper.getAppearance(SquareType.HIGHLIGHTED);
			int sPosYH = mouseHover.spritePos.x * tileWidth;
			int sPosXH = mouseHover.spritePos.y * tileWidth;
			g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, sPosXH, sPosYH, sPosXH + tileWidth, sPosYH + tileHeight, null);
			
			/*
			g.setColor(new Color(255, 255, 0, 80));
			g.fillRect(x, y, cellSize, cellSize);
			*/
		}
		
		// beneathMouse Highlight
		if (isBeneathMouse) {
			SquareAppearance mouseHover = SquareTypeAppearanceMapper.getAppearance(SquareType.MOUSE_HOVER);
			int sPosYH = mouseHover.spritePos.x * tileWidth;
			int sPosXH = mouseHover.spritePos.y * tileWidth;
			g.drawImage(spriteSheet, x, y, x + tileWidth, y + tileWidth, sPosXH, sPosYH, sPosXH + tileWidth, sPosYH + tileHeight, null);
			
			/*
			g.setColor(new Color(255, 255, 0, 80));
			g.fillRect(x, y, cellSize, cellSize);
			*/
		}
		
		// draw Piece
		if (piece != null) {
			int py = piece.spritePos().x * tileWidth;
			int px = piece.spritePos().y * tileHeight;
			// hier die vertikalverschiebung der pieces
			int pieceOffsetY = y - tileHeightHalf - 2;
			
			g.drawImage(spriteSheet, x, pieceOffsetY, x + tileWidth, pieceOffsetY + tileHeight, px, py, px + tileWidth, py + tileHeight, null);			
			// System.out.println(row+","+col+" -- SQUARE|PIECE: "+square+" ("+sx+"/"+sy+") | "+piece+" ("+px+"/"+py+")");
		}

	}
	
	
	@Override
	public Point screenToGrid(int screenX, int screenY, int tileSize) {
		
		// - - - !!! - - - NEEDS REPAIR - - - !!! - - - NEEDS REPAIR - - - !!! - - - NEEDS REPAIR - - - !!! - - -
		// koordinaten am board
		int gridX = -1;
		int gridY = -1;
				
		int actualScreenX = screenX - screenOffsetX;
		int actualScreenY = screenY - screenOffsetY;
		
		int halfRow = actualScreenY / tileSize / 2 - 1;
		int halfCol = actualScreenX / tileSize - 1;
		
		 
		 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		double offgesettetesScreenX = screenX - screenOffsetX; 
		double offgesettetesScreenY = screenY - screenOffsetY;
		
		double col = offgesettetesScreenX / (double)tileWidth + offgesettetesScreenY / (double)tileHeightHalf;
		double row = offgesettetesScreenY / (double)tileHeightHalf - offgesettetesScreenX / (double)tileWidth;

		return new Point ((int)Math.floor(col), (int)Math.ceil(row));
		
	}

	
	/*
	@Override
    public void setSquare(Graphics2D g, int row, int col, SquareAppearance square) {}
    
    @Override
    public void setPiece(Graphics2D g, int row, int col, PieceAppearance piece) {}
	
	@Override
	public void setBoardBorder() {}
	
	@Override
	public void showMouseHoverIndicator(int row, int col) {}

	@Override
	public void clearMouseHoverIndicator() {}
	*/

}
