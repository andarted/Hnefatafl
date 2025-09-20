package org.andarted.hnefatafl.view;

public interface IView {
	
	void initializeView();
	void updateDebugDisplay(String string);
	void initializeNewGame(int size);
	void setActivePlayerDisplay(String activeSide);
	void setAnarchist(int row, int col);
	void setRoyalist(int row, int col);
	void setKing(int row, int col);
	void removePiece(int row, int col);
	void congratWin(String string);
	void highlightReach(int originRow, int originCol, int fromRow, int toRow, int fromCol, int toCol);
	void delegateClearHighlight();
	void delegateSetHighlightAt(int row, int col);
	void delegateClearHighlightAt(int row, int col);

}
