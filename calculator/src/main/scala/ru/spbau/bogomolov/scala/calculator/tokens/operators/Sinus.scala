package ru.spbau.bogomolov.scala.calculator.tokens.operators

object Sinus extends Operator {
  override def tokenString = "sin"

  override def compute(x: Double): Double = Math.sin(x)

  override def priority = 0
}
