package ru.spbau.bogomolov.scala.calculator.tokens.operators

object Multiply extends Operator {
  override def tokenString = "*"

  override def compute(x: Double, y: Double): Double = x * y

  override def compute(x: Double) = throw new UnsupportedOperationException

  override def priority = 1
}
