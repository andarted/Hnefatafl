package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.model.PieceType;
import java.awt.Color;
import java.awt.Point;

public class PieceTypeAppearanceMapper {
	public static PieceAppearance getAppearance(PieceType type) {
		switch (type){
			case NOBODY:
				return new PieceAppearance(Color.WHITE, ' ', new Point(0,0));
			case ANARCHIST:
				return new PieceAppearance(Color.RED, 'A', new Point(2,0));
			case ROYALIST:
				return new PieceAppearance(Color.BLUE, 'R', new Point(2,1));
			case KING:
				return new PieceAppearance(Color.CYAN, 'K', new Point(2,2));
			case PIECE_ERROR:
				return new PieceAppearance(Color.BLACK, 'E', new Point(1,0));
			default:
				throw new IllegalArgumentException("Unknown PieceType: " + type);
		}
	}

}