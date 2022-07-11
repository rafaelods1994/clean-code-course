package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GildedRoseCBackstagePassesRefactoredByRafaTest {

	private static final int SELLIN_VALUE_LESS_THAN_6_DAYS = 4;
	private static final int SELLIN_VALUE_MORE_THAN_6_DAYS = 7;
	private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
	private int SELLIN_VALUE_MORE_THAN_11_DAYS = 15;
	private int DEFAULT_QUALITY = 3;

	@Test
	public void shouldIncreaseQualityBy1backStageMoreThan11Days() {
		GildedRose app = createGildedRoseWithOneItem(BACKSTAGE, SELLIN_VALUE_MORE_THAN_11_DAYS, DEFAULT_QUALITY);

		app.updateQuality();

		Item expectedItem = new Item(BACKSTAGE, SELLIN_VALUE_MORE_THAN_11_DAYS - 1, DEFAULT_QUALITY + 1);

		assertItem(expectedItem, app.items[0]);
	}

	@Test
	public void shouldIncreaseQualityBy2backStageBetween5And10Days() {

		GildedRose app = createGildedRoseWithOneItem(BACKSTAGE, SELLIN_VALUE_MORE_THAN_6_DAYS, DEFAULT_QUALITY);

		app.updateQuality();

		Item expectedItem = new Item(BACKSTAGE, SELLIN_VALUE_MORE_THAN_6_DAYS - 1, DEFAULT_QUALITY + 2);
		assertItem(expectedItem, app.items[0]);

	}

	@Test
	public void shouldIncreaseQualityBy3backStageLessThan5Days() {

		GildedRose app = createGildedRoseWithOneItem(BACKSTAGE, SELLIN_VALUE_LESS_THAN_6_DAYS, DEFAULT_QUALITY);

		app.updateQuality();

		Item expectedItem = new Item(BACKSTAGE, SELLIN_VALUE_LESS_THAN_6_DAYS - 1, DEFAULT_QUALITY + 3);
		assertItem(expectedItem, app.items[0]);
	}

	private void assertItem(Item expectedItem, Item actualItem) {
		assertEquals(expectedItem.name, actualItem.name);
		assertEquals(expectedItem.sellIn, actualItem.sellIn);
		assertEquals(expectedItem.quality, actualItem.quality);
	}

	private GildedRose createGildedRoseWithOneItem(String backstagePassesToATafkal80etcConcert, int sellIn,
			int quality) {
		Item item = new Item(backstagePassesToATafkal80etcConcert, sellIn, quality);
		Item[] items = new Item[] { item };
		GildedRose app = new GildedRose(items);
		return app;
	}

}