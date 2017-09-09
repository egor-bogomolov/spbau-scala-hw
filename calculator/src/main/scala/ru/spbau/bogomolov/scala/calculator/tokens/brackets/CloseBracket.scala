package ru.spbau.bogomolov.scala.calculator.tokens.brackets

object CloseBracket extends Bracket {
  override def isOpening = false

  override def tokenString = ")"
}
