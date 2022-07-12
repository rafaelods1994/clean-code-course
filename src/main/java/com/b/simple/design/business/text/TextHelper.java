package com.b.simple.design.business.text;

public class TextHelper {

	public String swapLastTwoCharacters(String text) {
		if (isTextBlankOrMoreThanOneCharacter(text)) {
			return text;
		}

		char[] textToChar = text.toCharArray();

		int textLength = textToChar.length - 1;
		char lastCharacter = textToChar[textLength];

		textToChar[textLength] = textToChar[textLength - 1];
		textToChar[textLength - 1] = lastCharacter;

		return String.valueOf(textToChar);
	}

	private boolean isTextBlankOrMoreThanOneCharacter(String text) {
		return text.isBlank() || text.length() == 1;
	}

	public String truncateAInFirst2Positions(String str) {
		if (isTextBlankOrMoreThanOneCharacter(str)) {
			return str.replaceAll("A", "");
		}
		String twoFirstPositions = str.substring(0, 2);
		String restOfString = str.substring(2);

		String twoFirstPositionAfterReplacing = twoFirstPositions.replaceAll("A", "");

		return twoFirstPositionAfterReplacing + restOfString;
	}
}
