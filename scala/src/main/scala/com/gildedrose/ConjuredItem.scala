package com.gildedrose

case class ConjuredItem(conjuredName: String, sellBy: Int, value: Int)
  extends Item(conjuredName, sellBy, value)
