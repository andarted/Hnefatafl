package org.andarted.hnefatafl.view;

interface IListen {
	// Generel
	void setIListen(IListen iListen);
	void setBoardPanelListener(BoardPanelListener boardPanelListener);
	void setSidePanelListener(SidePanelListener sidePanelListener);
	
	//BoardPanel
	void onFieldClick(int row, int col);
	void delegateOnFieldHoverToView(int row, int col, int screenX, int screenY);
	
	// SidePanel
	void clickOnToggleActivePartyButton();
	void clickOnFreeMovementButton();
	void clickOnGetAnarchistButton();
	void clickOnGetRoyalistButton();
	void clickOnGetKingButton();
	void clickOnGetRemoveButton();
	void clickOnShowRoyalistDeathZoneButton();
	void clickOnShowAnarchistDeathZoneButton();
	
}
