package ru.spbau.bogomolov.scala.calculator.tokens.operators

import ru.spbau.bogomolov.scala.calculator.tokens.{ParseResult, Token}

/**
  * A token that represents any operator in expression. Operators can be binary and unary.
  */
trait Operator extends Token {
  /**
    * String that represents operator in expression (i.e. "+" for plus)
    */
  def tokenString : String

  /**
    * Result of applying the operator if it's treated as binary operator.
    * @throws UnsupportedOperationException if the operator can't be used as binary one.
    */
  @throws[UnsupportedOperationException]
  def compute(x : Double, y : Double): Double =
    throw new UnsupportedOperationException(tokenString + " can't be used as binary operator")

  /**
    * Result of applying the operator if it's treated as unary operator.
    * @throws UnsupportedOperationException if the operator can't be used as unary one.
    */
  @throws[UnsupportedOperationException]
  def compute(x : Double): Double =
    throw new UnsupportedOperationException(tokenString + " can't be used as unary operator")

  /**
    * Used to set order of operations in the expression.
    */
  def priority: Int

  override def parseFrom(string: String): ParseResult = {
    if (string.startsWith(tokenString)) ParseResult(this, tokenString.length)
    else ParseResult(null, 0)
  }
}
