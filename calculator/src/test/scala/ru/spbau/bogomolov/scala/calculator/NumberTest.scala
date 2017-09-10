package ru.spbau.bogomolov.scala.calculator

import org.scalatest.FunSuite
import ru.spbau.bogomolov.scala.calculator.tokens.Number

class NumberTest extends FunSuite {

  private[this] val value0 = 0
  private[this] val valueInt = 123
  private[this] val valueDouble = 123.123
  private[this] val string = "random"

  test("testNotParsing") {
    assert(null ==  Number.parseFrom(string + value0.toString).token)
  }

  test("testParseFrom0") {
    testFromNumber(value0)
  }

  test("testParseFromInt") {
    testFromNumber(valueInt)
  }

  test("testParseFromDouble") {
    testFromNumber(valueDouble)
  }

  private def testFromNumber(number: Double) = {
    val parseResult = Number.parseFrom(number.toString + string)
    assert(parseResult.token != null)
    assert(parseResult.token.asInstanceOf[Number].value == number)
    assert(parseResult.length == number.toString.length)
  }
}
