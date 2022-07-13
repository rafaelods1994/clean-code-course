package com.c.refactoring.movie;

import com.c.refactoring.StringUtils;

public class Movie {

	String rating;

	public Movie(String rating) {
		super();
		this.rating = rating;
	}

	public String getRating() {
		return rating;
	}

	/*
	 * Axx or By Where x represents any digit between 0 and 9, and y represents any
	 * digit between 1 and 4
	 */
	public boolean isValidRating() {

		if (null == this.getRating()) {
			return false;
		}

		if (isValidARating()) {
			return true;
		}

		if (isValidBRating()) {
			return true;
		}

		return false;
	}

	private boolean isValidBRating() {
		if (isFirstDigitEqualsTo("B") && isLengthEqualsTo(2)) {
			if (isNumeric(1, 2) && isNumberLowerThanAndHigherThan(5, 0)) {

				return true;
			}

		}
		return false;
	}

	private boolean isValidARating() {

		return isFirstDigitEqualsTo("A") && isLengthEqualsTo(3) && isNumeric(1, 3);
	}

	private String getSubStringRating(int beginIndex, int lastIndex) {
		return this.getRating().substring(beginIndex, lastIndex);
	}

	private boolean isNumberLowerThanAndHigherThan(int number1, int number2) {
		int subStringRating = Integer.parseInt(getSubStringRating(1, 2));
		return subStringRating < number1 && subStringRating > number2;
	}

	private boolean isFirstDigitEqualsTo(String letter) {
		return this.getRating().substring(0, 1).equalsIgnoreCase(letter);
	}

	private boolean isLengthEqualsTo(int length) {
		return this.getRating().length() == length;
	}

	private boolean isNumeric(int beginIndex, int lastIndex) {
		return StringUtils.isNumeric(getSubStringRating(beginIndex, lastIndex));
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}
