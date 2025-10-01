package org.andarted.hnefatafl.model;

public enum Variant {
	STANDARD(false),
	ALTERNATIVE(true);

	private final boolean alternative;
	
	Variant(boolean alternative){
		this.alternative = alternative;
	}
	
	public boolean thisIsTheAlt() {
		return alternative;
	}
}
