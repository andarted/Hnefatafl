package org.andarted.hnefatafl.view;

interface BoardPanelListener { // Listener Interface wird in der Methode view.setBoardPanelListener "implementiert"
	void onFieldClick(int row, int col);
	void onMouseHover(int row, int col, int screenX, int screenY);
}
