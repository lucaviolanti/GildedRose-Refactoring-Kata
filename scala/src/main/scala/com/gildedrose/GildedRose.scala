package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    items.foreach {
      case brie@AgedBrie(_, _) => updateBrie(brie)

      case pass@BackstagePass(_, _) => updatePass(pass)

      case Sulfuras(_, _) => {
      }

      case conjuredItem@ConjuredItem(_, _, _) => updateConjuredItem(conjuredItem)

      case item@_ => updateRegularItem(item)
    }
  }

  private def updateBrie(brie: AgedBrie): Unit = {
    brie.sellIn = brie.sellIn - 1
    if (brie.quality < 50) {
      brie.quality += (if (brie.sellIn < 0) 2 else 1)
    }
  }

  private def updatePass(pass: BackstagePass): Unit = {
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

  def updateConjuredItem(conjuredItem: ConjuredItem): Unit = {
    if (conjuredItem.quality > 1) {
      conjuredItem.quality -= 2
    }
    conjuredItem.sellIn -= 1
    if (conjuredItem.sellIn < 0 && conjuredItem.quality > 1) {
      conjuredItem.quality -= 2
    }
  }

  private def updateRegularItem(item: Item): Unit = {
    if (item.quality > 0) {
      item.quality -= 1
    }
    item.sellIn -= 1
    if (item.sellIn < 0 && item.quality > 0) {
      item.quality -= 1
    }
  }
}
