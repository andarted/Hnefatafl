package org.andarted.hnefatafl.model;

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
	void debugSetPiece(PieceType pieceType);
	void setMode(ModeType mode);
	
	void delegateSetMouseHoverPos(int row,int col);
	
	// void initializePresenter(Presenter presenter);
	
	void debugPanelToggleActiveParty();
	void grabPiece(int row, int col);
	
	void onSquareClicked(int row, int col);
}
