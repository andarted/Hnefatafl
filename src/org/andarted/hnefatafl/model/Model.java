package org.andarted.hnefatafl.model;

import java.awt.Point;
import java.util.ArrayList;

import org.andarted.hnefatafl.common.GameBoard;
import org.andarted.hnefatafl.common.PieceType;
import org.andarted.hnefatafl.common.TraceLogger;
import org.andarted.hnefatafl.presenter.IPresenter;
import org.andarted.hnefatafl.common.LineUp;
import org.andarted.hnefatafl.common.Variant;

public class Model implements IModel {
	
	// - - - ATTRIBUTES - - -
	
	private IPresenter presenter;
	private GameBoard gameBoard;
	private String[] LineUpAsStringArray;
	private PieceType[][] currentState;
	private PieceType[][] lineUpPieceTypeMatrix;
	
	private char[][] lineUpCharMatrix;

	
	// - - - CONSTRUCTOR - - -
	public Model() {
		
	}
	
	
	// - - - METHODES - - - 
	
    public void initializePresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
    
    @Override
    public GameBoard newGame(int size, Variant variant) {
    	this.gameBoard = new GameBoard(size, variant);
    	setlineUp(size, variant);
    	return gameBoard;
    }
    
    @Override
    public GameBoard newGameBoard(int size, Variant variant) {
    	
    	this.gameBoard = new GameBoard(size, variant);
    	
    	recallSpecificLineUp(size, variant);
    	
    	
    	return gameBoard;
    }
    
    /*
    private void generateLineUp(LineUp lineUp, Variant variant) {
    	this.currentState = LineUpFactory.createLineUp(lineUp, variant);
    }
    */
    
    // - - - METHODS / LINE UP PIECES - - -
    
    private void setlineUp(int size, Variant variant){
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
    		return PieceType.ERROR;
    		
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
    	return gameBoard;
    }

    
    // - - - SETTER - - -
    
    public void setFreshLineUp(int size, Variant variant) {	
    	// generateLineUp(lineUpFromSize(size), variant);
    }
    
    public void setPiece(PieceType pieceType, int row, int col) {
    	this.currentState[row][col] = pieceType;
    }


	@Override
	public void delegateSetMouseHoverPos(int row, int col) {
		TraceLogger.log("model", "delegateSetMouseHoverPos:", true, "gameBoard.setMouseHoverPos()");
		gameBoard.setMouseHoverPos(row,col);
	}
}
