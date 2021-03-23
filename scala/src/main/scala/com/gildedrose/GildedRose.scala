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

  private def updateBrie(ab: AgedBrie): Unit = {
    ab.sellIn = ab.sellIn - 1
    if (ab.quality < 50) {
      ab.quality += (if (ab.sellIn < 0) 2 else 1)
    }
  }

  private def updatePass(bp: BackstagePass): Unit = {
    bp.sellIn = bp.sellIn - 1
    if (bp.sellIn >= 10) bp.quality += 1
    else if (bp.sellIn >= 5) bp.quality += 2
    else if (bp.sellIn >= 0) bp.quality += 3
    else bp.quality = 0
    if (bp.quality > 50) bp.quality = 50
  }

  def updateConjuredItem(ci: ConjuredItem): Unit = {
    ci.sellIn -= 1
    ci.quality -= (if (ci.sellIn <= 0) 4 else 2)
    if (ci.quality < 0) ci.quality = 0
  }

  private def updateRegularItem(ri: Item): Unit = {
    ri.sellIn -= 1
    ri.quality -= (if (ri.sellIn <= 0) 2 else 1)
    if (ri.quality < 0) ri.quality = 0
  }
}
