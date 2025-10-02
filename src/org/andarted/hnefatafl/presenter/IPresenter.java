package org.andarted.hnefatafl.presenter;

import org.andarted.hnefatafl.model.GameBoard;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.SquareType;
import org.andarted.hnefatafl.model.Variant;

public interface IPresenter {
    void onSquareClicked(int row, int col);
    
    void handleNewGameItem(int size, Variant variant);
    void handleExitItem();    
    
    void handleToggleActiveParty();
    void handleDebugFreeMovementButton();
    
    void handleDebugGetRoyalist();
    void handleDebugGetAnarchist();
    void handleDebugGetKing();
    void handleDebugGetRemove();
    
    void handleDebugShowRoyalistDeathZoneButton();
    void handleDebugShowAnarchistDeathZoneButton();
    
    void onFieldHover(int row, int col);
    
    int getBoardSize();
    GameBoard getGameBoard();
    SquareType getSquareAt(int row, int col);
    PieceType getPieceAr(int row, int col);
    String getActiveParty();
    
    void startDefaultGame();
    
    
    
    // void deligateInitializeView();
    
    // void startDefaultGame();
}