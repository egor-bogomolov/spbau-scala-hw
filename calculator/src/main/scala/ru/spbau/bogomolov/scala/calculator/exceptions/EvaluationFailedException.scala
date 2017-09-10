package ru.spbau.bogomolov.scala.calculator.exceptions

class EvaluationFailedException(private val message: String = "",
                                private val cause: Throwable = None.orNull) extends Exception(message, cause) {}
