package ru.spbau.bogomolov.scala.calculator

import ru.spbau.bogomolov.scala.calculator.exceptions.{EvaluationFailedException, ParsingFailedException}
import ru.spbau.bogomolov.scala.calculator.tokens.operators.Operator
import ru.spbau.bogomolov.scala.calculator.tokens.Token

class Node(val token : Token) {

  private var left: Node = null
  private var right: Node = null
  private var parent: Node = this

  def getRoot: Node = if (parent == this) parent else parent.getRoot

  @throws(classOf[EvaluationFailedException])
  def evaluate: Double = {
    token match {
      case number: tokens.Number =>
        if (left == null && right == null) return number.value
        else throw new EvaluationFailedException("Evaluation of the expression failed")
      case operator: Operator =>
        if (left == null && right != null) return operator.compute(right.evaluate)
        else if (left != null && right != null) return operator.compute(left.evaluate, right.evaluate)
        else throw new EvaluationFailedException("Evaluation of the expression failed")
    }
  }

  @throws(classOf[ParsingFailedException])
  def insertNode(node: Node): Node = token match {
    case _: Operator =>
      if (right == null) {
        return addRightChild(node)
      } else  {
        return makeLeftChildOf(node)
      }
    case _: tokens.Number =>
      var currentNode = this
      while (currentNode.parent != currentNode && !hasLessPriority(currentNode.parent, node)) {
        currentNode = currentNode.parent
      }
      return currentNode.makeLeftChildOf(node)
  }

  @throws(classOf[ParsingFailedException])
  private def hasLessPriority(node1: Node, node2: Node): Boolean = {
    if (!node1.token.isInstanceOf[Operator] || !node2.token.isInstanceOf[Operator]) {
      throw new ParsingFailedException("Expression isn't correct")
    }
    return node1.token.asInstanceOf[Operator].priority < node2.token.asInstanceOf[Operator].priority
  }

  private def makeLeftChildOf(node : Node): Node = {
    if (parent != this) {
      parent.right = node
      node.parent = parent
    }
    node.left = this
    parent = node
    return node
  }

  private def addRightChild(node : Node): Node = {
    right = node
    node.parent = this
    return node
  }
}