package ru.spbau.bogomolov.scala.collection.mutable

import org.scalatest.FunSuite

import scala.collection.mutable

class MultisetTest extends FunSuite {

  val value = 3
  val count = 7
  val limit = 9

  test("testEmptyGet") {
    val multiset = Multiset[Int]()
    assert(multiset.find(value).isEmpty)
  }

  test("testEmptyCount") {
    val multiset = Multiset[Int]()
    assert(multiset.count(value) == 0)
  }

  test("testAddGet") {
    val multiset = Multiset(value)
    val findResult = multiset.find(value)
    assert(findResult.isDefined && findResult.get == value)
  }

  test("testAddCount") {
    val multiset = Multiset[Int]()
    multiset.add(value, count)
    assert(multiset.count(value) == count)
  }

  test("testForEach") {
    val multiset = Multiset[Int]()
    var counter = 0
    for (i <- 1 to value) {
      multiset.add(i, count)
    }
    multiset.foreach((value, count) => counter += value * count)
    assert(counter == count * value * (value + 1) / 2)
  }

  test("testFilter") {
    val multiset = Multiset[Int]()
    for (i <- 1 to limit) {
      multiset.add(i)
    }
    val filtered = multiset.filter((value) => value % 2 == 0)
    for (i <- 1 to limit) {
      if (i % 2 == 0) assert(filtered.count(i) == 1)
      else assert(filtered.count(i) == 0)
    }
  }

  test("testMap") {
    val multiset = Multiset[Int]()
    for (i <- 1 to limit) {
      multiset.add(i, i * 2)
    }
    val mapped = multiset.map(value => Some(value))
    for (i <- 1 to limit) {
      assert(mapped.count(Some(i)) == i * 2)
    }
  }

  test("testFlatMap") {
    val multiset = new Multiset[Int]
    for (i <- 1 to limit) {
      multiset.add(i, i * 2)
    }
    val mapped = multiset.flatMap(value => mutable.Seq(Some(value), Some(value)))
    for (i <- 1 to limit) {
      assert(mapped.count(Some(i)) == i * 4)
    }
  }

  test("testIntersect") {
    val multiset1 = Multiset[Int]()
    val multiset2 = Multiset[Int]()
    for (i <- 1 to limit) {
      multiset1.add(i, i)
      multiset2.add(limit - i + 1, i)
    }
    val intersection = multiset1 & multiset2
    for (i <- 1 to limit) {
      assert(intersection.count(i) == Math.min(i, limit - i + 1))
    }
  }

  test("testUnion") {
    val multiset1 = Multiset[Int]()
    val multiset2 = Multiset[Int]()
    for (i <- 1 to limit) {
      multiset1.add(i, i)
      multiset2.add(limit - i + 1, i)
    }
    val intersection = multiset1 | multiset2
    for (i <- 1 to limit) {
      assert(intersection.count(i) == limit + 1)
    }
  }

  /** Uses variables in matching because order isn't specified. */
  test("testPatternMatching") {
    Multiset(1, 2, 3) match {
      case Multiset(a, b, c) => assert(Multiset(a, b, c) == Multiset(1, 2, 3))
      case _ => fail("Matching failed.")
    }
  }

  test("testForFilter") {
    val multiset = Multiset(1, 2, 3, 4)
    val filtered = for {
      element <- multiset
      if element % 2 == 0
    } yield element
    assert(filtered == Multiset(2, 4))
  }

  test("testForMap") {
    val multiset = Multiset(1, 2, 3, 4)
    val filtered = for {
      element <- multiset
    } yield element * 2
    assert(filtered == Multiset(2, 4, 6, 8))
  }

  test("testForFlatMap") {
    val multiset1 = Multiset(1, 2, 3)
    val multiset2 = mutable.Seq('a', 'b')
    val filtered = for {
      first <- multiset1
      second <- multiset2
    } yield (first, second)
    assert(filtered == Multiset((1, 'a'), (1, 'b'), (2, 'a'), (2, 'b'), (3, 'a'), (3, 'b')))
  }
}
