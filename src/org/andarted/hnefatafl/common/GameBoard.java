package org.andarted.hnefatafl.common;

import org.andarted.hnefatafl.model.Variant;

public class GameBoard {
	private final int boardSize;
	private final SquareType[][] squares;
	private final PieceType[][] pieces;
	
	private boolean[][] squareSelection;
	
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
	
    public void setHighlightAt(int row, int col) {
    	squareSelection[row][col] = true;
    }
    
    public void clearHighlightAt(int row, int col) {
    	squareSelection[row][col] = true;
    }
    
    public void ClearHighlight() {
    	initializeHighlight();
    }
	
	private void resetHighlightMap() {
		initializeHighlight();
	}
			// - - - - - - - - - /SPECIAL PAINT REACH MAP METHODE - - - - - - - - -
	
	
		// - - - GETTER - - - 
	
	public SquareType getSquareAt(int row, int col) {return squares[row][col];}
	public PieceType getPieceAt(int row, int col) {return pieces[row][col];}
	public int getBoardSize() {return boardSize;}
	public boolean isHighlighted(int row, int col) {return squareSelection[row][col];}
	
		// - - - SETTER - - -
	
	public void setBoardClear() { clearBoard(); }
	public void setSquareAt(SquareType square, int row, int col){
		squares[row][col] = square;
	}
	public void setPieceAt(PieceType piece, int row, int col) {
		pieces[row][col] = piece;
		System.out.println("GameBoard: setPieceAt (" + row + "," + col + ") ");
	}
	
	
}
