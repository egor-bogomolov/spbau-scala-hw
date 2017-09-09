package ru.spbau.bogomolov.scala.calculator.exceptions

class ParsingFailedException(private val message: String = "",
                             private val cause: Throwable = None.orNull) extends Exception(message, cause) {}
