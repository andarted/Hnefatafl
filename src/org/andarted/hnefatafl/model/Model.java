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
import org.andarted.hnefatafl.model.rules.Rules;
import org.andarted.hnefatafl.presenter.IPresenter;

public class Model implements IModel {
	
	// - - - ATTRIBUTES - - -
	
	private IPresenter presenter;
	private GameBoard gameBoard;
	
	private final Rules rules = new Rules();
	
	// private String[] LineUpAsStringArray;
	// private PieceType[][] currentState;
	// private PieceType[][] lineUpPieceTypeMatrix;
	
	private char[][] lineUpCharMatrix;
	
	private Point activeSquare = new Point(-1,-1);
	private Participant activeParty = Participant.ANARCHISTS;
	private Participant currentEnemy = Participant.ROYALISTS;	
	
	private Set<Point> currentReach;
	private PieceType currentPieceType = PieceType.ANARCHIST;
	private ModeType currentMode = ModeType.GRAB_PIECE;
	private Phase currentPhase = Phase.CHECK_IF_MOVE_IS_POSSIBLE;
	
	private Point dropPos = new Point (3,3);

	
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
    
    
    /*
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
    		QLog.log("model", "takTurn", " ERROR - somehow this.currentPhase is " + this.currentPhase.toString() + " - which doesn't exist!");
    	}
    }
    
    */
    
