package org.andarted.hnefatafl.model;

// import org.andarted.hnefatafl.common.PieceType;
import org.andarted.hnefatafl.common.Variant;

// import org.andarted.hnefatafl.presenter.Presenter;

public interface IModel {

	GameBoard newDefaultGame();
	GameBoard newGame(int size, Variant variant);

	GameBoard getGameBoard();
	SquareType getSquareAt(int row, int col);
	PieceType getPieceAt(int row, int col);

	int getBoardSize();
	String getActiveParty();
	
	void setFreshLineUp(int size, Variant variant);
	void setPiece(PieceType pieceType, int row, int col);
	
	void delegateSetMouseHoverPos(int row,int col);
	
	// void initializePresenter(Presenter presenter);
	
	void debugPanelToggleActiveParty();
	void grabPiece(int row, int col);
}
