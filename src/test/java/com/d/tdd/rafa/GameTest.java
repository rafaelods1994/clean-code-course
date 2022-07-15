package com.d.tdd.rafa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameTest {

	// 0 20
	// 1 20
	// 5 20
	// 1 10 2 10
	// spare 5,5 1 18
	Game game = new Game();

	@Test
	void testAll0s() {

		rollMultipleTimes(0, 20);

		assertEquals(0, game.score());

	}

	@Test
	void testAll1s() {

		rollMultipleTimes(1, 20);

		assertEquals(20, game.score());

	}

	@Test
	void testHalf1sAndHalf2s() {

		rollMultipleTimes(1, 10);
		rollMultipleTimes(2, 10);

		assertEquals(30, game.score());

	}

	@Test
	void testSpare() {

		rollASpare();
		rollMultipleTimes(1, 18);
		// 5,5|1,1|1,1

		assertEquals(29, game.score());

	}

	@Test
	void testTwoSpare() {

		rollASpare();
		rollASpare();
		rollMultipleTimes(1, 16);
		// 5,5|5,5|1,1
		// 15 +11 +2...
		assertEquals(42, game.score());

	}

	@Test
	void testStrike() {

		game.roll(10);
		rollMultipleTimes(1, 18);
		// 10|1,1|..
		// 12 +2...
		assertEquals(30, game.score());

	}

	private void rollASpare() {
		rollMultipleTimes(5, 2);

	}

	private void rollMultipleTimes(int pinsKnockedDown, int noOfTimes) {
		for (int x = 1; x <= noOfTimes; x++) {
			game.roll(pinsKnockedDown);
		}
	}

	// RED
	// GREEN
	// REFACTOR

}
