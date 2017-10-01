package ru.spbau.bogomolov.scala.collection.mutable

import org.scalatest.FunSuite

class MultisetTest extends FunSuite {

  val value = 3
  val count = 7

  test("testEmptyGet") {
    val multiset = new Multiset[Int]()
    assert(multiset.find(value).isEmpty)
  }

  test("testEmptyCount") {
    val multiset = new Multiset[Int]()
    assert(multiset.count(value) == 0)
  }

  test("testAddGet") {
    val multiset = new Multiset[Int]()
    multiset.add(value)
    val findResult = multiset.find(value)
    assert(findResult.isDefined && findResult.get == value)
  }

  test("testAddCount") {
    val multiset = new Multiset[Int]()
    multiset.add(value, count)
    assert(multiset.count(value) == count)
  }
}
