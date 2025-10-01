package org.andarted.hnefatafl.model;

// import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// import org.andarted.hnefatafl.common.PieceType;
import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.common.TraceLogger;
import org.andarted.hnefatafl.presenter.IPresenter;

public class Model implements IModel {
	
	// - - - ATTRIBUTES - - -
	
	private IPresenter presenter;
	private GameBoard gameBoard;
	private String[] LineUpAsStringArray;
	// private PieceType[][] currentState;
	private PieceType[][] lineUpPieceTypeMatrix;
	
	private char[][] lineUpCharMatrix;
	
	private Point activeSquare = new Point(-1,-1);
	private Participant activeParty = Participant.ANARCHISTS;
	private Participant currentEnemy = Participant.ROYALISTS;	
	
	private Set<Point> currentReach;
	private PieceType currentPieceType = PieceType.ANARCHIST;
	private ModeType currentMode = ModeType.PLAY;
	private Phase currentPhase = Phase.CHECK_IF_MOVE_IS_POSSIBLE;
	

	
	// - - - CONSTRUCTOR - - -
	public Model() {
		// gameBoard = newDefaultGame();
	}

	
    // - - - VERBINDUNG ZUM PRESENTER - - -
	
    public void initializePresenter(IPresenter presenter) {
        this.presenter = presenter;
        QLog.log("model", "initializePresenter", "model initialisiert presenter");
    }
    
	
	// - - - METHODES - - - 
    
    /*
     * Zug
     *  - checken, ob Zug überhaupt möglich ist.
     *  - nimm Figur
     *  - zeige mögliche neue Positionen
     *  - stelle Figur auf neue Position -/- breche ab
     *  - repaint
     *  - breechne neue Death Zone Gegner
     *  - entferne alle Figurne aus Deathzone
     *  - repaint
     *  - checke Siegbedingungen
     *  - wechsel aktive Spielerin
     *  
     *  
     */
    
    private void takeTurn() {
    	switch (this.currentPhase) {
    	case CHECK_IF_MOVE_IS_POSSIBLE:
    		// fall through
    	case GRAB_PIECE:
    		// fall through
    	case DROP_PIECE:
    		// fall through
    	case CATCH_ENEMY:
    		// fall through
    	case CHECK_WIN_CONDITION:
    		// fall through
    	case END_TURN:
    		break;
    	default:
    		QLog.log("model", "takTurn", "XXX ERROR XXX - somehow this.currentPhase is " + this.currentPhase.toString() + " - which doesn't exist!");
    	}
    }
    
    
    @Override
    public void grabPiece(int row, int col) {
    	PieceType piece = gameBoard.pieces[row][col];
    	Participant party = piece.party;
    	
    	if (party == this.activeParty) {
    		QLog.log("model", "grabPiece()", "grabPiece at " + row + " " + col + ".");
    		this.activeSquare = new Point (row, col);
    		
    		setReach(row, col); // >>> Issue #6
    		
    		movePiece(-1, -1, -1, -1); // >>> Issue #7
    		
    	}
    	else {
    		QLog.log("model", "grabPiece", "Piece at " + row + " " + col + " is not from " + activeParty + " (aka activeParty) but from " + party);
    	}
    	
    }
    
    private void setReach(int row, int col) {
    	this.currentReach = new HashSet<Point>();
    	this.activeSquare = new Point (row, col);
    	
    	int rowN = row;
    	int colN = col;
    	while (gameBoard.getPieceNorthFrom(rowN, colN) == PieceType.NOBODY && colN >= 0) {
    		this.currentReach.add(new Point (rowN-1,colN));
    		rowN--;
    		QLog.log("model", "setReach", "Auf Pos " + rowN + "," + colN + " ist ein Squaretype " + gameBoard.getSquareAt(rowN, colN) + ".");
    	}

    	int rowE = row;
    	int colE = col;
    	while (gameBoard.getPieceEastFrom(rowE, colE) == PieceType.NOBODY && colE <= gameBoard.getBoardSize()) {
    		this.currentReach.add(new Point (rowE,colE+1));
    		colE++;
    	}
    	
    	int rowS = row;
    	int colS = col;
    	while (gameBoard.getPieceSouthFrom(rowS, colS) == PieceType.NOBODY && rowS >= 0) {
    		this.currentReach.add(new Point (rowS+1,colS));
    		rowS++;
    	}
    	
    	int rowW = row;
    	int colW = col;
    	while (gameBoard.getPieceWestFrom(rowW, colW) == PieceType.NOBODY && rowW <= gameBoard.getBoardSize()) {
    		this.currentReach.add(new Point (rowW,colW-1));
    		colW--;
    	}
    	
    	if (currentPieceType != PieceType.KING) {
    		this.currentReach.removeAll(this.gameBoard.getSpecialTerrain());
    	}
    	
    	QLog.log("model", "setReach", currentReach.toString());
//    	gameBoard.clearHighlight();
    	gameBoard.setReach(currentReach);
    	// - nicht vergessen : Was wenn Liste leer ist? Stichwort: NullPointerException
    }
   
