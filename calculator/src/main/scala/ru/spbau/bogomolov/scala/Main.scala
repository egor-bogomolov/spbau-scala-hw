package ru.spbau.bogomolov.scala

import ru.spbau.bogomolov.scala.calculator.Calculator

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    val expression = StdIn.readLine()
    println(Calculator.compute(expression.replaceAll("\\s", "")))
  }
}
