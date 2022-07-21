package com.luv2code.tdd;

public class FizzBuzz {

	public static String divided(int number) {

		StringBuilder returnValue = new StringBuilder();

		if (0 == number) {
			return null;
		}

		if (isDivisibleFor3(number)) {
			returnValue.append("Fizz");
		}

		if (isDivisibleFor5(number)) {
			returnValue.append("Buzz");
		}

		if (returnValue.isEmpty()) {
			returnValue.append(number);
		}

		return returnValue.toString();
	}

	private static boolean isDivisibleFor5(int number) {
		return 0 == number % 5;
	}

	private static boolean isDivisibleFor3(int number) {
		return 0 == number % 3;
	}

}
