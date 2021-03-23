package com.gildedrose

class Item(val name: String, var sellIn: Int, var quality: Int) {

}

object Item {
  def apply(name: String, sellIn: Int, quality: Int): Item =
    name match {
      case "Aged Brie" => AgedBrie(sellIn, quality)
      case "Backstage passes to a TAFKAL80ETC concert" => BackstagePass(sellIn, quality)
      case "Sulfuras, Hand of Ragnaros" => Sulfuras(sellIn, quality)
      case conjured if conjured.startsWith("Conjured") => ConjuredItem(conjured, sellIn, quality)
      case other => new Item(other, sellIn, quality)
    }
}