    private void grabPiece(int row, int col) {
    	PieceType piece = gameBoard.pieces[row][col];
    	Participant party = piece.party;
    	
    	if (party == this.activeParty) {
    		QLog.log("model", "grabPiece()", "grabPiece at " + row + " " + col + ".");
    		this.activeSquare = new Point (row, col);
    		this.currentPieceType = piece;
    		setReach(row, col);
    		this.currentMode = ModeType.MOVE_PIECE;
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
    	gameBoard.setReach(currentReach);
    }

    
    /*
    private Point northOf(Point p, int distance) {
    	return new Point(p.x+distance,p.y);
    }
    
    private Point eastOf(Point p, int distance) {
    	return new Point(p.x,p.y+1);
    }
    
    private Point southOf(Point p, int distance) {
    	return new Point(p.x+1,p.y);
    }
    
    private Point westOf(Point p, int distance) {
    	return new Point(p.x,p.y-1);
    }
    
    private void movePiece(int rowStart, int colStart, int rowEnd, int colEnd) {
    	
    }
    */
    
    private void moveCurrentPieceTo(int row, int col) {
    	if(gameBoard.inReach(row, col)) {
    		this.dropPos = new Point (row,col);
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [1/4] -> gameBoard.removePieceAt");
    		gameBoard.removePieceAt(this.activeSquare.x, this.activeSquare.y);
    		QLog.log("model", "moveCurrendPieceTo", "in Reach [2/4] -> gameBoard.setPieceAt " + this.activeSquare.x + "," + this.activeSquare.y + ".");
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
    		QLog.log("model", "moveCurrendPieceTo", "NOT in Reach [1/2] -> current Mode back to GRAB_PIECE");
    		currentMode = ModeType.GRAB_PIECE;
    	}
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
    	return gameBoard;
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
    
    
    // - - - METHODS / HIT DEDECTION - - -
    
    /*
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
    */
    
    // - - - METHODS TO CAPTURE
    
    private void simpleCapture(int row, int col) {
    	int rowN2 = row-2, rowN1 = row-1, rowS1 = row+1, rowS2 = row+2;
    	int colW2 = col-2, colW1 = col-1, colE1 = col+1, colE2 = col+2;
    	PieceType foe;
    	if (activeParty == Participant.ANARCHISTS) {foe = PieceType.ROYALIST;}
    		else {foe = PieceType.ANARCHIST;}
    	
    	QLog.log("model", "simpleCapture", "CHECKE AB, OB ES EINE FIGUR ZU CATCHEN GIBT");
    	// !!! wichtig für folgende if: nur wegen reihenfolge der Bedingungen gibt's keine NullPointerExcaption !!!!
    	if (row > 1 && getPieceAt(rowN1, col) == foe && getPieceAt(rowN2, col).party == activeParty) { 
    		QLog.log("model", "simpleCapture", "capture piece north");
    		setPiece(PieceType.NOBODY, rowN1, col);
    	}
    	if (col < getBoardSize()-2 && getPieceAt(row, colE1) == foe && getPieceAt(row, colE2).party == activeParty) {
    		QLog.log("model", "simpleCapture", "capture piece east");
    		setPiece(PieceType.NOBODY, row, colE1);
    	}
    	if (row < getBoardSize()-2 && getPieceAt(rowS1, col) == foe && getPieceAt(rowS2, col).party == activeParty) {
    		QLog.log("model", "simpleCapture", "capture piece south");
    		setPiece(PieceType.NOBODY, rowS1, col);
    	}
    	if (col > 1 && getPieceAt(row, colW1) == foe && getPieceAt(row, colW2).party == activeParty) {
    		QLog.log("model", "simpleCapture", "capture piece west");
    		setPiece(PieceType.NOBODY, row, colW1);
    	}
    }
    
    private void escapeSquareCapture(int row, int col) {
    	int rowN2 = row-2, rowN1 = row-1, rowS1 = row+1, rowS2 = row+2;
    	int colW2 = col-2, colW1 = col-1, colE1 = col+1, colE2 = col+2;
    	PieceType foe;
    	if (activeParty == Participant.ANARCHISTS) {foe = PieceType.ROYALIST;}
    		else {foe = PieceType.ANARCHIST;}
    	
    	QLog.log("model", "simpleCapture", "CHECKE AB, OB ES EINE FIGUR ZU CATCHEN GIBT");
    	// !!! wichtig für folgende if: nur wegen reihenfolge der Bedingungen gibt's keine NullPointerExcaption !!!!
    	if (row > 1 && getPieceAt(rowN1, col) == foe && getSquareAt(rowN2, col) == SquareType.ESCAPE) { 
    		QLog.log("model", "escapeSquareCapture", "capture piece north");
    		setPiece(PieceType.NOBODY, rowN1, col);
    	}
    	if (col < getBoardSize()-2 && getPieceAt(row, colE1) == foe && getSquareAt(row, colE2) == SquareType.ESCAPE) {
    		QLog.log("model", "escapeSquareCapture", "capture piece east");
    		setPiece(PieceType.NOBODY, row, colE1);
    	}
    	if (row < getBoardSize()-2 && getPieceAt(rowS1, col) == foe && getSquareAt(rowS2, col) == SquareType.ESCAPE) {
    		QLog.log("model", "escapeSquareCapture", "capture piece south");
    		setPiece(PieceType.NOBODY, rowS1, col);
    	}
    	if (col > 1 && getPieceAt(row, colW1) == foe && getSquareAt(row, colW2) == SquareType.ESCAPE) {
    		QLog.log("model", "escapeSquareCapture", "capture piece west");
    		setPiece(PieceType.NOBODY, row, colW1);
    	}
    }
    
    private void throneSquareCapture(int row, int col) {
    	// TODO
    }
    
    private 
    
    
    private void shieldWallCapture(int row, int col) {
    	int rowN2 = row-2, rowN1 = row-1, rowS1 = row+1, rowS2 = row+2;
    	int colW2 = col-2, colW1 = col-1, colE1 = col+1, colE2 = col+2;
    	PieceType foe;
    	Set<Integer> wallOfFoes = new HashSet<>();
    	Set<Point> allWallOfFoes = new HashSet<>();
    	boolean contestIsActive = true;
    	
    	if (activeParty == Participant.ANARCHISTS) {foe = PieceType.ROYALIST;}
    		else {foe = PieceType.ANARCHIST;}
    	
    	QLog.log("model", "shieldWallCapture", "CHECKE AB, OB ES EINE WALL ZU CATCHEN GIBT");
    	// !!! wichtig für folgende if: nur wegen reihenfolge der Bedingungen gibt's keine NullPointerExcaption !!!!
    	
    	if (row == 1 && getPieceAt(rowN1, col) == foe) {
    		QLog.log("model", "shildWallCapture", "conflict at the north wall");
    		allWallOfFoes.addAll(shieldWallCompetitionRow(col, row, rowN1));
    	}
    	if (col == getBoardSize()-2 && getPieceAt(row, colE1) == foe) {
    		QLog.log("model", "shildWallCapture", "conflict at the east wall");
    		allWallOfFoes.addAll(shieldWallCompetitionCol(col, row, colE1));
    	}
    	if (row == getBoardSize()-2 && getPieceAt(rowS1, col) == foe) {
    		QLog.log("model", "shildWallCapture", "conflict at the south wall");
    		allWallOfFoes.addAll(shieldWallCompetitionRow(col, row, rowS1));
    	}
    	if (col == 1 && getPieceAt(row, colW1) == foe) {
    		QLog.log("model", "shildWallCapture", "conflict at the west wall");
    		allWallOfFoes.addAll(shieldWallCompetitionCol(col, row, colW1));
    	}
    	
    	// König aussortieren
    	allWallOfFoes.removeIf(p -> getPieceAt(p.x,p.y) == PieceType.KING);
    	
    	// Foes gefangen nehmen
    	for (Point p : allWallOfFoes) {
    		setPiece(PieceType.NOBODY, p.x, p.y);
    	}
    }
    

    private Set<Point> shieldWallCompetitionRow(int origin, int friendWall, int foeWall) {
    	Set<Point> wallOfFoes = new HashSet<>();
    	int walker = origin;
    	QLog.log("model", "shieldWallCompetitionRow", "suche linkes Ende");
    	// finde anfang
    	while (getPieceAt(foeWall, walker).party == currentEnemy) {
    		QLog.log("", "", "" + walker);
    		walker--;
    	}
    	QLog.log("", "", "linkes Ende ist auf " + walker);
    	
    	// falls anfang legitim ist
    	if (getPieceAt(foeWall, walker).party == activeParty || getSquareAt(foeWall, walker) == SquareType.ESCAPE) {
    		QLog.log("", "", "foe wall ist links eingekesselt");
    		QLog.log("", "", "walker geht einen Schritt nach rechts");
    		walker++;
    		// gehe durch die Reihe
    		while (getPieceAt(foeWall, walker).party == currentEnemy && getPieceAt(friendWall, walker).party == activeParty) {
    			wallOfFoes.add(new Point(foeWall,walker));
    			QLog.log("", "", "" + walker);
    			walker++;
    		}
    	}
    	// checke ob die Wall irgendwo bricht
    	if (getPieceAt(foeWall, walker).party != activeParty && getSquareAt(foeWall, walker) != SquareType.ESCAPE) {
    		wallOfFoes.clear();
    	}
    	return wallOfFoes;
    }
    
    
    private Set<Point> shieldWallCompetitionCol(int friendWall, int origin, int foeWall) {
    	Set<Point> wallOfFoes = new HashSet<>();
    	int walker = origin;
    	QLog.log("model", "shieldWallCompetitionRow", "suche nördlichen Ende");
    	// finde anfang
    	QLog.log("", "", "Piece auf (" + walker + "," + foeWall + ") gehört zu " + getPieceAt(walker, foeWall).party);
    	while (getPieceAt(walker, foeWall).party == currentEnemy) {
    		QLog.log("", "", "" + walker);
    		walker--;
    	}
    	QLog.log("", "", "nördliches Ende ist auf " + walker);
    	
    	// falls anfang legitim ist
    	if (getPieceAt(walker, foeWall).party == activeParty || getSquareAt(walker, foeWall) == SquareType.ESCAPE) {
    		QLog.log("", "", "foe wall ist nördlich eingekesselt");
    		QLog.log("", "", "walker geht einen Schritt nach süden");
    		walker++;
    		// gehe durch die Reihe
    		while (getPieceAt(walker, foeWall).party == currentEnemy && getPieceAt(walker, friendWall).party == activeParty) {
    			wallOfFoes.add(new Point(walker, foeWall));
    			QLog.log("", "", "" + walker);
    			walker++;
    		}
    	}
    	// checke ob die Wall irgendwo bricht
    	if (getPieceAt(walker, foeWall).party != activeParty && getSquareAt(walker, foeWall) != SquareType.ESCAPE) {
    		wallOfFoes.clear();
    	}
    	return wallOfFoes;
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
    public Point getDropPos() {
    	return dropPos;
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
		
		switch (this.currentMode){
			case GRAB_PIECE:
				QLog.log("model", "onSquareClicked", "weil im GRAB_PIECE-Mode -> grabPiece from " + row + "," + col + ".");
				grabPiece(row,col);
				break;
			case MOVE_PIECE:
				QLog.log("model", "onSquareClicked[1/3]", "case MOVE_PIECE: -> moveCurrentPieceTo(row,col)");
				moveCurrentPieceTo(row,col);
				// -> check ob gewinnbedingung erfüllt wurde
				QLog.log("model", "onSquareClicked[2/3]", "case MOVE_PIECE: -> simpleCapture");
				// -> capture all trapped enemies
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
		
	}

	private void trapAllEnemies(int row, int col){
		// QLog.log("model", "trapAllEnemies", "-> simpleCapture");
		
		// rules.applyAll(this);				//					TODO Issue #26 die ganze ServiceLoader Geschichte funktioniert nicht. 
		simpleCapture(row,col);
		escapeSquareCapture(row, col);
		throneSquareCapture(row, col);
		shieldWallCapture(row,col);
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
