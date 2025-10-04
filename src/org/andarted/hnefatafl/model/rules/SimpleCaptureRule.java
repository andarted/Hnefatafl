package org.andarted.hnefatafl.model.rules;

import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.model.Participant;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.IModel;

class SimpleCaptureRule {
	
	private final IModel model;
	
    public SimpleCaptureRule(IModel model) {
    	this.model = model;
    }

	void simpleCapture(int row, int col) {
    	int rowN2 = row-2, rowN1 = row-1, rowS1 = row+1, rowS2 = row+2;
    	int colW2 = col-2, colW1 = col-1, colE1 = col+1, colE2 = col+2;
    	PieceType foe;
    	if (model.getActiveParty() == Participant.ANARCHISTS) {foe = PieceType.ROYALIST;}
    		else {foe = PieceType.ANARCHIST;}
    	
    	QLog.log("model", "simpleCapture", "CHECKE AB, OB ES EINE FIGUR ZU CATCHEN GIBT");
    	// !!! wichtig für folgende if: nur wegen reihenfolge der Bedingungen gibt's keine NullPointerExcaption !!!!
    	if (row > 1 && model.getPieceAt(rowN1, col) == foe && model.getPartyAt(rowN2, col) == model.getActiveParty()) { 
    		QLog.log("model", "simpleCapture", "capture piece north");
    		model.setPiece(PieceType.NOBODY, rowN1, col);
    	}
    	if (col < model.getBoardSize()-2 && model.getPieceAt(row, colE1) == foe && model.getPartyAt(rowS2, colE2) == model.getActiveParty()) {
    		QLog.log("model", "simpleCapture", "capture piece east");
    		model.setPiece(PieceType.NOBODY, row, colE1);
    	}
    	if (row < model.getBoardSize()-2 && model.getPieceAt(rowS1, col) == foe && model.getPartyAt(rowS2, col) == model.getActiveParty()) {
    		QLog.log("model", "simpleCapture", "capture piece south");
    		model.setPiece(PieceType.NOBODY, rowS1, col);
    	}
    	if (col > 1 && model.getPieceAt(row, colW1) == foe && model.getPartyAt(row, colW2) == model.getActiveParty()) {
    		QLog.log("model", "simpleCapture", "capture piece west");
    		model.setPiece(PieceType.NOBODY, row, colW1);
    	}
    }

}
