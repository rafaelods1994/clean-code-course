package com.rafa.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DemoUtilsTest {

	private DemoUtils demoUtils = new DemoUtils();

	@Test
	void testEqualsAndNotEquals() {

		assertEquals(0, demoUtils.add(0, 0));
		assertEquals(1, demoUtils.add(0, 1));
		assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
		assertEquals(-2, demoUtils.add(1, -3));

		assertNotEquals(-2, demoUtils.add(1, -2), "1-2 must not be -2");

	}

	@Test
	void testCheckNull() {
		assertNull(demoUtils.checkNull(null));
		assertNotNull(demoUtils.checkNull("test"));
	}
}
