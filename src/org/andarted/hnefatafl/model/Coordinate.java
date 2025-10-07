package org.andarted.hnefatafl.model;

class Coordinate {
	public final int row;
	public final int col;
	
	public Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Coordinate)) return false;
		Coordinate c = (Coordinate) o;
		return row == c.row && col == c.col;
	}
	
	@Override
	public int hashCode() {
		return 31 * row + col; // super simpel, sollte für den Zweck aber ausreichen.
	}
	
	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}
	
}
