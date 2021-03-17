package com.gildedrose

class GildedRose(val items: Array[Item]) {
  def updateQuality() {
    for (item <- items) {
      item.name match {
        case "Aged Brie" => {
          if (item.quality < 50) {
            item.quality = item.quality + 1
          }
          item.sellIn = item.sellIn - 1
          if (item.sellIn < 0 && item.quality < 50) {
            item.quality = item.quality + 1
          }
        }

        case "Backstage passes to a TAFKAL80ETC concert" => {
          if (item.quality < 50) {
            item.quality = item.quality + 1
            if (item.sellIn < 11 && item.quality < 50) {
              item.quality = item.quality + 1
            }
            if (item.sellIn < 6 && item.quality < 50) {
              item.quality = item.quality + 1
            }
          }
          item.sellIn = item.sellIn - 1
          if (item.sellIn < 0) {
            item.quality = 0
          }
        }

        case "Sulfuras, Hand of Ragnaros" => {
        }

        case _ => {
          if (item.quality > 0) {
            item.quality = item.quality - 1
          }
          item.sellIn = item.sellIn - 1
          if (item.sellIn < 0 && item.quality > 0) {
            item.quality = item.quality - 1
          }
        }
      }
    }
  }
}
