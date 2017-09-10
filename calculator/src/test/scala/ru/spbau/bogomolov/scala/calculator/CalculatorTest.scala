package ru.spbau.bogomolov.scala.calculator

import org.scalatest.FunSuite
import ru.spbau.bogomolov.scala.calculator.tokens.{Number, Token}
import ru.spbau.bogomolov.scala.calculator.tokens.brackets.{CloseBracket, OpenBracket}
import ru.spbau.bogomolov.scala.calculator.tokens.operators.{Divide, Multiply, Plus, Sinus}

class CalculatorTest extends FunSuite {

  test("testTokenize") {
    val list = Calculator.tokenize("123.123+15.3*24/sin(3.14)")
    val expected = List[Token](Number.withValue(123.123), Plus, Number.withValue(15.3), Multiply, Number.withValue(24),
      Divide, Sinus, OpenBracket, Number.withValue(3.14), CloseBracket)
    assert(list.equals(expected))
  }

  test("testBrackets") {
    assert(Calculator.compute("3*(1+1)") == 6)
  }

  test("testOrder") {
    assert(Calculator.compute("3+6*6") == 39)
  }

  test("testOrderInOnePriority") {
    assert(Calculator.compute("3/3*2") == 2)
  }

  test("testComputeLong") {
    assert(almostEqual(Calculator.compute("-(17*23-168)*7-(15+13)/48+14+((3+2)/7+6)-12"), -1552.86904762))
  }

  def almostEqual(x: Double, y: Double, precision: Double = 0.0000001): Boolean = (x - y).abs < precision
}
