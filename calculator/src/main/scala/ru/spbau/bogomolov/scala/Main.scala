package ru.spbau.bogomolov.scala

import ru.spbau.bogomolov.scala.calculator.Calculator

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    val expression = StdIn.readLine()
    try {
      println(Calculator.compute(expression.replaceAll("\\s", "")))
    } catch {
      case e : Exception => println(e.getMessage)
    }
  }
}
