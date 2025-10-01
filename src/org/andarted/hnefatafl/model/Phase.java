package org.andarted.hnefatafl.model;

enum Phase {
	CHECK_IF_MOVE_IS_POSSIBLE,
	GRAB_PIECE,
	DROP_PIECE,
	CATCH_ENEMY,
	CHECK_WIN_CONDITION,
	END_TURN;
}

/*
 * Zug
 *  - checken, ob Zug überhaupt möglich ist.
 *  - nimm Figur
 *  - zeige mögliche neue Positionen
 *  - stelle Figur auf neue Position -/- breche ab
 *  - repaint
 *  - breechne neue Death Zone Gegner
 *  - entferne alle Figurne aus Deathzone
 *  - repaint
 *  - checke Siegbedingungen
 *  - wechsel aktive Spielerin
 *  
 *  
 */