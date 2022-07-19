package com.luv2code.junitdemo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class DemoUtilsTest {

	private DemoUtils utils;

	@BeforeEach
	void setupBeforeEach() {
		utils = new DemoUtils();
	}

	@Test
	void test_add_method() {

		assertEquals(0, utils.add(0, 0));
		assertEquals(6, utils.add(2, 4), "2+4 must be 6");
		assertNotEquals(6, utils.add(1, 9), "1+9 must not be 6");

	}

	@Test
	void test_check_null() {
		assertNotNull(utils.checkNull(""));
		assertNull(utils.checkNull(null));
	}

	@Test
	void test_same_and_not_same() {

		assertSame(utils.getAcademy(), utils.getAcademyDuplicate());

		assertNotSame("luv2code", utils.getAcademyDuplicate());

	}

	@Test
	void test_is_greater_true_and_is_greater_false() {
		assertTrue(utils.isGreater(10, 5));
		assertFalse(utils.isGreater(5, 10));
	}

	@Test
	void test_if_array_is_equals() {
		String[] stringArray = { "A", "B", "C" };
		assertArrayEquals(stringArray, utils.getFirstThreeLettersOfAlphabet());
	}

	@Test
	void test_if_iterator_is_equals() {
		// Iterator (List, LinkedList, ArrayList, Hashset...)
		List<String> stringList = List.of("luv", "2", "code");

		assertIterableEquals(stringList, utils.getAcademyInList());
	}

	@Test
	void test_if_list_match() {
		List<String> stringList = List.of("luv", "2", "code");

		assertLinesMatch(stringList, utils.getAcademyInList());
	}

	@Test
	void test_throws_and_does_not_throw() {

		assertThrows(Exception.class, () -> {
			utils.throwException(-1);
		});
		assertDoesNotThrow(() -> {
			utils.throwException(5);
		});

	}

	@Test
	void test_timeout() {
		assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
			utils.checkTimeout();
		});
	}

	@Test
	void test_multiply() {
		assertEquals(4, utils.multiply(2, 2));
	}

}
