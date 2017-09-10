package ru.spbau.bogomolov.scala.calculator.tokens

/**
  * Represents anything that can be found in arithmetic expression (operators, numbers, brackets etc.)
  */
trait Token {
  /**
    * If string starts with token returns an instance of token and length of prefix that was used to create it.
    * Otherwise should return (null, 0)
    */
  def parseFrom(string: String) : ParseResult
}
