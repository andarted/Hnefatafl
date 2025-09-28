package org.andarted.hnefatafl.view;

import org.andarted.hnefatafl.model.SquareType;
import java.awt.Color;
import java.awt.Point;

public class SquareTypeAppearanceMapper {
	public static SquareAppearance getAppearance(SquareType type) {
		switch (type) {
			case EMPTY:
				return new SquareAppearance(Color.WHITE, ' ', new Point (0,1));
			case THRONE:
				return new SquareAppearance(Color.LIGHT_GRAY, ' ', new Point (0,2));
			case ESCAPE:
				return new SquareAppearance(new Color(150, 150, 200), ' ', new Point(0,1));
			case MOUSE_HOVER:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,0));
			case HIGHLIGHTED:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			case HIGHLIGHTED_ORIGIN:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			case HIGHLIGHTED_XAXIS:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,2));
			case HIGHLIGHTED_YAXIS:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,3));
			case HIGHLIGHTED_PLANE:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			case ANARCHIST_DEATH_ZONE:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			case ROYALIST_DEATH_ZONE:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			case SQUARE_ERROR:
				return new SquareAppearance(Color.WHITE, ' ', new Point (1,1));
			default:
				throw new IllegalArgumentException("Unknown SquareType: " + type);
		}
	}

}
