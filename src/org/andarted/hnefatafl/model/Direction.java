package org.andarted.hnefatafl.model;

public enum Direction {
	NORTH(-1, 0),
	EAST(0, 1),
	SOUTH(1, 0),
	WEST(0, -1);

	public final int dRow;
	public final int dCol;

	Direction(int dRow, int dCol){
		this.dRow = dRow;
		this.dCol = dCol;
	}
}