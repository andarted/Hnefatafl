package org.andarted.hnefatafl.model;

public enum PieceType {
	NOBODY(Participant.NEUTRAL),
	ANARCHIST(Participant.ANARCHISTS),
	ROYALIST(Participant.ROYALISTS),
	KING(Participant.ROYALISTS),
	PIECE_ERROR(Participant.NEUTRAL);
	
	final Participant party;
	
	PieceType (Participant party){
		this.party = party;	
	}
}
