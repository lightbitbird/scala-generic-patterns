package com.seung.mixin

object LazyMixin {
  def main(args: Array[String]): Unit = {
    //初期化の順は、A -> B -> C となる
    //lazyを活用して変数宣言の読み込み順を回避
    (new C).output
  }

  trait A {
    val foo: String
  }

  trait B extends A {
    lazy val bar = foo + "World"
  }

  class C extends B {
    val foo = "Hello"

    def output(): Unit = println(bar)
  }

}

