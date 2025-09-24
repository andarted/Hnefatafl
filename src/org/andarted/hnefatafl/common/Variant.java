package org.andarted.hnefatafl.common;

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
