package ru.spbau.bogomolov.scala.calculator.exceptions

class UnmatchedBracketsException(private val message: String = "",
                                 private val cause: Throwable = None.orNull) extends Exception(message, cause) {}
