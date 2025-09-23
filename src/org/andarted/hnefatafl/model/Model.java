package org.andarted.hnefatafl.model;

import org.andarted.hnefatafl.common.GameBoard;
import org.andarted.hnefatafl.presenter.IPresenter;
import org.andarted.hnefatafl.presenter.Presenter;

public class Model implements IModel {
	
	// - - - ATTRIBUTES - - -
	
	private IPresenter presenter;
	private GameBoard gameBoard;

	
	// - - - CONSTRUCTOR - - -
	public Model() {
		
	}
	
	
	// - - - METHODES - - - 
	
    public void initializePresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
    
    public GameBoard newGame(int size, Variant variant) {
    	this.gameBoard = new GameBoard(size, variant);
    	return gameBoard;
    }
    
    
    // - - - GETTER - - -
    
    public GameBoard getGameBoard() {
    	return gameBoard;
    }

}
