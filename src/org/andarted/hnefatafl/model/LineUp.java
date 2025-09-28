package org.andarted.hnefatafl.model;

enum LineUp {
	SIZE_7_STANDART(7, new String[] {
			"..AAA..",
			"...A...",
			"A.RRR.A",
			"AARKRAA",
			"A.RRR.A",
			"...A...",
			"..AAA.."
	}),
	
	SIZE_7_ALTERNATIVE(7, new String[] {
			"..A.A..",
			".......",
			"A..R..A",
			"..RKR..",
			"A..R..A",
			".......",
			"..A.A.."
	}),

	SIZE_9_STANDART(9, new String[] {
			"...AAA...",
			"....A....",
			"....R....",
			"A...R...A",
			"AARRKRRAA",
			"A...R...A",
			"....R....",
			"....A....",
			"...AAA..."
	}),
	
	SIZE_9_ALTERNATIVE(9, new String[] {
			"...A.A...",
			"..A...A..",
			".A..R..A.",
			"A...R...A",
			"..RRKRR..",
			"A...R...A",
			".A..R..A.",
			"..A...A..",
			"...A.A..."
	}),
	
	SIZE_11_STANDART(11, new String[] {
			"...AAAAA...",
			".....A.....",
			"...........",
			"A....R....A",
			"A...RRR...A",
			"AA.RRKRR.AA",
			"A...RRR...A",
			"A....R....A",
			"...........",
			".....A.....",
			"...AAAAA..."
	}),
	
	SIZE_11_ALTERNATIVE(11, new String[] {
			"....A.A....",
			"...A.A.A...",
			"..A..R..A..",
			".A...R...A.",
			"A....R....A",
			".ARRRKRRRA.",
			"A....R....A",
			".A...R...A.",
			"..A..R..A..",
			"...A.A.A...",
			"....A.A...."
	}),
	
	SIZE_13_STANDART(13, new String[] {
			"...AAAAAAA...",
			"......A......",
			".............",
			"......R......",
			"A.....R.....A",
			"A....RRR....A",
			"AA.RRRKRRR.AA",
			"A....RRR....A",
			"A.....R.....A",
			"......R......",
			".............",
			"......A......",
			"...AAAAAAA..."	
	}),
	
	SIZE_13_ALTERNATIVE(13, new String[] {
			".....A.A.....",
			"..A.A...A.A..",
			".A.A..R..A.A.",
			"..A...R...A..",
			".A....R....A.",
			"A.....R.....A",
			"..RRRRKRRRR..",
			"A.....R.....A",
			".A....R....A.",
			"..A...R...A..",
			".A.A..R..A.A.",
			"..A.A...A.A..",
			".....A.A....."
	});
	
	// - - - ATTRIBUTES - - - 
	
	private final int size;
	private final String[] lineUp;
	
	
	// - - - CONSTUCTOR - - -
	
	LineUp(int size, String[] lineUp) {
		this.size = size;
		this.lineUp = lineUp;	
	}
	
	
	// - - - GETTER - - -
	
	public int getSize() {
		return size;
	}
	
	public String[] getLineUpString() {
		return lineUp;
	}

}