package com.gildedrose

class GildedRose(val items: Array[Item]) {
  def updateQuality() {
    items.foreach {
      case brie@AgedBrie(_, _) => {
        brie.sellIn = brie.sellIn - 1
        if (brie.quality < 50) {
          brie.quality += (if (brie.sellIn < 0) 2 else 1)
        }
      }

      case pass@BackstagePass(_, _) => {
        if (pass.quality < 50) {
          pass.quality += 1
          if (pass.sellIn < 11 && pass.quality < 50) {
            pass.quality += 1
          }
          if (pass.sellIn < 6 && pass.quality < 50) {
            pass.quality += 1
          }
        }
        pass.sellIn = pass.sellIn - 1
        if (pass.sellIn < 0) {
          pass.quality = 0
        }
      }

      case Sulfuras(_, _) => {
      }

      case item@_ => {
        if (item.quality > 0) {
          item.quality -= 1
        }
        item.sellIn -= 1
        if (item.sellIn < 0 && item.quality > 0) {
          item.quality -= 1
        }
      }
    }
  }
}
