package org.andarted.hnefatafl.presenter;

import org.andarted.hnefatafl.common.Variant;

public interface IPresenter {
    void onSquareClicked(int row, int col);
    
    void handleNewGameItem(int size, Variant variant);
    void handleExitItem();    
    
    void handleDebugSkipButton();
    void handleDebugFreeMovementButton();
    
    void handleDebugGetRoyalist();
    void handleDebugGetAnarchist();
    void handleDebugGetKing();
    void handleDebugGetRemove();
    
    void handleDebugShowRoyalistDeathZoneButton();
    void handleDebugShowAnarchistDeathZoneButton();
    
    // void deligateInitializeView();
    
    // void startDefaultGame();
}