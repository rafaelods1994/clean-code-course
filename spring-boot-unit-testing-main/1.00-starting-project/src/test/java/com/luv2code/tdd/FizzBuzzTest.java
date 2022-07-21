package com.luv2code.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class FizzBuzzTest {

	// If number is divisible by 3, print Fizz
	// If number is divisible by 5, print Buzz
	// If number is divisible by 3 and 5, print FizzBuzz
	// If number is NOT divisible by 3 or 5, then print the number

	@Test
	public void test_if_number_divisible_by_3_print_Fizz() {

		assertEquals("Fizz", FizzBuzz.divided(3));
		assertNull(FizzBuzz.divided(0));
	}

	@Test
	public void test_if_number_divisible_by_5_print_Buzz() {

		assertEquals("Buzz", FizzBuzz.divided(5));
		assertNull(FizzBuzz.divided(0));
	}

	@Test
	public void test_if_number_divisible_by_3_and_5_print_FizzBuzz() {

		assertEquals("FizzBuzz", FizzBuzz.divided(15));
	}

	@Test
	public void test_if_number_not_divisible_by_3_or_5_print_the_number() {

		assertEquals("7", FizzBuzz.divided(7));
	}

	@ParameterizedTest(name = "value{0},expected{1}")
	@CsvFileSource(resources="/small-test-data.csv")
	public void test_with_small_data_file_csv(int value, String expected) {

		assertEquals(expected, FizzBuzz.divided(value));
	}

	@ParameterizedTest(name = "value{0},expected{1}")
	@CsvFileSource(resources="/medium-test-data.csv")
	public void test_with_medium_data_file_csv(int value, String expected) {

		assertEquals(expected, FizzBuzz.divided(value));
	}
	
	@ParameterizedTest(name = "value{0},expected{1}")
	@CsvFileSource(resources="/large-test-data.csv")
	public void test_with_large_data_file_csv(int value, String expected) {

		assertEquals(expected, FizzBuzz.divided(value));
	}
}
