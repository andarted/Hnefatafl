package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.presenter.Presenter;
import org.andarted.hnefatafl.common.GameBoard;

public interface IView {
	
	void initializeView();
	void updateDebugDisplay(String string);
	void initializeNewGame(GameBoard gameBoard);
	void setActivePlayerDisplay(String activeSide);
	/*
	void setAnarchist(int row, int col);
	void setRoyalist(int row, int col);
	void setKing(int row, int col);
	void removePiece(int row, int col);
	*/
	void congratWin(String string);
	// void highlightOnHoverSquare(int row, int col);
	void highlightReach(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol);
	/*
	void delegateClearHighlight();
	void delegateSetHighlightAt(int row, int col);
	void delegateClearHighlightAt(int row, int col);
	*/
	void initializePresenter(Presenter presenter);
	void setGameBoard(GameBoard gameBoard);
	void onFieldHover(int row, int col, int screenX, int screenY);
	void setMouseHoverPos(int row, int col);
	
	void delegateRepaint();
	void delegateOnFieldHoverToPresenter(int row,int col);

}
