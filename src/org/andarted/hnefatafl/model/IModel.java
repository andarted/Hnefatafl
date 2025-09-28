package org.andarted.hnefatafl.model;

import org.andarted.hnefatafl.common.GameBoard;
import org.andarted.hnefatafl.common.PieceType;
import org.andarted.hnefatafl.common.Variant;

// import org.andarted.hnefatafl.presenter.Presenter;

public interface IModel {

	GameBoard newGameBoard(int size, Variant variant);
	GameBoard newGame(int size, Variant variant);
	GameBoard newDefaultGame();

	void setFreshLineUp(int size, Variant variant);
	void setPiece(PieceType pieceType, int row, int col);
	
	void delegateSetMouseHoverPos(int row,int col);
	
	// void initializePresenter(Presenter presenter);
	
}
