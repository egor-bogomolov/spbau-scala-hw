package ru.spbau.bogomolov.scala.calculator.tokens.operators

import ru.spbau.bogomolov.scala.calculator.tokens.Token
import ru.spbau.bogomolov.scala.calculator.tokens.{ParseResult, Token}

trait Operator extends Token {
  def tokenString : String

  def compute(x : Double, y : Double): Double

  def compute(x : Double): Double

  def priority: Int

  override def parseFrom(string: String): ParseResult = {
    if (string.startsWith(tokenString)) ParseResult(this, tokenString.length)
    else ParseResult(null, 0)
  }
}
