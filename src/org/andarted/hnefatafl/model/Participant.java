package org.andarted.hnefatafl.model;

public enum Participant {
	ANARCHISTS,
	ROYALISTS,
	NEUTRAL;

	public Participant getOpponent(){
		switch (this) {
		case ANARCHISTS: return ROYALISTS;
		case ROYALISTS: return ANARCHISTS;
		case NEUTRAL: return NEUTRAL;
		default: throw new IllegalStateException("Unexpected Participant: " + this);
		}
	}
}