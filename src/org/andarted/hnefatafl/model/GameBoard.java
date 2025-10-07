package org.andarted.hnefatafl.model;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.andarted.hnefatafl.common.QLog;
// import org.andarted.hnefatafl.model.PieceType;
// import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.common.TraceLogger;

public class GameBoard {
	private final int boardSize;
	private final SquareType[][] squares;
	public final PieceType[][] pieces;
	public final AreaType[][] area;
	
	private boolean[][] squareBeneathMouse;
	private boolean[][] reachSelection;
	private int mouseHoverPosX = 0;
	private int mouseHoverPosY = 0;
	
	private final int lefCol;
	private final int rigCol;
	private final int topRow;
	private final int botRow;
	private final int centre;
	

	// - - - KONSTRUKTOR - - -
	
	public GameBoard(int size, Variant variant){
		QLog.log("gameBoard", "", "NEUES GAMEBOARD WIRD ERSTELLT");
		boardSize = size;
		topRow = 0;
		botRow = boardSize-1;
		lefCol = 0;
		rigCol = boardSize-1;
		centre = boardSize/2;
		// boolean isThisTheAlt = variant.isThisTheAlt();
		
		squares = new SquareType[size][size];
		pieces = new PieceType[size][size];
		area = new AreaType[size][size];
		squareBeneathMouse = new boolean[size][size];
		reachSelection = new boolean[size][size];
		for (int row = topRow; row <= botRow; row++) {
			for (int col = lefCol; col <= rigCol; col++) {
				squares[row][col] = SquareType.EMPTY;
				pieces[row][col] = PieceType.NOBODY;
				area[row][col] = AreaType.COMMONS;
				reachSelection[row][col] = false;
			}
		}
		
		integrateBoardItems(size);
		divideLand(size);
	}
	
	private void divideLand(int size) {
		for (int col = 1; col < size-1; col++) {				// NORTH
			area[0][col] = AreaType.NORTH_WALL;
		}
		for (int col = 1; col < size-1; col++) {
			area[1][col] = AreaType.NORTH_FIELD;
		}
		for (int row = 1; row < size-1; row++) {				// EAST
			area[row][size-1] = AreaType.EAST_WALL;
		}
		for (int row = 1; row < size-2; row++) {
			area[row][size-2] = AreaType.EAST_FIELD;
		}
		for (int col = 1; col < size-1; col++) {				// SOUTH
			area[size-1][col] = AreaType.SOUTH_WALL;
		}
		for (int col = 1; col < size-1; col++) {
			area[size-2][col] = AreaType.SOUTH_FIELD;
		}
		for (int row = 1; row < size-1; row++) {				// WEST
			area[row][0] = AreaType.WEST_WALL;
		}
		for (int row = 1; row < size-2; row++) {
			area[row][1] = AreaType.WEST_FIELD;
		}
		area[centre-1][centre] = AreaType.THRONE_NORTH_SQUARE;	// THRONE AREA
		area[centre-2][centre] = AreaType.THRONE_NORTH_ATTACK;
		area[centre][centre+1] = AreaType.THRONE_EAST_SQUARE;
		area[centre][centre+2] = AreaType.THRONE_EAST_ATTACK;
		area[centre+1][centre] = AreaType.THRONE_SOUTH_SQUARE;
		area[centre+2][centre] = AreaType.THRONE_SOUTH_ATTACK;
		area[centre][centre-1] = AreaType.THRONE_WEST_SQUARE;
		area[centre][centre-2] = AreaType.THRONE_WEST_ATTACK;
		area[centre][centre] = AreaType.THRONE_WITH_KING;		// THRONE
	}
		
	private void integrateBoardItems(int size) {
		squares[topRow][lefCol] = SquareType.ESCAPE;
		squares[topRow][rigCol] = SquareType.ESCAPE;
		squares[botRow][lefCol] = SquareType.ESCAPE;
		squares[botRow][rigCol] = SquareType.ESCAPE;
		squares[centre][centre] = SquareType.THRONE;
	}

	
	// - - - METHODEN - - -

	private void clearBoard() {
		for (int row = topRow; row <= botRow; row++) {
			for (int col = lefCol; col <= rigCol; col++) {
				pieces[row][col] = PieceType.NOBODY;
			}
		}
	}
	
			// - - - - - - - - - SPECIAL PAINT REACH MAP METHODE - - - - - - - - -
	
	/* Diese ganze paintReach Sache, um zu eruieren welche Felde gehighlighted
	 * werden sollen gehört eigentlich nicht in die View, das ist ein Job für's
	 * Model oder dem Presenter. Weil die aber noch an grundelegenderen Problemen
	 * arbeiten, habe ich das hier angelegt. ... Und es bald bereut.
	 * 
	 * Als ich nämlich einen zweiten Renderer auf Sprite basis angelegt habe,
	 * merkte ich, dass ich mehr Infos haben möchte, als nur binäre welche Felder
	 * gehilighted sind. Gerne wüsste ich wo die aktive Figur ist, und wo das in
	 * räumlicher Relation zum gerade gepainteten Feld liegt, damit ich z.b. per
	 * Animation anzeigen kann, wohin die Figur laufen kann.
	 * 
	 * Aber dafür müsste ich die ganze Highlight-verarbeitung in die Renderer
	 * auslagern, was bedeutet, dass ich noch mal meinen alten Swing-basierten
	 * Renderer umbauen müsste, und ich zudem furchtbar viel redundanten code
	 * schreibe. Das sollte eigentlich alles an andere Stelle passieren, und die
	 * View sollte nur erfahren, welches Feld einzeln jeweils wie aussehen soll.
	 * 
	 * Deswegen gibt's im Sprite Renderer nur ein langweiliges Highlighting. ...
	 * Und vielleicht noch nicht mal das, weil der Presenter jetzt erst mal
	 * einfach nur zum Laufen gebracht werden muss.
	 */
	
