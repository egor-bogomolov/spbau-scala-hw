package ru.spbau.bogomolov.scala.calculator.tokens.brackets

import ru.spbau.bogomolov.scala.calculator.tokens.Token
import ru.spbau.bogomolov.scala.calculator.tokens.{ParseResult, Token}

trait Bracket extends Token {
  def isOpening: Boolean

  def tokenString: String

  override def parseFrom(string: String): ParseResult = {
    if (string.startsWith(tokenString)) ParseResult(this, tokenString.length)
    else ParseResult(null, 0)
  }
}
