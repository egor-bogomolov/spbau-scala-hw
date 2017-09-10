package ru.spbau.bogomolov.scala.calculator.tokens.brackets

object OpenBracket extends Bracket {
  override def isOpening = true

  override def tokenString = "("
}
