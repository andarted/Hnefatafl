package org.andarted.hnefatafl.model;

import java.util.HashSet;
import java.util.Set;

import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.common.TraceLogger;
import org.andarted.hnefatafl.model.rules.Rules;
import org.andarted.hnefatafl.presenter.IPresenter;

public class Model implements IModel {
	
	// - - - ATTRIBUTES - - -
	
	private IPresenter presenter;
	private GameBoard gameBoard;
	
	private final Rules rules = new Rules();
	
	private char[][] lineUpCharMatrix;
	
	private Participant activeParty = Participant.ANARCHISTS;
	private Participant currentEnemy = Participant.ROYALISTS;	
	
	private Set<Coordinate> currentReach  = new HashSet<Coordinate>();
	private PieceType currentPieceType = PieceType.ANARCHIST;
	private ModeType currentMode = ModeType.GRAB_PIECE;
	private Phase currentPhase = Phase.CHECK_IF_MOVE_IS_POSSIBLE;
	
	private int putToRow = -1;
	private int putToCol = -1;
	private int tookFromRow = -1;
	private int tookFromCol = -1;

	
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

    private void grabPiece(int row, int col) {
    	PieceType piece = gameBoard.pieces[row][col];
    	Participant party = piece.party;
    	
    	if (party == this.activeParty) {
    		QLog.log("model", "grabPiece()", "grabPiece at " + row + " " + col + ".");
    		this.tookFromRow = row;
    		this.tookFromCol = col;
    		this.currentPieceType = piece;
    		setReach(row, col);
    		this.currentMode = ModeType.MOVE_PIECE;
    	}
    	else {
    		QLog.log("model", "grabPiece", "Piece at " + row + " " + col + " is not from " + activeParty + " (aka activeParty) but from " + party);
    	}
    }
    
    
    private void moveCurrentPieceTo(int row, int col) {
    	if(gameBoard.inReach(row, col)) {
    		this.putToRow = row;
    		this.putToCol = col;
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [1/4] -> gameBoard.removePieceAt");
    		gameBoard.removePieceAt(this.tookFromRow, this.tookFromCol);
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [2/4] -> gameBoard.setPieceAt " + row + "," + col + ".");
    		gameBoard.movePieceTo(currentPieceType, row, col);
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [3/4] -> gameBoard.clearReachHighlight");
    		gameBoard.clearReachHighlight(); 
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [4/4] -> trapAllEnemies");
    		trapAllEnemies(row,col);
    		presenter.handleToggleActiveParty();
    	}
    	else {
    		QLog.log("model", "moveCurrendPieceTo", "NOT in Reach [1/2] -> gameBoard.clearReachHighlight");
    		gameBoard.clearReachHighlight();
    		QLog.log("model", "moveCurrendPieceTo", "NOT in Reach [2/2] -> current Mode back to GRAB_PIECE");
    		currentMode = ModeType.GRAB_PIECE;
    	}
    }
    
    
    // - - - METHODS REACH - - -
    
    private void setReach(int row, int col) {
    	QLog.log("model", "setReach", "beginne Reach zu setten");
    	for (Direction dir : Direction.values()) {
    		QLog.log("", "", "erst mal richtung " + dir);
    		setReachInDirection(row, col, dir);
    	}
    	gameBoard.setReach(currentReach);
    }
    
    private void setReachInDirection(int row, int col, Direction dir) {
    	int tempRow = row + dir.dRow;
    	int tempCol = col + dir.dCol;
    	
    	while 	(isInsideBoard(tempRow,tempCol) && gameBoard.getPieceAt(tempRow, tempCol) == PieceType.NOBODY) {
					currentReach.add(new Coordinate (tempRow, tempCol));
					tempRow += dir.dRow;
					tempCol += dir.dCol;
			}
    }
    
    

    
    private void setDefaultStartParty() {
    	activeParty = Participant.ANARCHISTS;
    	currentEnemy = Participant.ROYALISTS;
    }
    
    private void toggleActiveParty() {
    	if (activeParty == Participant.ROYALISTS) {
    		QLog.log("model", "toggleActiveParty", "setzt active Party auf ANARCHISTS|");
    		activeParty = Participant.ANARCHISTS;
    		currentEnemy = Participant.ROYALISTS;
    	}
    	else {
    		QLog.log("model", "toggleActiveParty", "setzt active Party auf ROYALISTS|");
    		activeParty = Participant.ROYALISTS;
    		currentEnemy = Participant.ANARCHISTS;
    	}
    }

