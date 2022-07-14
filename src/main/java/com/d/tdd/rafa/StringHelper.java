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

}