    private Point northOf(Point p) {
    	return new Point(p.x+1,p.y);
    }
    
    private Point eastOf(Point p) {
    	return new Point(p.x,p.y+1);
    }
    
    private Point southOf(Point p) {
    	return new Point(p.x+1,p.y);
    }
    
    private Point westOf(Point p) {
    	return new Point(p.x,p.y-1);
    }
    
    private void movePiece(int rowStart, int colStart, int rowEnd, int colEnd) {
    	
    }
    
    private void checkMovePossible() {
    	
    }
    
    private void toggleActiveParty() {
    	if (activeParty == Participant.ROYALISTS) {
    		QLog.log("model", "toggleActiveParty", "setzt active Party auf ANARCHISTS");
    		activeParty = Participant.ANARCHISTS;
    		currentEnemy = Participant.ROYALISTS;
    	}
    	else {
    		QLog.log("model", "toggleActiveParty", "setzt active Party auf ROYALISTS");
    		activeParty = Participant.ROYALISTS;
    		currentEnemy = Participant.ANARCHISTS;
    	}
    }
    
    // - - - OVERRIDES - - - 
    
    @Override
    public GameBoard newDefaultGame() {
    	QLog.log("model", "newDefaultGame", "-> model.newGame([default Einstellungen])");
    	newGame(9, Variant.STANDARD);
    	return this.gameBoard;
    }
    
    
    @Override
    public GameBoard newGame(int size, Variant variant) {
    	QLog.log("model", "newGame[1/2]", "this.gameBoard ist neues GameBoard");
    	this.gameBoard = new GameBoard(size, variant);
    	QLog.log("model", "newGame[2/2]", "-> model.setLineUp() [squares & pieces werden gemäß Aufstellung neu initialisiert");
    	setLineUp(size, variant);
    	return gameBoard;
    }
    

    /*
    private void generateLineUp(LineUp lineUp, Variant variant) {
    	this.currentState = LineUpFactory.createLineUp(lineUp, variant);
    }
    */
    
    // - - - METHODS / LINE UP PIECES - - -
    
    private void setLineUp(int size, Variant variant){
    	int height = size;
    	int width = size;
    	PieceType piece = PieceType.NOBODY;
    	
    	recallLineUp(size, variant);
    	
    	for (int row = 0; row < height; row++) {
    		for (int col = 0; col < width; col++) {
    			piece = recallPieceAt(lineUpCharMatrix, row, col);
    			gameBoard.setPieceAt(piece, row, col);
    		}
    	}
    }
    
    private PieceType recallPieceAt(char[][] charMatrix, int row, int col) {
    	char pieceChar = lineUpCharMatrix[row][col];
    	PieceType pieceType = PieceType.NOBODY;
    	
    	switch (pieceChar) {
    	case '.': return PieceType.NOBODY;
    	case 'A': return PieceType.ANARCHIST;
    	case 'R': return PieceType.ROYALIST;
    	case 'K': return PieceType.KING;
    	
    	default: 
    		System.out.println("model.recallPieceAt(): How the Fuck messed I up???");
    		return PieceType.PIECE_ERROR;
    		
    	}
    }
    
    private void recallLineUp(int size, Variant variant){
    	this.lineUpCharMatrix = translateToCharMatrix(recallSpecificLineUp(size, variant));
    }
    
    private static String[] recallSpecificLineUp(int size, Variant variant) {
    	switch (size) {
    		case 7: 
    			if (variant.thisIsTheAlt())
    				return LineUp.SIZE_7_ALTERNATIVE.getLineUpString();
    			else
    				return LineUp.SIZE_7_STANDART.getLineUpString();
    			
    		case 9: 
    			if (variant.thisIsTheAlt())
    				return LineUp.SIZE_9_ALTERNATIVE.getLineUpString();
    			else
    				return LineUp.SIZE_9_STANDART.getLineUpString();
    			
    		case 11: 
    			if (variant.thisIsTheAlt())
    				return LineUp.SIZE_11_ALTERNATIVE.getLineUpString();
    			else
    				return LineUp.SIZE_11_STANDART.getLineUpString();
    			
    		case 13: 
    			if (variant.thisIsTheAlt())
    				return LineUp.SIZE_13_ALTERNATIVE.getLineUpString();
    			else
    				return LineUp.SIZE_13_STANDART.getLineUpString();
    			
    		default:
    			throw new IllegalArgumentException("Model: Gibt kein LineUp für " + size + " && " + variant);
    	}
    }
    
