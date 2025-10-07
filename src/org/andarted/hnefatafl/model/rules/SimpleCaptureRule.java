package org.andarted.hnefatafl.model.rules;

import org.andarted.hnefatafl.common.QLog;
import org.andarted.hnefatafl.model.Participant;
import org.andarted.hnefatafl.model.PieceType;
import org.andarted.hnefatafl.model.Direction;
import org.andarted.hnefatafl.model.Model;

public class SimpleCaptureRule implements IRule{
	private int row;
	private int col;
	
	public SimpleCaptureRule() {
	}
	
	public void apply(Model model) {
		this.row = model.getClickX();
		this.col = model.getClickY();
		
    	// QLog.log("model", "simpleCatch", "checke ob (" + row + "," + col + ") innerhalb des Boards ist.");
    	if (!model.isInsideBoard(row,col)) return;
    	// QLog.log("model", "simpleCatch", "ausgangsfeld (" + row + "," + col + ") ist schon mal innerhalb des Boards.");
    	for (Direction dir : Direction.values()) {
    		// QLog.log("rules", "apply (simpleCaptureR)", "schauen wir uns mal die richtung " + dir.toString() + " an.");
    		tryCapture(model, row, col, dir);
    	}
	}
	
    
    private void tryCapture(Model model, int row, int col, Direction dir) {
    	int row1 = row + dir.dRow;
    	int col1 = col + dir.dCol;
    	int row2 = row + dir.dRow * 2;
    	int col2 = col + dir.dCol * 2;
    	
    	if (!model.isInsideBoard(row2, col2)) return;
    	
    	// QLog.log("model", "tryCapture", "amboss feld (" + row2 + "," + col2 + ") ist auch innerhalb des Boards.");
    	if (model.getPartyAt(row1, col1) == model.getCurrentEnemy() &&
    		model.getPartyAt(row2, col2) == model.getActiveParty()) {
    		
    		System.out.println("");
    		QLog.log("(╯•̀ᗜ•́)–≖{=====>", " ε=ε=ε= ┌(°□°)╯", " ⛊(•̀_•́)⎞");
    		QLog.log(" " + model.getPartyAt(row, col), " " + model.getPartyAt(row1, col1), " " + model.getPartyAt(row2, col2));
    		QLog.log(" " + row + "," + col, " " + row1 + "," + col1, " " + row2 + "," + col2);
    		System.out.println("");

        		model.setPiece(PieceType.NOBODY, row1, col1);
    	}
    }
}