package org.andarted.hnefatafl.model;

import java.util.Map;
import java.util.EnumMap;



public enum LineUp {
	SIZE_7(7,
			Map.of(
					Variant.STANDARD, new String[] {
							"AA..",
							"A..",
							"RR"
					},
					Variant.ALTERNATIVE, new String[] {
							".A..",
							"...",
							"R."
					})),
	
	SIZE_9(9,
			Map.of(
					Variant.STANDARD, new String [] {
							"AA...",
							"A...",
							"R..",
							"R."
					},
					Variant.ALTERNATIVE, new String[] {
							".A...",
							"..A.",
							"R..",
							"R."
					})),
	
	SIZE_11(11,
			Map.of(
					Variant.STANDARD, new String [] {
							"AAA...",
							"A....",
							"....",
							"R..",
							"RR"
					},
					Variant.ALTERNATIVE, new String[] {
							".A....",
							"A.A..",
							"R..A",
							"R..",
							"R."
					})),
	
	SIZE_13(13,
			Map.of(
					Variant.STANDARD, new String [] {
							"AAAA...",
							"A.....",
							".....",
							"R...",
							"R..",
							"RR"
					},
					Variant.ALTERNATIVE, new String[] {
							".A.....",
							"..A.A.",
							"R..A.",
							"R...",
							"R..",
							"R."
					}));
	
	
	// - - - ATTRIBUTES - - - 
	
	// private final int size;
	private final int height;
	private final int width;
	private final int heightQuadrant; 
	private final int widthQuadrant;
	private final int offset;
	private final Map<Variant, String[]> lineUps;
	
	private char[][] lineUpBoard;
	
	// - - - CONSTUCTOR - - -
	
	LineUp(int size, Map<Variant, String[]> lineUps) {
		// this.size = size;
		this.height = size;
		this.width = size;
		this.heightQuadrant = (size+1)/2;
		this.widthQuadrant = (size+1)/2;
		this.offset = size/2;
		// this.lineUp = lineUp;
		this.lineUps = new EnumMap<>(lineUps);
	}
	
	
	// - - - METHODS (PRIVATE) - - -
	
	private char[][] genLineUpBoard(String[] seed) {	
		
		this.lineUpBoard = new char[height][width]; 
		char[][] lineUpQuadrant = new char [heightQuadrant][widthQuadrant];
		
		lineUpQuadrant = genLineUpQuadrant(seed);
		
		mapAlphaQuadrant(lineUpQuadrant);
		mapBetaQuadrant(lineUpQuadrant);
		mapDeltaQuadrant(lineUpQuadrant);
		mapGammaQuadrant(lineUpQuadrant);
		
		placeKing(lineUpQuadrant);
		
		return lineUpBoard;
	}
	
	
	private char[][] genLineUpQuadrant(String[] seed){
		char[][] lineUpQuadrant = new char[height][width];
		
		transferSeedToQuadrant(seed, lineUpQuadrant);
		mirroredSeed(seed, lineUpQuadrant);

		return lineUpQuadrant;
	}
	
	private void transferSeedToQuadrant(String[] seed, char[][] quadrant) {
		// int height = this.height;
		for (int row = 0; row < heightQuadrant; row++) {
			for (int col = 0; col < widthQuadrant; col++) {
				quadrant[row][col] = seed[row].charAt(col);
			}
		}
	}
	
	private void mirroredSeed(String[] seed, char[][] quadrant) {
		// int height = this.height;
		for (int row = 0; row < heightQuadrant; row++) {
			for (int col = 0; col < widthQuadrant; col++) {
				quadrant[row][col] = seed[col].charAt(row);
			}
		}
	}
	
	private void mapAlphaQuadrant(char[][] lineUpQuadrant) {
		for (int row = 0; row < heightQuadrant; row++){
			for (int col = 0; col < widthQuadrant; col++) {
				lineUpBoard[row+offset][col] = lineUpQuadrant[height-row][width-col];
			}
		}
	}
	
	private void mapBetaQuadrant(char[][] lineUpQuadrant) {
		for (int row = 0; row < heightQuadrant; row++){
			for (int col = 0; col < widthQuadrant; col++) {
				lineUpBoard[row+offset][col+offset] = lineUpQuadrant[height-row][col];
			}
		}
	}
	
	private void mapDeltaQuadrant(char[][] lineUpQuadrant) {
		for (int row = 0; row < heightQuadrant; row++){
			for (int col = 0; col < widthQuadrant; col++) {
				lineUpBoard[row][col+offset] = lineUpQuadrant[row][col];
			}
		}
	}
	
	private void mapGammaQuadrant(char[][] lineUpQuadrant) {
		for (int row = 0; row < heightQuadrant; row++){
			for (int col = 0; col < widthQuadrant; col++) {
				lineUpBoard[row][col] = lineUpQuadrant[row][width-col];
			}
		}
	}
	
	private void placeKing(char[][] lineUpQuadrant) {
		lineUpBoard[heightQuadrant][widthQuadrant] = 'k';
	}
	
	private PieceType charToPieceType(char charForPiece) {
		switch(charForPiece) {
		case '.':
			return PieceType.NOBODY;
		case 'A':
			return PieceType.ANARCHIST;
		case 'R':
			return PieceType.ROYALIST;
		case 'K':
			return PieceType.KING;
		default:
			return PieceType.ERROR;
		}
		
	}
	
		
	// - - - GETTER - - -
	
	public PieceType[][] getLineUp(int size, Variant variant) {
		PieceType[][] lineUp = new PieceType[size][size];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				lineUp[row][col] = charToPieceType(lineUpBoard[row][col]);
			}
		}
		return lineUp;
	}
	
}

