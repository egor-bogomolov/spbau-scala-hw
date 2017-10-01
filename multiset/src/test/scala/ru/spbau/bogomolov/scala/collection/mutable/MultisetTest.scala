package ru.spbau.bogomolov.scala.collection.mutable

import org.scalatest.FunSuite

import scala.collection.mutable

class MultisetTest extends FunSuite {

  val value = 3
  val count = 7
  val limit = 9

  test("testEmptyGet") {
    val multiset = new Multiset[Int]
    assert(multiset.find(value).isEmpty)
  }

  test("testEmptyCount") {
    val multiset = new Multiset[Int]
    assert(multiset.count(value) == 0)
  }

  test("testAddGet") {
    val multiset = new Multiset[Int]
    multiset.add(value)
    val findResult = multiset.find(value)
    assert(findResult.isDefined && findResult.get == value)
  }

  test("testAddCount") {
    val multiset = new Multiset[Int]
    multiset.add(value, count)
    assert(multiset.count(value) == count)
  }

  test("testForEach") {
    val multiset = new Multiset[Int]
    var counter = 0
    for (i <- 1 to value) {
      multiset.add(i, count)
    }
    multiset.foreach((value, count) => counter += value * count)
    assert(counter == count * value * (value + 1) / 2)
  }

  test("testFilter") {
    val multiset = new Multiset[Int]
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
    val multiset = new Multiset[Int]
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
    val multiset1 = new Multiset[Int]
    val multiset2 = new Multiset[Int]
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
    val multiset1 = new Multiset[Int]
    val multiset2 = new Multiset[Int]
    for (i <- 1 to limit) {
      multiset1.add(i, i)
      multiset2.add(limit - i + 1, i)
    }
    val intersection = multiset1 | multiset2
    for (i <- 1 to limit) {
      assert(intersection.count(i) == limit + 1)
    }
  }

}
