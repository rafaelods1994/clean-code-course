package com.d.tdd.rafa;

public class StringHelper {

	public String truncateAatFirstTwoPositions(String string) {

		if (string.length() < 2) {
			return string.replaceAll("A", "");
		}

		String firstTwoCharacters = string.substring(0, 2);
		String restOfTheString = string.substring(2);
		return firstTwoCharacters.replaceAll("A", "") + restOfTheString;
	}

	public boolean areFirstTwoCharactersSameAsLastTwo(String string) {

		int length = string.length();
		if (length < 2) {
			return false;
		}

		String firstTwoCharacters = string.substring(0, 2);
		String lastTwoCharacters = string.substring(length - 2);

		return firstTwoCharacters.equals(lastTwoCharacters);
	}

}
