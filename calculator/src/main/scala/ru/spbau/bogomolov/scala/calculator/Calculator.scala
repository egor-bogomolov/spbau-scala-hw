package ru.spbau.bogomolov.scala.calculator

import ru.spbau.bogomolov.scala.calculator.tokens.brackets.{Bracket, CloseBracket, OpenBracket}
import ru.spbau.bogomolov.scala.calculator.tokens.operators._
import ru.spbau.bogomolov.scala.calculator.tokens.Token
import ru.spbau.bogomolov.scala.calculator.tokens.Number

import scala.collection.mutable.ListBuffer

object Calculator {
  private val supportedTokens = Array(Number, Plus, Minus, Divide, Multiply, Sinus, OpenBracket, CloseBracket)

  def compute(expression: String): Double = {
    val resultingTree = buildTree(tokenize(expression))
    if (resultingTree.tokens.isEmpty && (resultingTree.node != null)) {
      return resultingTree.node.evaluate
    }
    throw new UnsupportedOperationException
  }

  private def tokenize(expression: String): List[Token] = {
    var list = new ListBuffer[Token]()
    var position = 0
    while(position < expression.length) {
      var found = false
      for (token <- supportedTokens) {
        val result = token.parseFrom(expression.substring(position))
        if (result.token != null) {
          position += result.length
          list += result.token
          found = true
        }
      }
      if (!found) throw new UnsupportedOperationException
    }
    return list.toList
  }
  private def buildTree(tokens: List[Token], node : Node = null, hadOpening : Boolean = false): BuildResult = {
    var currentNode = node
    if (tokens.isEmpty) {
      if (hadOpening) throw new UnsupportedOperationException
      else return BuildResult(node.getRoot, tokens)
    }
    tokens.head match {
      case bracket : Bracket =>
        if (bracket.isOpening) {
          val resultInBrackets = buildTree(tokens.tail, node = null, hadOpening = true)
          if (currentNode == null) currentNode = resultInBrackets.node.getRoot
          else currentNode = currentNode.insertNode(resultInBrackets.node.getRoot)
          return buildTree(resultInBrackets.tokens, currentNode, hadOpening)
        } else if (hadOpening) {
          return BuildResult(node, tokens.tail)
        } else {
          throw new UnsupportedOperationException
        }
      case token => if (currentNode != null) {
        return buildTree(tokens.tail, currentNode.insertNode(new Node(token)), hadOpening)
      } else {
        return buildTree(tokens.tail, new Node(token), hadOpening)
      }
    }
  }

  case class BuildResult(node : Node, tokens : List[Token])
}
