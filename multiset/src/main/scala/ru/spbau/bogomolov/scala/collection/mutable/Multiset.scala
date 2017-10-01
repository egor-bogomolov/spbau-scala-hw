package ru.spbau.bogomolov.scala.collection.mutable

import scala.collection.mutable

/**
  * Mutable multiset.
  * @tparam A type of elements in the set.
  */
class Multiset[A] {
  private val elementCount = mutable.HashMap.empty[A, Int]

  def add(element: A, count: Int = 1): Unit = {
    if (count <= 0) throw new IllegalArgumentException("add should be called with positive count.")
    val currentCount = elementCount.getOrElse(element, 0)
    elementCount.update(element, currentCount + count)
  }

  def find(element: A): Option[A] = elementCount.get(element).flatMap(_ => Some(element))

  def count(element: A): Int = elementCount.getOrElse(element, 0)
}