    private static char[][] translateToCharMatrix(String[] stringArray){
    	int rows = stringArray.length;
    	int cols = findMaxCol(stringArray);
    	
    	char[][] charMap = new char[rows][cols];
    	
    	for (int row = 0; row < rows; row++) {
    			charMap[row] = stringArray[row].toCharArray();
    		}
    	return charMap;
    }
 
    private static int findMaxCol(String[] stringArray) {
    	int maxStringLength = 0;
    	int numberOfStrings = stringArray.length;
    	for (int currentString = 0; currentString < numberOfStrings; currentString++) {
    		if (maxStringLength < stringArray[currentString].length()) {
    			maxStringLength = stringArray[currentString].length();
    		}
    	}
    	return maxStringLength;
    }
    
    
    // - - - METHODS / HIT DEDECTION - - -
    
    private ArrayList<Point> hitScanAnarchists(GameBoard gameBoard) {
    	ArrayList<Point> deathZoneAnarchists = new ArrayList<Point>();
    		
    	return deathZoneAnarchists;
    }
    
    private ArrayList<Point> hitScanRoyalists(GameBoard gameBoard) {
    	ArrayList<Point> deathZoneRoyalists = new ArrayList<Point>();
    		
    	return deathZoneRoyalists;
    }
    
    private ArrayList<Point> hitScanKings(GameBoard gameBoard) {
    	ArrayList<Point> deathZoneKings = new ArrayList<Point>();
    		
    	return deathZoneKings;
    }
    
    
    // - - - GETTER - - -
    
    public GameBoard getGameBoard() {
    	return this.gameBoard;
    }
    
    @Override
    public int getBoardSize() {
    	return gameBoard.getBoardSize();
    }
    
    @Override
    public SquareType getSquareAt(int row, int col) {
    	return gameBoard.getSquareAt(row, col);
    }
    
    @Override
    public PieceType getPieceAt(int row, int col) {
    	return gameBoard.getPieceAt(row,  col);
    }
    
    @Override
    public String getActiveParty() {
    	return activeParty.toString();
    }
    


    
    // - - - SETTER - - -
    
    public void setFreshLineUp(int size, Variant variant) {	
    	// generateLineUp(lineUpFromSize(size), variant);
    }

	@Override
    public void debugSetPiece(PieceType pieceType) {
		QLog.log("model", "debugSetPiece", "currentMode ist " + currentMode);
		this.currentMode = ModeType.DEBUG;
		QLog.log("model", "debugSetPiece", "currentPieceType ist " + pieceType);
		this.currentPieceType = pieceType;
		
    }
    
	@Override
    public void setPiece(PieceType pieceType, int row, int col) {
		// QLog.log("model", "setPiece", "Aktuell liegt auf dem Zielfeld: " + this.currentState[row][col] + ".");
		QLog.log("model", "setPiece", "Setzte " + pieceType + " auf (" + row + "," + col + ").");
		gameBoard.setPieceAt(pieceType, row, col);
		
    	// this.currentState[row][col] = pieceType;
    }
	
	@Override
	public void setMode(ModeType mode) {
		QLog.log("model", "setMode", "currentMode wird gesetzt auf " + mode.toString() + ".");
		this.currentMode = mode;
	}


	@Override
	public void delegateSetMouseHoverPos(int row, int col) {
		TraceLogger.log("model", "delegateSetMouseHoverPos:", true, "gameBoard.setMouseHoverPos()");
		gameBoard.setMouseHoverPos(row,col);
	}

	
	// - - - HANDLE - - -
	
	@Override
	public void onSquareClicked(int row, int col) {
		QLog.log("model", "onSquareClicked", "registriere Click auf " + row + "," + col);
		/*
		QLog.log("", "", "weil im PLAY-Mode -> grabPice auf " + row + "," + col + ".");
		grabPiece(row,col);
		*/
		
		switch (this.currentMode){
			case PLAY:
				QLog.log("", "", "weil im PLAY-Mode -> grabPice auf " + row + "," + col + ".");
				grabPiece(row,col);
				break;
			case DEBUG:
				QLog.log("", "", "weil im DEBUG-Mode -> setPiece auf " + row + "," + col + ".");
				debugSetPiece(currentPieceType);
				setPiece(currentPieceType, row, col);
				break;
			default:
				System.out.println("model.onSquareClicked:   XXX ERROR XXX - Wie konnte das passieren?");
		}
		
	}


	// - - - DEBUG - - -
	
    @Override
    public void debugPanelToggleActiveParty() {
    	toggleActiveParty();
    }

	
}
