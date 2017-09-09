package ru.spbau.bogomolov.scala.calculator.tokens

class Number extends Token {
  private[this] var value$ = 0

  def value: Int = value$

  override def parseFrom(string: String) : ParseResult = {
    if (string.charAt(0).isDigit) {
      var position = 0
      while(position < string.length && string.charAt(position).isDigit) {
        position += 1
      }
      value$ = Integer.parseInt(string.substring(0, position))
      return ParseResult(this, position)
    }
    return ParseResult(null, 0)
  }
}

object Number extends Token {
  override def parseFrom(string: String) : ParseResult = new Number().parseFrom(string)
}
