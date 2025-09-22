package org.andarted.hnefatafl.view;

import java.awt.Color;
import java.awt.Point;

public enum HighlightType {
	NO_HIGLIGHT(Color.RED, "", new Point(0, 0)),
	HIGHLIGHTED(Color.YELLOW,"", new Point(1, 0)),
	HIGHLIGHTED_ORIGIN(Color.YELLOW,"+", new Point(1, 1)),
	HIGHLIGHTED_XAXIS(Color.YELLOW,"-", new Point(1, 2)),
	HIGHLIGHTED_YAXIS(Color.YELLOW,"|", new Point(1, 3));
	
	private final Color color;
	private final String symbol;
	private final Point spriteSheetPos;
	
	HighlightType(Color color, String symbol, Point spriteSheetPos){
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
