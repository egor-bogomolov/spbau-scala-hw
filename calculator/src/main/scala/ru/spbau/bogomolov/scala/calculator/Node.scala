package ru.spbau.bogomolov.scala.calculator

import ru.spbau.bogomolov.scala.calculator.exceptions.{EvaluationFailedException, ParsingFailedException}
import ru.spbau.bogomolov.scala.calculator.tokens.operators.Operator
import ru.spbau.bogomolov.scala.calculator.tokens.Token

/**
  * A node in computation tree. Has a token which can be Operator or Number.
  * If node represents an unary operator it has one right child.
  * If node represents a binary operator it has 2 children.
  * If node represents a number it has no children.
  */
class Node(val token : Token) {

  var left: Node = null
  var right: Node = null
  var parent: Node = this

  /**
    * @return root of the tree.
    */
  def getRoot: Node = if (parent == this) parent else parent.getRoot

  /**
    * @throws EvaluationFailedException if tree contains invalid nodes (invalid number of children).
    * @return result of computation of the expression that corresponds to the tree.
    */
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

  /**
    * Inserts a node in the tree according to rules of building of computation trees.
    * @throws ParsingFailedException if nodes had invalid combination of tokens.
    * @return node that was inserted (for simplicity of code)
    */
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

  /**
    * Checks that both nodes have operators as tokens and compares their priorities.
    * @throws ParsingFailedException if any node hasn't operator as token.
    * @return true if the first node's operator has less priority otherwise false.
    */
  @throws(classOf[ParsingFailedException])
  def hasLessPriority(node1: Node, node2: Node): Boolean = {
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