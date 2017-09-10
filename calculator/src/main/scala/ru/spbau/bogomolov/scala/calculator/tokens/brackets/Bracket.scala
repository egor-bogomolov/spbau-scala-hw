package ru.spbau.bogomolov.scala.calculator.tokens.brackets

import ru.spbau.bogomolov.scala.calculator.tokens.{ParseResult, Token}

/**
  * A token that represents a bracket.
  */
trait Bracket extends Token {
  /**
    * @return true if bracket is opening, otherwise (closing) false
    */
  def isOpening: Boolean

  /**
    * String that represents bracket in expression (i.e. "(" for opening bracket)
    */
  def tokenString: String

  override def parseFrom(string: String): ParseResult = {
    if (string.startsWith(tokenString)) ParseResult(this, tokenString.length)
    else ParseResult(null, 0)
  }
}
