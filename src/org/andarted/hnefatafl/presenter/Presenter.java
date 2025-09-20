package org.andarted.hnefatafl.presenter;

import org.andarted.hnefatafl.view.IView;

import java.awt.Color;


public class Presenter implements IPresenter {
    private final IView view;
    private String[][] board = new String[11][11];
    
    private final char charForAnarchist = 'A';
    private final char charForRoyalist = 'R';
    private final char charForKing = 'K';
    private final char charForEmptySpace = '.';
    private char currentPiece = charForEmptySpace;
    private char activePlayer = currentPiece;

    public Presenter(IView view) {
        this.view = view;
        // Einfach initialisieren (Model-Teil, Beispiel)
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                board[i][j] = ".";
            }
        }
    }
    
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

        System.out.println("Presenter: Feld (" + row + "," + col + ") gesetzt!");
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
    }
    
    
    @Override
    public void handleNewGameItem(int size, boolean altSetUp) {
    	view.initializeNewGame(size);
    }
    
    @Override
    public void handleExitItem() {
    	System.exit(0);
    }
    
    @Override
    public void handleDebugGetRoyalist() {
    	currentPiece = 'R';
    }
    
    @Override
    public void handleDebugGetAnarchist() {
    	currentPiece = 'A';
    }
    
    @Override
    public void handleDebugGetKing() {
    	currentPiece = 'K';
    }
    
    @Override
    public void handleDebugGetRemove() {
    	currentPiece ='.';
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
		view.highlightReach(3, 3, 1, 7, 2, 5); // temp tests:
	}
	
	@Override
	public void handleDebugShowAnarchistDeathZoneButton() {
		// TODO Auto-generated method stub
		view.delegateClearHighlight(); // temp tests:
	}
}