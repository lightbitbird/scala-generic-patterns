package com.seung.mixin


object SelfType extends App {
  val gu: GreetUser = new GreetUser with HelloGreeter
  gu.start()
  GreetUser.start()
}

object GreetUser extends GreetUser with HelloGreeter

trait Greeter {
  def greet(): Unit
}

trait GreetUser {
  // trait 自身がミックスインされるインスタンスは、[Greeter]というトレイト
  // ではなくてはならないということを指定したもの
  self: Greeter =>

  def start(): Unit = greet()

  override final def toString = "GreetUser"
}

trait HelloGreeter extends Greeter {
  def greet(): Unit = println("Hello!")
}
