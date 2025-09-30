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
    
    
    @Override
    public void onSquareClicked(int row, int col) {

        System.out.println("Presenter: Kilck auf Feld (" + row + "," + col + ").");
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
    
    
    // - - - DEBUG BUTTONS - - -
    
	@Override
	public void handleToggleActivePartyButton() {
		QLog.log("presenter", "handleToggleActivePartyButton()", "-> model.toggleActiveParty()");
		model.debugPanelToggleActiveParty();
		QLog.log("presenter", "handleToggleActivePartyButton()", "-> view.setActivePartyDisplay");
		view.setActivePartyDisplay(model.getActiveParty());
	}
    
    @Override
    public void handleDebugGetRoyalist() {
    	currentPiece = PieceType.ROYALIST;
		System.out.println("Presenter: debug set royalist pieces is now active");
		view.updateDebugDisplay("Mode: Drop Royalists");
    }
    
    @Override
    public void handleDebugGetAnarchist() {
    	currentPiece = PieceType.ANARCHIST;
		System.out.println("Presenter: debug set anarchist pieces is now active");
		view.updateDebugDisplay("Mode: Drop Anarchists");
    }
    
    @Override
    public void handleDebugGetKing() {
    	currentPiece = PieceType.KING;
		System.out.println("Presenter: debug set king pieces is now active");
		view.updateDebugDisplay("Mode: Drop Kings");
    }
    
    @Override
    public void handleDebugGetRemove() {
    	currentPiece = PieceType.NOBODY;
		System.out.println("Presenter: debug remove pieces is now active");
		view.updateDebugDisplay("Mode: Remove Pieces");
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
	
	@Override
	public String getActiveParty() {
		return model.getActiveParty();
	}
	
	
}