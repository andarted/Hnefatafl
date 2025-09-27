package org.andarted.hnefatafl.view;

interface IListen {
	//BoardPanel
	void onFieldClick(int row, int col);
	void delegateOnFieldHoverToView(int row, int col, int screenX, int screenY);
	
	// SidePanel
	void clickOnSkipButton();
	void clickOnFreeMovementButton();
	void clickOnGetAnarchistButton();
	void clickOnGetRoyalistButton();
	void clickOnGetKingButton();
	void clickOnGetRemoveButton();
	void clickOnShowRoyalistDeathZoneButton();
	void clickOnShowAnarchistDeathZoneButton();
}
