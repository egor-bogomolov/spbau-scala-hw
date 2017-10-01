package ru.spbau.bogomolov.scala.collection.mutable

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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
    * Applies predicate to all elements of the [[Multiset]] and returns the multiset with elements that returned true.
    */
  def filter(predicate: A => Boolean): Multiset[A] = {
    val filtered = new Multiset[A]
    foreach((element, count) => if (predicate(element)) filtered.add(element, count))
    filtered
  }

  /**
    * Maps function to all elements of the [[Multiset]] and returns the resulting multiset.
    */
  def map[B](func: A => B): Multiset[B] = {
    val mapped = new Multiset[B]
    foreach((element, count) => mapped.add(func(element), count))
    mapped
  }

  /**
    * Maps function to all elements of the [[Multiset]] and returns the resulting multiset.
    */
  def flatMap[B](func: A => mutable.Iterable[B]): Multiset[B] = {
    val mapped = new Multiset[B]
    foreach((element, count) =>
      func(element).foreach(newElement => mapped.add(newElement, count)))
    mapped
  }

  /**
    * Returns [[Multiset]] that contains each element minimum of sets' counts times.
    */
  def &(that: Multiset[A]): Multiset[A] = {
    val intersection = new Multiset[A]
    foreach((element, count) => {
      val result = Math.min(count, that.count(element))
      if (result != 0) intersection.add(element, result)
    })
    intersection
  }

  /**
    * Returns [[Multiset]] that contains each element sum of sets' counts times.
    */
  def |(that: Multiset[A]): Multiset[A] = {
    val union = new Multiset[A]
    this.foreach((element, count) => union.add(element, count))
    that.foreach((element, count) => union.add(element, count))
    union
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case m: Multiset[A] => m.elementCount == elementCount
    case _ => false
  }
}

object Multiset {

  def apply[A](elements: A*): Multiset[A] = {
    val multiset = new Multiset[A]
    for (element <- elements) multiset.add(element)
    multiset
  }

  def unapplySeq[A](multiset: Multiset[A]): Option[mutable.Seq[A]] = {
    val elements = ListBuffer.empty[A]
    multiset.foreach((element, count) => elements ++= List.fill(count)(element))
    Some(elements)
  }
}