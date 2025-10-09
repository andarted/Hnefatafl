package org.andarted.hnefatafl.model;


class LineUpGenerator {
	
	private LineUpGenerator() {}
	

	public static PieceType[][] generate(int size, Variant variant){
		String[] lineUpAsStrings;
		char[][] lineUpAsChars;
		PieceType[][] lineUp;
		
		lineUpAsStrings = recallSpecificLineUp(size, variant);
		lineUpAsChars = convertToCharMatrix(lineUpAsStrings);
		lineUp = convertToPieceTypeMatrix(lineUpAsChars);
		return lineUp;
	}
	
	
	private static String[] recallSpecificLineUp(int size, Variant variant) {
		switch (size) {
			case 7: return variant.thisIsTheAlt() ? LineUp.SIZE_7_ALTERNATIVE.getLineUpString()
					: LineUp.SIZE_7_STANDART.getLineUpString();
				
			case 9: return variant.thisIsTheAlt() ? LineUp.SIZE_9_ALTERNATIVE.getLineUpString()
					: LineUp.SIZE_9_STANDART.getLineUpString();
				
			case 11: return variant.thisIsTheAlt() ? LineUp.SIZE_11_ALTERNATIVE.getLineUpString()
					: LineUp.SIZE_11_STANDART.getLineUpString();
				
			case 13: return variant.thisIsTheAlt() ? LineUp.SIZE_13_ALTERNATIVE.getLineUpString()
					: LineUp.SIZE_13_STANDART.getLineUpString(); 
				
			default:
				throw new IllegalArgumentException("Model: Gibt kein LineUp für " + size + " && " + variant);
		}
	}
	
	
	private static char[][] convertToCharMatrix(String[] stringArray){
		int rows = stringArray.length;
		int cols = findMaxCol(stringArray);
		char[][] charMatrix = new char[rows][cols];
		
		for (int row = 0; row < rows; row++) {
				charMatrix[row] = stringArray[row].toCharArray();
			}
		return charMatrix;
	}
	
	
	private static int findMaxCol(String[] stringArray) {
		int max = 0;
		int numberOfStrings = stringArray.length;
		for (int currentString = 0; currentString < numberOfStrings; currentString++) {
			if (max < stringArray[currentString].length()) {
				max = stringArray[currentString].length();
			}
		}
		return max;
	}
	
	
	private static PieceType[][] convertToPieceTypeMatrix(char[][] lineUpAsChars){
		int rows = lineUpAsChars.length;
		int cols = lineUpAsChars[0].length;
		PieceType[][] lineUp = new PieceType[rows][cols];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				lineUp[row][col] = mapCharToPieceType(lineUpAsChars[row][col]);
			}
		}
		return lineUp;
	}


	private static PieceType mapCharToPieceType(char lineUpAsChars) {		
		switch (lineUpAsChars) {
		case '.': return PieceType.NOBODY;
		case 'A': return PieceType.ANARCHIST;
		case 'R': return PieceType.ROYALIST;
		case 'K': return PieceType.KING;
		
		default:
			throw new IllegalArgumentException("Unknown piece symbol: " + lineUpAsChars);
//			System.out.println("model.recallPieceAt(): How the Fuck messed I up???");
//			return PieceType.PIECE_ERROR;		
		}
	}

}