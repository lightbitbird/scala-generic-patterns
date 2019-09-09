package com.seung.typeparameters

object StackMain extends App {
  val stack = Stack().push(1)
  println(stack)
  val stack2 = stack.push(2)
  println(stack2)
  val stack3 = stack2.push(3)
  println(stack3)
  val stack3_2 = stack3.pop
  println(stack3_2)
  val stack2_1 = stack3_2._2.pop
  println(stack2_1)
  val stack1_0 = stack2_1._2.pop
  println(stack1_0)
}

trait Stack[+T] {
  def pop: (T, Stack[T])

  def push[E >: T](e: E): Stack[E]

  def isEmpty: Boolean
}

class NonEmptyStack[+T](private val top: T, private val rest: Stack[T]) extends Stack[T] {
  override def push[E >: T](e: E): Stack[E] = new NonEmptyStack[E](e, this)

  override def isEmpty: Boolean = false

  def pop: (T, Stack[T]) = (top, rest)
}

case object EmptyStack extends Stack[Nothing] {
  def pop: Nothing = throw new IllegalArgumentException("empty stack")

  def push[E >: Nothing](e: E) = new NonEmptyStack[E](e, this)

  def isEmpty: Boolean = true
}

object Stack {
  def apply(): Stack[Nothing] = EmptyStack
}
