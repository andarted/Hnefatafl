package org.andarted.hnefatafl.common;

import java.awt.Color;
import java.awt.Point;

enum PieceType {
	NOBODY(Color.WHITE, "", new Point(0, 0)),
	ANARCHIST(Color.RED, "A", new Point(2, 0)),
	ROYALIST(Color.BLUE, "R", new Point(2, 1)),
	KING(Color.CYAN, "K", new Point(2, 2));

	private final Color color;
	private final String symbol;
	private final Point spriteSheetPos;
	
	PieceType(Color color, String symbol, Point spriteSheetPos){
		this.color = color;
		this.symbol = symbol;
		this.spriteSheetPos = spriteSheetPos;
		}

	public Color getColor(){
		return color;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public int getSpriteSheetPosX() {
		return spriteSheetPos.x;
	}
	
	public int getSpriteSheetPosY() {
		return spriteSheetPos.y;
	}
}
