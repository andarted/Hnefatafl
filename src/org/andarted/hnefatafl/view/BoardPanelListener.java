package org.andarted.hnefatafl.view;

interface BoardPanelListener {										// Listener Interface wird in der Methode
																	// view.initializeListener() / setBoardPanelListener "implementiert"
	void onFieldClick(int row, int col);
	void delegateOnFieldHoverToView(int row, int col, int screenX, int screenY);
}
