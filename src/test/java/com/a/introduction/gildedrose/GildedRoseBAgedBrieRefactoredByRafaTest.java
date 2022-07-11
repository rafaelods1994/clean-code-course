package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GildedRoseBAgedBrieRefactoredByRafaTest {

	private int UNEXPIRED_AGEDBRIE = 4;
	private String AGED_BRIE = "Aged Brie";
	private int EXPIRED_AGEDBRIE = -1;
	private int DEFAULT_QUALITY = 3;
	private int MAX_QUALITY = 50;

	@Test
	public void shouldUpdateQualityPlusOneUnexpiredAgedBrie() {
		GildedRose app = createGildedRoseWithOneItem(AGED_BRIE, UNEXPIRED_AGEDBRIE, DEFAULT_QUALITY);

		app.updateQuality();

		Item itemExpected = new Item(AGED_BRIE, UNEXPIRED_AGEDBRIE - 1, DEFAULT_QUALITY + 1);
		assertItem(itemExpected, app.items[0]);
	}

	@Test
	public void shouldUpdateQualityPlus2ExpiredAgedBrie() {
		GildedRose app = createGildedRoseWithOneItem(AGED_BRIE, EXPIRED_AGEDBRIE, DEFAULT_QUALITY);

		app.updateQuality();

		Item itemExpected = new Item(AGED_BRIE, EXPIRED_AGEDBRIE - 1, DEFAULT_QUALITY + 2);
		assertItem(itemExpected, app.items[0]);
	}

	@Test
	public void shouldNotUpdateQualityBeyondMaximum() {
		GildedRose app = createGildedRoseWithOneItem(AGED_BRIE, UNEXPIRED_AGEDBRIE, MAX_QUALITY);

		app.updateQuality();

		Item itemExpected = new Item(AGED_BRIE, UNEXPIRED_AGEDBRIE - 1, MAX_QUALITY);
		assertItem(itemExpected, app.items[0]);
	}

	private GildedRose createGildedRoseWithOneItem(String agedBrie, int sellin2, int quality2) {
		Item item = new Item(agedBrie, sellin2, quality2);
		Item[] items = new Item[] { item };
		GildedRose app = new GildedRose(items);
		return app;
	}

	private void assertItem(Item itemExpected, Item itemActual) {
		assertEquals(itemExpected.name, itemActual.name);
		assertEquals(itemExpected.sellIn, itemActual.sellIn);
		assertEquals(itemExpected.quality, itemActual.quality);
	}
}
