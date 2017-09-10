package ru.spbau.bogomolov.scala.calculator

import ru.spbau.bogomolov.scala.calculator.exceptions.{EvaluationFailedException, ParsingFailedException, UnmatchedBracketsException}
import ru.spbau.bogomolov.scala.calculator.tokens.brackets.{Bracket, CloseBracket, OpenBracket}
import ru.spbau.bogomolov.scala.calculator.tokens.operators._
import ru.spbau.bogomolov.scala.calculator.tokens.Token
import ru.spbau.bogomolov.scala.calculator.tokens.Number

import scala.collection.mutable.ListBuffer

/**
  * Calculator for arithmetic expressions. They can contain numbers, +, -, /, *, sin(), (, ). Expression shouldn't
  * contain any other symbols.
  */
object Calculator {
  private val supportedTokens = Array(Number, Plus, Minus, Divide, Multiply, Sinus, OpenBracket, CloseBracket)

  /**
    * @param expression - expression which result should be computed.
    * @throws ParsingFailedException if expression was incorrect.
    * @throws EvaluationFailedException if expression can't be computed.
    * @throws UnmatchedBracketsException if expression has unmatched brackets.
    * @throws UnsupportedOperationException if binary operator is used as unary one or opposite.
    * @return result of computing.
    */
  @throws(classOf[ParsingFailedException])
  @throws(classOf[EvaluationFailedException])
  @throws(classOf[UnmatchedBracketsException])
  @throws(classOf[UnsupportedOperationException])
  def compute(expression: String): Double = {
    val resultingTree = buildTree(tokenize(expression))
    if (resultingTree.tokens.isEmpty && (resultingTree.node != null)) {
      return resultingTree.node.evaluate
    }
    throw new EvaluationFailedException("Expression wasn't parsed correctly")
  }

  /**
    * Divides the expression in a list of tokens.
    */
  @throws(classOf[ParsingFailedException])
  def tokenize(expression: String): List[Token] = {
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
      if (!found) throw new ParsingFailedException("Parsing failed at position " + position)
    }
    return list.toList
  }

  /**
    * Builds computation tree for an expression from list of tokens.
    * @param tokens - list of tokens.
    * @param node - node representing the most outer token in the string to the left (last token if there were no
    *             brackets or root of the tree if prefix was contained in brackets).
    * @param hadOpening - if there was an unmatched opening bracket to the left from current point.
    * @return root of the computation tree that was built.
    */
  @throws(classOf[UnmatchedBracketsException])
  @throws(classOf[ParsingFailedException])
  def buildTree(tokens: List[Token], node : Node = null, hadOpening : Boolean = false): BuildResult = {
    var currentNode = node
    if (tokens.isEmpty) {
      if (hadOpening) throw new UnmatchedBracketsException("Expression contains unmatched opening bracket")
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
          throw new UnmatchedBracketsException("Expression contains unmatched closing bracket")
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
