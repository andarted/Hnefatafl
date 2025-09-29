package org.andarted.hnefatafl.presenter;

import org.andarted.hnefatafl.model.GameBoard;
import org.andarted.hnefatafl.model.IModel;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.view.IView;
import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.common.TraceLogger;
import org.andarted.hnefatafl.common.Variant;


import java.awt.Color;


public class Presenter implements IPresenter {
    private final IView view;
    private final IModel model;
    private GameBoard gameBoard;
    // private String[][] board = new String[11][11];
    
    private final char charForAnarchist = 'A';
    private final char charForRoyalist = 'R';
    private final char charForKing = 'K';
    private final char charForEmptySpace = '.';
    private char currentPieceChar = charForEmptySpace;
    private char activePlayer = currentPieceChar;
    
    private PieceType currentPiece = PieceType.NOBODY;
    
    private boolean fieldHoverIsActive = false;
    
    
    // - - - CONSTRUCTOR - - -
    
    public Presenter(IView view, IModel model) {
    	this.model = model;
    	this.view = view;
        startDefaultGame();
        QLog.log("presenter", "", "presenter wird konstruiert, initialisiert model & view, -> model.startDefaultGame()");
    }

    
    // - - - METHODEN - - -
    
    @Override
	public void startDefaultGame() {
		QLog.log("presenter", "startDefaultGame()", "-> model.newDefaultGame()");
		this.gameBoard = model.newDefaultGame();
		/*
		view.initializeView();
		view.setGameBoard();
		*/
	}
    
	
	/*
    private void deligateSetPiece(PieceType pieceType, int row, int col) {
    	switch (pieceType) {
    	case NOBODY: model.setPiece(pieceType, row, col);
    	}
    }
    */
    
    // - - - DEBUG BUTTONS - - -
    
    void updateDisplayToAnarchists() {
		activePlayer = charForAnarchist;
		view.updateDebugDisplay("Anarchists");
		System.out.println("Presenter: Anarchists turn");
	}
	
	void updateDisplayToRoyalists() {
		activePlayer = charForRoyalist;
		System.out.println("Presenter: Royalists turn");
		view.updateDebugDisplay("Royalists");
	}


    @Override
    public void onSquareClicked(int row, int col) {

        System.out.println("Presenter: Kilck auf Feld (" + row + "," + col + ").");
        /*
        switch (currentPiece) {
        case 'A':
        	view.setAnarchist(row, col);
        	break;
        case 'R':
        	view.setRoyalist(row, col);
        	break;
        case 'K':
        	view.setKing(row, col);
        	break;
        case '.':
        	view.removePiece(row, col);
        	break;
        	}
        */
    }
    
    
    @Override
    public void handleNewGameItem(int size, Variant variant) {
    	this.gameBoard = model.newGame(size, variant);
    	System.out.println("Presenter: handleNewGameItem (" + size + ", " + variant.toString() + ")");
    	    	
    	view.initializeNewGame(model.getGameBoard());
    	view.setGameBoard();
    }
    
    @Override
    public void handleExitItem() {
    	System.exit(0);
    }
    
    @Override
    public void handleDebugGetRoyalist() {
    	currentPiece = PieceType.ROYALIST;
		System.out.println("Presenter: debug set royalist pieces is now active");
		view.updateDebugDisplay("Mode: Drop Royalists");
		
    	currentPieceChar = 'R';
    }
    
    @Override
    public void handleDebugGetAnarchist() {
    	currentPiece = PieceType.ANARCHIST;
		System.out.println("Presenter: debug set anarchist pieces is now active");
		view.updateDebugDisplay("Mode: Drop Anarchists");
		
    	currentPieceChar = 'A';
    }
    
    @Override
    public void handleDebugGetKing() {
    	currentPiece = PieceType.KING;
		System.out.println("Presenter: debug set king pieces is now active");
		view.updateDebugDisplay("Mode: Drop Kings");
		
    	currentPieceChar = 'K';
    }
    
    @Override
    public void handleDebugGetRemove() {
    	currentPiece = PieceType.NOBODY;
		System.out.println("Presenter: debug remove pieces is now active");
		view.updateDebugDisplay("Mode: Remove Pieces");
		
    	currentPieceChar ='.';
    }

	@Override
	public void handleDebugSkipButton() {
		switch (activePlayer) {
		case 'A':
			updateDisplayToRoyalists();
			break;
		case 'R':
			updateDisplayToAnarchists();
			break;
		case 'K':
			updateDisplayToAnarchists();
			break;
		default:
			updateDisplayToRoyalists();		
		}
	}

	@Override
	public void handleDebugFreeMovementButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDebugShowRoyalistDeathZoneButton() {
		// TODO Auto-generated method stub
		// view.highlightReach(3, 3, 1, 7, 2, 5); // temp tests:
	}
	
	@Override
	public void handleDebugShowAnarchistDeathZoneButton() {
		// TODO Auto-generated method stub
		// view.delegateClearHighlight(); // temp tests:
	}

	@Override
	public void onFieldHover(int row, int col) {
		// gameBoard.setMouseHoverPosition(row, col);
		TraceLogger.log("presenter", "onFieldHover [1/2]:", true, "model.delegateSetMouseHoverPos()");
		model.delegateSetMouseHoverPos(row,col);
		
		TraceLogger.log("presenter", "onFieldHover [2/2]:", true, "view.delegateRepaint()");
		view.delegateRepaint();
		/*
		if(row != -1 && col != -1 || fieldHoverIsActive != false) {
			view.setMouseHoverHighlight(row, col);
		}
		*/
	}
	
	@Override
	public int getBoardSize() {
		return model.getBoardSize();
	}


	@Override
	public SquareType getSquareAt(int row, int col) {
		return model.getSquareAt(row, col);
	}


	@Override
	public PieceType getPieceAr(int row, int col) {
		return model.getPieceAt(row, col);
	}


	@Override
	public GameBoard getGameBoard() {
		return model.getGameBoard();
	}
	
	
}