package ru.spbau.bogomolov.scala.collection.mutable

import scala.collection.mutable

/**
  * Mutable multiset.
  * @tparam A type of elements in the set.
  */
class Multiset[A] {
  private val elementCount = mutable.HashMap.empty[A, Int]

  /**
    * Add "element" to the set "count" times. By default add 1 time.
    */
  def add(element: A, count: Int = 1): Unit = {
    if (count <= 0) throw new IllegalArgumentException("add should be called with positive count.")
    val currentCount = elementCount.getOrElse(element, 0)
    elementCount.update(element, currentCount + count)
  }

  /**
    * If element is presented in the multiset then returns Option with it else an empty Option.
    */
  def find(element: A): Option[A] = elementCount.get(element).flatMap(_ => Some(element))

  /**
    * Returns count of element in the multiset.
    */
  def count(element: A): Int = elementCount.getOrElse(element, 0)

  /**
    * Calls function on each element of the set. Doesn't take into account count of element.
    */
  def foreach(func: A => Unit): Unit = {
    for ((element, _) <- elementCount) func(element)
  }

  /**
    * Calls function on each element of the set. Takes into account count of element.
   */
  def foreach(func: (A, Int) => Unit): Unit = {
    for ((element, count) <- elementCount) func(element, count)
  }

  /**
    * Returns another multiset that consists of elements that predicate accepts.
    */
  def filter(predicate: A => Boolean): Multiset[A] = {
    val filtered = new Multiset[A]
    foreach((element, count) => if (predicate(element)) filtered.add(element, count))
    filtered
  }
}
