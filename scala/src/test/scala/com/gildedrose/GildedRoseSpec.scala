package com.gildedrose

import munit.ScalaCheckSuite
import org.scalacheck.Gen
import org.scalacheck.Prop._

class GildedRoseSpec extends ScalaCheckSuite {
  test("the name remains unchanged") {
    assertEquals(updateSingleItemOnce("foo", 0, 0).name, ("foo"))
  }

  property("Quality is never negative") {
    val qualityVals = Gen.choose(1, 100)
    forAll(qualityVals) { q =>
      updateSingleItemOnce("item", 0, q).quality >= 0
    }
  }

  property("regular items get their SellIn lowered by 1") {
    val sellInVals = Gen.choose(-100, 100)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("regularItem", s, 99).sellIn == s - 1
    }
  }

  property("regular items get their Quality lowered by 1 before sell by date") {
    val qualityVals = Gen.choose(1, 100)
    forAll(qualityVals) { q =>
      updateSingleItemOnce("regularItem", 20, q).quality == q - 1
    }
  }

  property("regular items get their Quality lowered twice as fast after their sell by date") {
    val sellInVals = Gen.choose(-100, 0)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("regularItem", s, 2).quality == 0
    }
  }

  property("\"Aged Brie\" increases its Quality the older it gets, before its sell by date") {
    val sellInVals = Gen.choose(1, 10)
    val qualityVals = Gen.choose(0, 10)
    forAll(sellInVals, qualityVals) { (s, q) =>
      updateSingleItemOnce("Aged Brie", s, q).quality == q + 1
    }
  }

  property("\"Aged Brie\" increases its Quality by two once expired") {
    val sellInVals = Gen.choose(-10, 0)
    val qualityVals = Gen.choose(0, 10)
    forAll(sellInVals, qualityVals) { (s, q) =>
      updateSingleItemOnce("Aged Brie", s, q).quality == q + 2
    }
  }

  property("\"Aged Brie\" does not increase its Quality over 50") {
    val sellInVals = Gen.choose(-10, 10)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Aged Brie", s, 50).quality == 50
    }
  }

  property("\"Sulfuras, Hand of Ragnaros\" does not lower its Quality") {
    val sellInVals = Gen.choose(-100, 100)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Sulfuras, Hand of Ragnaros", s, 80).quality == 80
    }
  }

  property("Backstage passes increase their Quality as its SellIn value approaches") {
    val sellInVals = Gen.choose(11, 100)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", s, 1).quality == 2
    }
  }

  property("Backstage passes increase their Quality by 2 when there are 10 days or less") {
    val sellInVals = Gen.choose(6, 10)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", s, 1).quality == 3
    }
  }

  property("Backstage passes increase their Quality by 3 when there are 5 days or less") {
    val sellInVals = Gen.choose(1, 5)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", s, 1).quality == 4
    }
  }

  property("Backstage passes drop their Quality to 0 after the concert") {
    val sellInVals = Gen.choose(-10, 0)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", s, 50).quality == 0
    }
  }

  property("\"Conjured\" items degrade in Quality twice as fast as normal items before their SellIn date") {
    val sellInVals = Gen.choose(1, 10)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Conjured item", s, 2).quality == 0
    }
  }

  property("\"Conjured\" items degrade in Quality twice as fast as normal items after their SellIn date") {
    val sellInVals = Gen.choose(-10, 0)
    forAll(sellInVals) { s =>
      updateSingleItemOnce("Conjured item", s, 4).quality == 0
    }
  }

  def updateSingleItemOnce(name: String, sellIn: Int, quality: Int): Item = {
    val items = Array[Item](Item(name, sellIn, quality))
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0)
  }
}