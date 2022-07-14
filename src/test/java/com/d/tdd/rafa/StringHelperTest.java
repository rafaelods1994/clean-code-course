package com.d.tdd.rafa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringHelperTest {

	// "", "A", "AA", "B", "BC"

	private StringHelper stringHelper = new StringHelper();

	@Test
	void shouldTruncateAinFirst2Position() {
		assertEquals("BCD", stringHelper.truncateAatFirstTwoPositions("ABCD"));
		assertEquals("CD", stringHelper.truncateAatFirstTwoPositions("AACD"));
		assertEquals("BCD", stringHelper.truncateAatFirstTwoPositions("BACD"));
		assertEquals("AA", stringHelper.truncateAatFirstTwoPositions("AAAA"));
		assertEquals("MNAA", stringHelper.truncateAatFirstTwoPositions("MNAA"));
		assertEquals("", stringHelper.truncateAatFirstTwoPositions(""));
		assertEquals("", stringHelper.truncateAatFirstTwoPositions("A"));
		assertEquals("", stringHelper.truncateAatFirstTwoPositions("AA"));
		assertEquals("B", stringHelper.truncateAatFirstTwoPositions("B"));
		assertEquals("BC", stringHelper.truncateAatFirstTwoPositions("BC"));

	}

	// - ""=>false, "A"=>false, "AB"=>true, "ABC"=>false, "AAA"=>true,
	// "ABCAB"=>true, "ABCDEBA"=>false
	@Test
	void shouldCheckIfFirstTwoCharactersAndLastTwoCharacteresAreTheSame() {
		assertFalse(stringHelper.areFirstTwoCharactersSameAsLastTwo(""));
		assertFalse(stringHelper.areFirstTwoCharactersSameAsLastTwo("A"));
		assertTrue(stringHelper.areFirstTwoCharactersSameAsLastTwo("AB"));
		assertFalse(stringHelper.areFirstTwoCharactersSameAsLastTwo("ABC"));
		assertTrue(stringHelper.areFirstTwoCharactersSameAsLastTwo("AAA"));
		assertTrue(stringHelper.areFirstTwoCharactersSameAsLastTwo("ABCAB"));
		assertFalse(stringHelper.areFirstTwoCharactersSameAsLastTwo("ABCDEBA"));
	}
}