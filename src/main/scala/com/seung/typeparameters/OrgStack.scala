package com.seung.typeparameters

object StackMain extends App {
  val stack = StackOrg().push(1)
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

trait StackOrg[+T] {
  def pop: (T, StackOrg[T])

  // 共変の引数対応: 型パラメータに反変[E >: T]を指定する
  def push[E >: T](e: E): StackOrg[E]

  def isEmpty: Boolean
}

class NonEmptyStackOrg[+T](private val top: T, private val rest: StackOrg[T]) extends StackOrg[T] {
  // 共変の引数対応: 型パラメータに反変[E >: T]を指定する
  override def push[E >: T](e: E): StackOrg[E] = new NonEmptyStackOrg[E](e, this)

  override def isEmpty: Boolean = false

  def pop: (T, StackOrg[T]) = (top, rest)
}

case object EmptyStackOrg$ extends StackOrg[Nothing] {
  def pop: Nothing = throw new IllegalArgumentException("empty stack")

  // 共変の引数対応: 型パラメータに反変[E >: T]を指定する
  def push[E >: Nothing](e: E) = new NonEmptyStackOrg[E](e, this)

  def isEmpty: Boolean = true
}

object StackOrg {
  def apply(): StackOrg[Nothing] = EmptyStackOrg$
}