	public void paintReachMap(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol) { // nicht genutzt !!

		clearReachHighlight();
		addHorizonalReach(originRow, fromCol, toCol);
		addVerticalReach(originCol, fromRow, toRow);
		// removeSpecialTerrain();
    }
	
	private void addHorizonalReach(int col, int from, int to) {
		for (int i = from; i <= to; i++) {
			reachSelection[col][i] = true;
		}
	}
	
	private void addVerticalReach(int row, int from, int to) {
		for (int i = from; i <= to; i++) {
			reachSelection[i][row] = true;
		}
	}

	private void initializeHighlight() {
		squareBeneathMouse = new boolean[boardSize][boardSize];
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				squareBeneathMouse[row][col] = false;
			}
		}
	}
	
    	
	public void clearHoverPosition() {
		initializeHighlight();
	}
	
			// - - - - - - - - - /SPECIAL PAINT REACH MAP METHODE - - - - - - - - -
	
	
		// - - - GETTER - - - 
	

	
	public SquareType getSquareNorthFrom(int row, int col) {
		row--;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	public SquareType getSquareEastFrom(int row, int col) {
		col++;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	
	public SquareType getSquareSouthFrom(int row, int col) {
		row++;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	public SquareType getSquareWestFrom(int row, int col) {
		col--;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	
	public PieceType getPieceNorthFrom(int row, int col) {
		row--;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	public PieceType getPieceEastFrom(int row, int col) {
		col++;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	
	public PieceType getPieceSouthFrom(int row, int col) {
		row++;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	public PieceType getPieceWestFrom(int row, int col) {
		col--;
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	
	public SquareType getSquareAt(int row, int col) {
		if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}
	}
	
	public PieceType getPieceAt(int row, int col) {return pieces[row][col];}
	
	public int getBoardSize() {return boardSize;}
	
	public int getCentre() {return centre;}
	
	public Set<Point> getSpecialTerrain() {
		Set<Point> specialTerrain = new HashSet<>();
		specialTerrain.add(new Point(centre,centre));
		specialTerrain.add(new Point(topRow,lefCol));
		specialTerrain.add(new Point(topRow,rigCol));
		specialTerrain.add(new Point(botRow,lefCol));
		specialTerrain.add(new Point(botRow,rigCol));
		return specialTerrain;
	}
	
	public boolean inReach(int row, int col) {return reachSelection[row][col];}
	
	public boolean squareIsBeneathMouse(int row, int col) {return squareBeneathMouse[row][col];}
	
    private Point northOf(int row, int col) {
    	return new Point(row-1,col);
    }
    
    private Point eastOf(int row, int col) {
    	return new Point(row,col+1);
    }
    
    private Point southOf(int row, int col) {
    	return new Point(row+1,col);
    }
    
    private Point westOf(int row, int col) {
    	return new Point(row,col-1);
    }
	
		// - - - SETTER - - -
	
	public void setBoardClear() { clearBoard(); }
	
	public void setSquareAt(SquareType square, int row, int col){
		squares[row][col] = square;
	}
	
	public void setPieceAt(PieceType piece, int row, int col) {
		QLog.log("gameBoard", "setPieceAt", "set " + piece +" Piece at (" + row + "," + col + ")|");
		pieces[row][col] = piece;
		
	}
	
	public void movePieceTo(PieceType piece, int row, int col) {
		QLog.log("gameBoard", "movePieceTo", "move " + piece + " Piece to (" + row + "," + col + ")|");
		pieces[row][col] = piece;
	}
	
	public void removePieceAt(int row, int col) {
		QLog.log("gameBoard", "removePieceAt", "remove Piece at (" + row + "," + col + ")|");
		pieces[row][col] = PieceType.NOBODY;
	}
	
	
	public void clearReachHighlighAt(int row, int col) {
		reachSelection[row][col] = false;
	}

	public void setReachHighlightAt(int row, int col) {
    	reachSelection[row][col] = true;
    }
    
    public void setReach(Set<Coordinate> reach) {
    	clearReachHighlight();
    	for (Coordinate c : reach) {
    		setReachHighlightAt(c.row, c.col);
    	}
    }

    public void clearReachHighlight() {
    	QLog.log("gameBoard", "clearReachHighlight", "clear reach highlight. |");
    	for (int row = 0; row < boardSize; row++) {
    		for (int col = 0; col < boardSize; col++) {
    			this.reachSelection[row][col] = false;
    		}
    	}
    }
    
    public void clearHighlightAt(int row, int col) {
    	squareBeneathMouse[row][col] = true;
    }

    
	
	public void setMouseHoverPos(int row, int col){
		this.mouseHoverPosX = row;
		this.mouseHoverPosY = col;
		clearHoverPosition();
		if (row != -1 || col != -1) {
			squareBeneathMouse[row][col] = true;
		}
		TraceLogger.log("gameBoard", "SetMouseHoverPos:", true, "– – –");
	}	
	
}