    /*
    private void generateLineUp(LineUp lineUp, Variant variant) {
    	this.currentState = LineUpFactory.createLineUp(lineUp, variant);
    }
    */
    
    
    
    
    
    
    
    
    
    
    // - - - METHODS / LINE UP PIECES - - -
    
    private void setLineUp(int size, Variant variant){
    	QLog.log("model", "setLineUp", "set up " + variant.toString() + " lineUp for boardSize " + size + " |");
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
    
    
    
    
    
    
    
    
    
    
    // - - - METHODS isInAnArea - - -
    
    
    public boolean isInsideBoard(int row, int col) {
    	return 0 <= row && row < getBoardSize() && 0 <= col && col < getBoardSize();
    }
    
    public boolean isWallArea(int row, int col) {													// TODO
    	return (0 <= row && row <= 2 || getBoardSize()-2 <= row && row <= getBoardSize()) &&
    		   (0 <= col && col <= 2 || getBoardSize()-2 <= row && row <= getBoardSize());
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
    	QLog.log("model", "newGame[2/2]", "-> model.setLineUp()");
    	setLineUp(size, variant);
    	setDefaultStartParty();
    	return gameBoard;
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
    public Participant getActiveParty() {
    	return activeParty;
    }
    
    @Override
    public Participant getCurrentEnemy() {
    	return currentEnemy;
    }
    
    @Override
    public Participant getPartyAt(int row, int col) {
    	return gameBoard.getPieceAt(row, col).party;
    }
    
    @Override
    public String getActivePartyString() {
    	return activeParty.toString();
    }
    
    @Override
    public ModeType getModeType() {
    	return currentMode;
    }
    
    @Override
    public int getClickX() {
    	return putToRow;
    }
    
    @Override
    public int getClickY() {
    	return putToCol;
    }
    
    @Override
    public AreaType getAreaAt(int row, int col) {
    	return gameBoard.area[row][col];
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
		gameBoard.clearReachHighlight();
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
		QLog.log("", "", "" + getSquareAt(row, col));
		QLog.log("", "", "" + getPieceAt(row, col));
		QLog.log("", "", "" + getPartyAt(row, col));
		QLog.log("", "", "" + getAreaAt(row, col));

		switch (this.currentMode){
			case GRAB_PIECE:
				QLog.log("model", "onSquareClicked", "weil im GRAB_PIECE-Mode -> grabPiece from " + row + "," + col + ".");
				grabPiece(row,col);
				break;
			case MOVE_PIECE:
				QLog.log("model", "onSquareClicked[1/3]", "case MOVE_PIECE: -> moveCurrentPieceTo(row,col)");
				moveCurrentPieceTo(row,col);
				// -> check ob gewinnbedingung erfüllt wurde TODO
				QLog.log("model", "onSquareClicked[2/3]", "case MOVE_PIECE: -> simpleCapture");
				// -> capture all trapped enemies TODO
				QLog.log("model", "onSquareClicked[3/3]", "case MOVE_PIECE: -> currentMode is GRAB_PIECE");
				this.currentMode = ModeType.GRAB_PIECE;
				break;
			case DEBUG:
				QLog.log("model", "onSquareClicked", "weil im DEBUG-Mode -> setPiece auf " + row + "," + col + ".");
				debugSetPiece(currentPieceType);
				setPiece(currentPieceType, row, col);
				break;
			default:
				System.out.println("model.onSquareClicked:   XXX ERROR XXX - Wie konnte das passieren?");
		}
		
		QLog.log("model", "onSquareClicked", "Area is " + gameBoard.area[row][col].toString());
	}

	private void trapAllEnemies(int row, int col){
		QLog.log("model", "trapAllEnemies", "-> simpleCatch");
		
		rules.applyAll(this);
	}
	

	// - - - DEBUG - - -
	
    @Override
    public void debugPanelToggleActiveParty() {
    	toggleActiveParty();
    }
    
    @Override
    public void handleDebugModeTButton() {
    	if(currentMode != ModeType.DEBUG) {
    		currentMode = ModeType.DEBUG;
    		presenter.setDebugModeTButton(true);    		
    	}
    	else {
    		currentMode = ModeType.GRAB_PIECE;
    		presenter.setDebugModeTButton(false);
    	}
    }

	
}
