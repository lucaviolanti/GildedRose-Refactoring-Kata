package com.gildedrose

import org.scalacheck.Prop.{forAll, propBoolean}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.Checkers

class GildedRoseTest extends AnyFreeSpec with Matchers with Checkers {
  "GildedRose" - {
    "updateQuality should update" - {

      "all items so that it" - {
        "leaves the name unchanged" in {
          updateSingleItemOnce("foo", 0, 0).name should equal("foo")
        }
        "does not make an item's Quality negative" in {
          forAll { q: Int =>
            (q >= 0 && q < 100) ==> {
              updateSingleItemOnce("item", 0, q).quality >= 0
            }
          }.check()
        }
      }

      "regular items so that it" - {
        "lowers their Quality by 1 before sell by date" in {
          forAll { q: Int =>
            (q > 0 && q < 10) ==> {
              updateSingleItemOnce("regularItem", 20, q).quality == q - 1
            }
          }.check()
        }
        "lowers their SellIn by 1" in {
          forAll { s: Int =>
            (s > -100 && s < 100) ==> {
              updateSingleItemOnce("regularItem", s, 99).sellIn == s - 1
            }
          }.check()
        }
        "lowers their Quality twice as fast once the sell by date has passed" in {
          forAll { s: Int =>
            (s > -100 && s <= 0) ==> {
              updateSingleItemOnce("regularItem", s, 2).quality == 0
            }
          }.check()
        }
      }

      "\"Aged Brie\" so that it" - {
        "increases its Quality the older it gets, before its sell by date" in {
          forAll { (s: Int, q: Int) =>
            (s > 0 && s < 10 &&
              q >= 0 && q <= 10) ==> {
              updateSingleItemOnce("Aged Brie", s, q).quality == q + 1
            }
          }.check()
        }
        "increases its Quality by two once expired" in {
          forAll { (s: Int, q: Int) =>
            (s > -10 && s <= 0 &&
              q >= 0 && q <= 10) ==> {
              updateSingleItemOnce("Aged Brie", s, q).quality == q + 2
            }
          }.check()
        }
        "does not increase its Quality over 50" in {
          forAll { (s: Int) =>
            (s > -10 && s < 10) ==> {
              updateSingleItemOnce("Aged Brie", s, 50).quality == 50
            }
          }.check()
        }
      }

      "\"Sulfuras, Hand of Ragnaros\" so that it" - {
        "does not lower its Quality" in {
          forAll { s: Int =>
            (s > -100 && s < 100) ==> {
              updateSingleItemOnce("Sulfuras, Hand of Ragnaros", s, 80).quality == 80
            }
          }.check()
        }
      }

      "Backstage passes so that it" - {
        "increases their Quality as its SellIn value approaches" in {
          updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", 12, 1).quality should equal(2)
        }
        "increases their Quality by 2 when there are 10 days or less" in {
          updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", 6, 1).quality should equal(3)
        }
        "increases their Quality by 3 when there are 5 days or less" in {
          updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", 4, 1).quality should equal(4)
        }
        "drops their Quality to 0 after the concert" in {
          forAll { (s: Int) =>
            (s > -100 && s <= 0) ==> {
              updateSingleItemOnce("Backstage passes to a TAFKAL80ETC concert", s, 99).quality == 0
            }
          }.check()
        }
      }
    }
  }

  def updateSingleItemOnce(name: String, sellIn: Int, quality: Int): Item = {
    val items = Array[Item](Item(name, sellIn, quality))
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0)
  }
}