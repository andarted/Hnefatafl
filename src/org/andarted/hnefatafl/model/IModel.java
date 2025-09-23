package org.andarted.hnefatafl.model;

import org.andarted.hnefatafl.common.GameBoard;

// import org.andarted.hnefatafl.presenter.Presenter;

public interface IModel {

	GameBoard newGame(int size, Variant variant);
	
	// void initializePresenter(Presenter presenter);
	
}
