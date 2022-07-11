package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GildedRoseRefactoredByRafaDefaultItemTest {

	@Test
	public void shouldDecreaseBy1TheQualityAndSellInWhenTheItemIsNotExpired() {
		String itemName = "DEFAULT_ITEM";
		int sellIn = 15;
		int quality = 3;
		GildedRose app = createGildedRoseWithOneItem(itemName, sellIn, quality);

		app.updateQuality();

		Item expected = new Item(itemName, sellIn - 1, quality - 1);

		assertItem(expected, app.items[0]);
	}


	@Test
	public void shouldDecreaseBy2TheQualityAndSellInBy1WhenTheItemIsExpired() {
		String itemName = "DEFAULT_ITEM";
		int sellIn = -1;
		int quality = 3;

		GildedRose app = createGildedRoseWithOneItem(itemName, sellIn, quality);
		app.updateQuality();

		Item expected = new Item(itemName, sellIn - 1, quality - 2);

		assertItem(expected, app.items[0]);

	}

	private void assertItem(Item expected, Item actual) {
		assertEquals(expected.name, actual.name);
		assertEquals(expected.sellIn, actual.sellIn);
		assertEquals(expected.quality, actual.quality);
	}

	private GildedRose createGildedRoseWithOneItem(String itemName, int sellIn, int quality) {
		Item item = new Item(itemName, sellIn, quality);
		Item[] items = new Item[] { item };

		GildedRose app = new GildedRose(items);
		return app;
	}
}