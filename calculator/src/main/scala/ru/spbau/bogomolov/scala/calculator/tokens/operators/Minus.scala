package ru.spbau.bogomolov.scala.calculator.tokens.operators

object Minus extends Operator {
  override def tokenString = "-"

  override def compute(x: Double, y: Double): Double = x - y

  override def compute(x: Double): Double = -x

  override def priority = 0
}
