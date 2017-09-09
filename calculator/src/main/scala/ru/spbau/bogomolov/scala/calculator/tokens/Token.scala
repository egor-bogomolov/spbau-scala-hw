package ru.spbau.bogomolov.scala.calculator.tokens

trait Token {
  def parseFrom(string: String) : ParseResult
}
