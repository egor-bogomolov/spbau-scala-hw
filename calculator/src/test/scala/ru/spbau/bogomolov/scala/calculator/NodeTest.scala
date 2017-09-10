package ru.spbau.bogomolov.scala.calculator

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import ru.spbau.bogomolov.scala.calculator.exceptions.EvaluationFailedException
import ru.spbau.bogomolov.scala.calculator.tokens.Number
import ru.spbau.bogomolov.scala.calculator.tokens.operators.{Operator, Plus}

class NodeTest extends FunSuite with MockitoSugar with BeforeAndAfterEach {

  private[this] val value = 5.5
  private[this] val mockNumber = mock[Number]
  private[this] val mockOperator = mock[Operator]
  private[this] val mockOperatorHighPriority = mock[Operator]

  override def beforeEach(): Unit = {
    when(mockNumber.value).thenReturn(value)
    when(mockOperator.compute(value)).thenReturn(value)
    when(mockOperator.compute(value, value)).thenReturn(value + value)
    when(mockOperator.priority).thenReturn(0)
    when(mockOperatorHighPriority.priority).thenReturn(1)
  }

  test("testEvaluateNumber") {
    val node = new Node(mockNumber)
    assert(node.evaluate == value)
    node.left = new Node(mockNumber)
    assertThrows[EvaluationFailedException](node.evaluate)
  }

  test("testEvaluateIncorrectOperator") {
    val node = new Node(mockOperator)
    assertThrows[EvaluationFailedException](node.evaluate)
    node.left = new Node(mockNumber)
    assertThrows[EvaluationFailedException](node.evaluate)
  }

  test("testEvaluateSimpleExpression") {
    val node = createOperatorWithChildren
    assert(node.evaluate == value + value)
    node.left = null
    assert(node.evaluate == value)
  }

  test("testInsertToOperatorWithEmptyRight") {
    val node = new Node(mockOperator)
    val nodeNumber = new Node(mockNumber)
    node.insertNode(nodeNumber)
    assert(node.right == nodeNumber)
  }

  test("testInsertToOperatorWithChildren") {
    val node = createOperatorWithChildren
    val newNode = new Node(mockOperator)
    node.insertNode(newNode)
    assert(newNode.left == node)
  }

  test("testInsertToAloneNumber") {
    val nodeNumber = new Node(mockNumber)
    val newNode = new Node(mockOperator)
    nodeNumber.insertNode(newNode)
    assert(newNode.left == nodeNumber)
  }

  test("testInsertToNumberWithHighParent") {
    val nodeNumber = new Node(mockNumber)
    val highPriorityNode = new Node(mockOperatorHighPriority)
    val newNode = new Node(mockOperator)
    highPriorityNode.right = nodeNumber
    nodeNumber.parent = highPriorityNode
    nodeNumber.insertNode(newNode)
    assert(newNode.left == highPriorityNode)
  }

  test("testInsertToNumberWithEqualParent") {
    val nodeNumber = new Node(mockNumber)
    val equalPriorityNode = new Node(mockOperator)
    val newNode = new Node(mockOperator)
    equalPriorityNode.right = nodeNumber
    nodeNumber.parent = equalPriorityNode
    nodeNumber.insertNode(newNode)
    assert(newNode.left == equalPriorityNode)
  }

  test("testInsertToNumberWithLowParent") {
    val nodeNumber = new Node(mockNumber)
    val lowPriorityNode = new Node(mockOperator)
    val newNode = new Node(mockOperatorHighPriority)
    lowPriorityNode.right = nodeNumber
    nodeNumber.parent = lowPriorityNode
    nodeNumber.insertNode(newNode)
    assert(lowPriorityNode.right == newNode)
    assert(nodeNumber == newNode.left)
  }



  private def createOperatorWithChildren : Node = {
    val node = new Node(mockOperator)
    node.left = new Node(mockNumber)
    node.right = new Node(mockNumber)
    return node
  }
}
