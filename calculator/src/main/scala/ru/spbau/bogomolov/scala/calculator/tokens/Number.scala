package ru.spbau.bogomolov.scala.calculator.tokens

class Number extends Token {
  private[this] var value$: Double = 0

  def value: Double = value$

  override def parseFrom(string: String) : ParseResult = {
    if (string.charAt(0).isDigit) {
      var position = getEndPositionOfNumber(string)
      if (position < string.length && string.charAt(position) == '.') {
        position += 1
        position += getEndPositionOfNumber(string.substring(position))
      }
      value$ = string.substring(0, position).toDouble
      return ParseResult(this, position)
    }
    return ParseResult(null, 0)
  }

  private def getEndPositionOfNumber(string: String) : Int = {
    var position = 0
    while(position < string.length && string.charAt(position).isDigit) {
      position += 1
    }
    return position
  }
}

object Number extends Token {
  override def parseFrom(string: String) : ParseResult = new Number().parseFrom(string)
}
