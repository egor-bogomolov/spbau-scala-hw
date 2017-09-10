package ru.spbau.bogomolov.scala.calculator.tokens

/**
  * A token that represents Double-number.
  */
class Number extends Token {
  private[this] var value$: Double = 0

  def value: Double = value$

  private def value_=(value: Double): Unit = {
    value$ = value
  }

  /**
    * If strings starts with number - reads it from the string and returns this instance of Number, otherwise (null, 0)
    */
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

  def canEqual(other: Any): Boolean = other.isInstanceOf[Number]

  override def equals(other: Any): Boolean = other match {
    case that: Number =>
      (that canEqual this) &&
        value$ == that.value
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(value$)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Number extends Token {
  override def parseFrom(string: String) : ParseResult = new Number().parseFrom(string)
  def withValue(v: Double): Number = {
    val num = new Number()
    num.value = v
    return num
  }
}
