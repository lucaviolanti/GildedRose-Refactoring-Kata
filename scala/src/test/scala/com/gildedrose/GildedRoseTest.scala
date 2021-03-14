package com.gildedrose

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class GildedRoseTest extends AnyFreeSpec with Matchers {
  "GildedRose" - {
    "updateQuality should update" - {
      "all items so that it" - {
        "leaves the name unchanged" in {
          val items = Array[Item](new Item("foo", 0, 0))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).name should equal("foo")
        }
        "does not make an item's Quality negative" in {
          val items = Array[Item](new Item("item", 0, 0))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should be >= 0
        }
      }

      "regular items so that it" - {
        "lowers their Quality by 1" in {
          val items = Array[Item](new Item("regularItem", 1, 1))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should equal(0)
        }
        "lowers their SellIn by 1" in {
          val items = Array[Item](new Item("regularItem", 1, 1))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should equal(0)
        }
        "lowers their Quality twice as fast once the sell by date has passed" in {
          val items = Array[Item](new Item("regularItem", 0, 2))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should equal(0)
        }
      }

      "\"Aged Brie\" so that it" - {
        "increases its Quality the older it gets" in {
          val items = Array[Item](new Item("Aged Brie", 1, 0))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should equal(1)
        }

        "increases its Quality by two once expired" in {
          val items = Array[Item](new Item("Aged Brie", 0, 0))
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).quality should equal(2)
        }
      }
    }
  }
}