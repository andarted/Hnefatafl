package org.andarted.hnefatafl.view;

import java.awt.Color;
import java.awt.Point;

class PieceAppearance {
	private final Color color;
	private final char initial;
	private final Point spritePos;

	public PieceAppearance(Color color, char initial, Point spritePos) {
		this.color = color;
		this.initial = initial;
		this.spritePos = spritePos;
	}
	
	public Color color() {return color;}
	public char initial() {return initial;}
	public Point spritePos() {return spritePos;}
}
