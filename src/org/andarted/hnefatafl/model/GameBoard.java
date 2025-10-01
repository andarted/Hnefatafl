package org.andarted.hnefatafl.model;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// import org.andarted.hnefatafl.model.PieceType;
// import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.common.TraceLogger;

public class GameBoard {
	private final int boardSize;
	private final SquareType[][] squares;
	public final PieceType[][] pieces;
	
	private boolean[][] squareSelection;
	private int mouseHoverPosX = 0;
	private int mouseHoverPosY = 0;
	
	private final int lefCol;
	private final int rigCol;
	private final int topRow;
	private final int botRow;
	private final int centre;
	

	// - - - KONSTRUKTOR - - -
	
	public GameBoard(int size, Variant variant){
		boardSize = size;
		topRow = 0;
		botRow = boardSize-1;
		lefCol = 0;
		rigCol = boardSize-1;
		centre = boardSize/2;
		// boolean isThisTheAlt = variant.isThisTheAlt();
		
		squares = new SquareType[size][size];
		pieces = new PieceType[size][size];
		squareSelection = new boolean[size][size];
		for (int row = topRow; row <= botRow; row++) {
			for (int col = lefCol; col <= rigCol; col++) {
				squares[row][col] = SquareType.EMPTY;
				pieces[row][col] = PieceType.NOBODY;
			}
		}
		integrateBoardItems(size);
	}
	
	private void clearBoard() {
		for (int row = topRow; row <= botRow; row++) {
			for (int col = lefCol; col <= rigCol; col++) {
				pieces[row][col] = PieceType.NOBODY;
			}
		}
	}
	
	private void integrateBoardItems(int size) {
		squares[topRow][lefCol] = SquareType.ESCAPE;
		squares[topRow][rigCol] = SquareType.ESCAPE;
		squares[botRow][lefCol] = SquareType.ESCAPE;
		squares[botRow][rigCol] = SquareType.ESCAPE;
		squares[centre][centre] = SquareType.THRONE;
	}
	
	// - - - METHODEN - - -
	
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
	
	public void paintReachMap(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol) {

		initializeHighlight();
		addHorizonalReach(originRow, fromCol, toCol);
		addVerticalReach(originCol, fromRow, toRow);
    }
	
	private void initializeHighlight() {
		squareSelection = new boolean[boardSize][boardSize];
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				squareSelection[row][col] = false;
			}
		}
	}
	
	private void addHorizonalReach(int col, int from, int to) {
		for (int i = from; i <= to; i++) {
    		squareSelection[col][i] = true;
    	}
	}
	
	private void addVerticalReach(int row, int from, int to) {
		for (int i = from; i <= to; i++) {
    		squareSelection[i][row] = true;
    	}
	}
    
    private void highlightHoverPosition() {
    	this.mouseHoverPosX = 1;
    	this.mouseHoverPosY = 1;
    }
    	
	public void clearHoverPosition() {
		initializeHighlight();
	}
	
			// - - - - - - - - - /SPECIAL PAINT REACH MAP METHODE - - - - - - - - -
	
	
		// - - - GETTER - - - 
	
	public SquareType getSquareAt(int row, int col) {
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}
	}
	
	public SquareType getSquareNorthFrom(int row, int col) {
		row--;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	public SquareType getSquareEastFrom(int row, int col) {
		col++;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	
	public SquareType getSquareSouthFrom(int row, int col) {
		row++;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	public SquareType getSquareWestFrom(int row, int col) {
		col--;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return squares[row][col];
		}
		else {
			return null;
		}}
	
	public PieceType getPieceNorthFrom(int row, int col) {
		row--;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	public PieceType getPieceEastFrom(int row, int col) {
		col++;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	
	public PieceType getPieceSouthFrom(int row, int col) {
		row++;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	public PieceType getPieceWestFrom(int row, int col) {
		col--;
		if (0 <= row && row < this.boardSize && 0 <= col && col < this.boardSize) {
			return pieces[row][col];
		}
		else {
			return null;
		}}
	
	public PieceType getPieceAt(int row, int col) {return pieces[row][col];}
	
	public int getBoardSize() {return boardSize;}
	
	public int getCentre() {return centre;}
	
	public Set<Point> getSpecialTerrain() {
		Set<Point> specialTerrain = new HashSet<>();
		specialTerrain.add(new Point(this.centre,this.centre));
		specialTerrain.add(new Point(0,0));
		specialTerrain.add(new Point(0,this.boardSize));
		specialTerrain.add(new Point(this.boardSize,0));
		specialTerrain.add(new Point(this.boardSize,this.boardSize));
		return specialTerrain;
	}
	
	public boolean isHighlighted(int row, int col) {return squareSelection[row][col];}
	
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
		pieces[row][col] = piece;
		// System.out.println("GameBoard: setPieceAt (" + row + "," + col + ") ");
	}
	
    public void setHighlightAt(int row, int col) {
    	squareSelection[row][col] = true;
    }
    
    public void setReach(Set<Point> reach) {
    	for (Point p : reach) {
    		setHighlightAt(p.x, p.y);
    	}
    }
    
    public void clearHighlight() {
    	for (int row = 0; row < this.boardSize; row++) {
    		for (int col = 0; col < this.boardSize; col--) {
    			this.squareSelection[row][col] = false;
    		}
    	}
    }
    
    public void clearHighlightAt(int row, int col) {
    	squareSelection[row][col] = true;
    }
	
	public void setMouseHoverPos(int row, int col){
		this.mouseHoverPosX = row;
		this.mouseHoverPosY = col;
		clearHoverPosition();
		if (row != -1 || col != -1) {
			squareSelection[row][col] = true;
		}
		
		TraceLogger.log("gameBoard", "SetMouseHoverPos:", true, "– – –");
	}	
	
}
