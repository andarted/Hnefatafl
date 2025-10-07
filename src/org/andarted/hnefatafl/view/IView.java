package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.model.GameBoard;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.presenter.Presenter;

public interface IView {
	
	void initializeView();
	// void updateDebugDisplay(String string);
	void initializeNewGame(GameBoard gameBoard);
	void setActivePartyDisplay(String activeSide);
	
	void setDebugModeTButton(boolean enabled);
	
	void congratWin(String string);

	void highlightReach(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol);

	void initializePresenter(Presenter presenter);
	void setGameBoard();
	void onFieldHover(int row, int col, int screenX, int screenY);

	void delegateRepaint();
	void delegateOnFieldHoverToPresenter(int row,int col);
	
	SquareType getSquareAt(int row, int col);
	PieceType getPieceAr(int row, int col);
	
}
