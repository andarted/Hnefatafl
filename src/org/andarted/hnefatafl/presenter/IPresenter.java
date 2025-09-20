package org.andarted.hnefatafl.presenter;

public interface IPresenter {
    void onSquareClicked(int row, int col);
    void handleNewGameItem(int size, boolean altSetUp);
    void handleExitItem();    
    
    void handleDebugSkipButton();
    void handleDebugFreeMovementButton();
    
    void handleDebugGetRoyalist();
    void handleDebugGetAnarchist();
    void handleDebugGetKing();
    void handleDebugGetRemove();
    
    void handleDebugShowRoyalistDeathZoneButton();
    void handleDebugShowAnarchistDeathZoneButton();
}