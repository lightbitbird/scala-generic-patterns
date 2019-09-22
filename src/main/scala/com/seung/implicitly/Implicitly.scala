package com.seung.implicitly

import com.seung.implicitly.Implicitly.{Connection, RichString}

import scala.language.implicitConversions

object ImplicitlyMain extends App {
  // 異なる型の引数への型変換 Implicit method
  Implicitly.intToBool(3)

  // 暗黙の型拡張 Implicit class
  implicit def enrichString(arg: String): RichString = new RichString(arg)

  println("Hi, ".smile)
  println("Raspberry:)".twice)

  implicit val connection = new Connection
  Implicitly.createTitle("Implicit")
  Implicitly.selectTitle
  Implicitly.updateTitle("Implicit")
  Implicitly.deleteTitle("Implicit")

  import TypeClassImplicitly._
  // Type class implicit
  println(sum(List("A", "B", "C")))
  println(sum(List(1, 2, 3)))

  // Type class implicit: companion Object
  println(sum(List(Rational(1, 1), Rational(2, 2))))
  // 単位元 & 結合即を満たす
  println(sum(List(Point(0, 1), Point(1, 0), Point(2, 3))))
  println(sum(List(Point(1, 1), Point(2, 2), Point(3, 3))))
  println(sum(List(Point(1, 2), Point(3, 4), Point(5, 6))))

  import Taps._

  // Use taps as debug prints
  "Hello, World".tap(s => println(s"\ntap:\n$s")).reverse.tap(println)
}

object Implicitly {
  // Implicit method
  // 異なる型の引数への型変換
  implicit def intToBoolean(arg: Int): Boolean = arg != 0

  def intToBool(num: Int): Boolean = {
    // Int 型から Boolean 型への暗黙の型変換が定義されている
    if (num) {
      println("1 is true.")
      true
    } else false
  }

  // Implicit class
  // コンパイラは、ある型に対するメソッド呼び出しを見つけた時、暗黙の型変換の変換先の型で、そのメソッドを定義しているものがないかを探索
  class RichString(val src: String) {
    def smile: String = src + ":-)"
    def twice: String = src + src
  }

  // Implicit parameter
  def createTitle(title: String)(implicit conn: Connection): Unit = conn.executeQuery(s"create title='${title}'")

  // Implicit parameter
  def selectTitle(implicit conn: Connection): Unit = conn.executeQuery(s"select")

  // Implicit parameter
  def updateTitle(title: String)(implicit conn: Connection): Unit = conn.executeQuery(s"update title='${title}'")

  // Implicit parameter
  def deleteTitle(title: String)(implicit conn: Connection): Unit = conn.executeQuery(s"delete title='${title}'")

  class Connection {
    def executeQuery(query: String): Unit = println(s"Execute: $query")
  }

}

object TypeClassImplicitly {

  trait Additive[A] {
    def plus(a: A, b: A): A

    def zero: A
  }

  def sumFromList[A](list: List[A])(implicit m: Additive[A]): A = list.foldLeft(m.zero)((x, y) => m.plus(x, y))

  def sum(list: List[String]): String = {
    // Type class implicit
    implicit object StringAdditive extends Additive[String] {
      def plus(a: String, b: String): String = a + b

      def zero: String = ""
    }
    sumFromList(list)
  }

  def sum(list: List[Int]): Int = {

    // Type class implicit
    implicit object IntAdditive extends Additive[Int] {
      override def plus(a: Int, b: Int): Int = a + b

      override def zero: Int = 0
    }
    sumFromList(list)
  }

  case class Rational(num: Int, den: Int)

  object Rational {

    // コンパニオンオブジェクトの中に、 型クラスのインスタンスを定義
    // importせず、Additive型クラスのインスタンスを使うことができる
    implicit object RationalAdditive extends Additive[Rational] {
      def plus(a: Rational, b: Rational): Rational = {
        if (a == zero) {
          b
        } else if (b == zero) {
          a
        } else {
          Rational(a.num * b.den + b.num * a.den, a.den * b.den)
        }
      }

      def zero: Rational = Rational(0, 0)
    }

  }

  // StringAdditive, IntAdditiveと違い、importせずAdditive型クラスのインスタンスを使うことができる
  def sum(list: List[Rational]): Rational = {
    sumFromList(list)
  }

  case class Point(x: Int, y: Int)

  object Point {
    // 単位元 & 結合則を満たす条件
    // m.plus(m.zero, t1) == t1  // 単位元
    // m.plus(t1, m.zero) == t1  // 単位元
    // m.plus(t1, m.plus(t2, t3)) == m.plus(m.plus(t1, t2), t3) // 結合則
    implicit object PointAdditive extends Additive[Point] {
      def plus(a: Point, b: Point): Point = Point(a.x + b.x, a.y + b.y)

      def zero: Point = Point(0, 0)
    }
  }

  // StringAdditive, IntAdditiveと違い、importせずAdditive型クラスのインスタンスを使うことができる
  def sum(list: List[Point]): Point = {
    sumFromList(list)
  }

}

// tap method implicit
// メソッドチェインの中にデバッグプリントととして利用できる
object Taps {
  implicit class Tap[T](self: T) {
    def tap[U](block: T => U): T = {
      block(self)
      self
    }
  }
}

