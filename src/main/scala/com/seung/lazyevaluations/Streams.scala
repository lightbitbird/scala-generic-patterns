package com.seung.lazyevaluations

object Streams extends App {
  stream
  endlessStream
  iterate(4)

  // benchmark ----------------------------
  //フィボナッチ数
  val fibs: LazyList[Int] = 0 #:: 1 #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }
  val list = fibs.take(1000000).toList
  // 1000000回計算してから処理が入る
  benchmark(
    list.filter(x => x % 2 == 0).map(x => x * 3).take(5).foldLeft(0)(_ + _)
  )
  // 遅延評価なら処理を先延ばしできるため5回で済む
  benchmark(
    fibs.filter(x => x % 2 == 0).map(x => x * 3).take(5).foldLeft(0)(_ + _)
  )
  // benchmark ----------------------------
  streamOrg()
  thunk(true)

  def stream() = {
    println("\nStream(LazyList): immutable --------")
    val s = 1 #:: 2 #:: 3 #:: 4 #:: LazyList.empty
    //    val s = 1 #:: 2 #:: 3 #:: 4 #:: Stream.empty
    println(s"1 #:: 2 #:: 3 #:: 4 #:: LazyList.empty = $s")
    val sval1 = s(0)
    println(s"s(0) = $sval1")
    val sval2 = s(1)
    println(s"s(1) = $sval2")
    val sval3 = s(2)
    println(s"s(2) = $sval3")
    val toList = s.toList
    println(s"toList = $toList")
  }

  def endlessStream() = {
    println("\nEndless Stream(LazyList) --------")
    println("#:: = cons")
    lazy val nines: LazyList[Int] = 9 #:: nines
    //  lazy val nines: LazyList[Int] = LazyList.cons(9, nines)

    println(s"nines(10000) = ${nines(10000)}")
    println(s"nines(100000) = ${nines(100000)}")
    val tenList = nines.take(10).toList
    println(s"nines.take(10).toList = $tenList")
  }

  def iterate(n: Int) = {
    println("\niterate --------")
    println("\nlazy process recursively --------")
    val s = LazyList.iterate(n) {
      n =>
        println("Calculate! 3  + " + n)
        3 + n
    }
    println("初期値=n, 公差=(3 + n)の等差数列となって出力される")
    println(s"s(0), s(1), s(2) = ${s(0)}, ${s(1)}, ${s(2)}")

    println(s"s(10) = ${s(10)}")
    println(s"Once again: s(10) = ${s(10)}, already calculated in the instance.")
    println(s"s(5) = ${s(5)}")

    println("lazy val ----------")
    lazy val ten = {
      println("Calculate: ten ")
      2 * 5
    }
    println(s"ten = $ten")
    println(s"ten = $ten, already calculated in the instance.")
  }

  // Lazy evaluation Benchmark
  def benchmark[T](f: => T) = {
    println("\nlazy evaluation benchmark ---------")
    val begin = System.currentTimeMillis()
    val result = f
    val end = System.currentTimeMillis()
    val formatter = java.text.NumberFormat.getNumberInstance()
    println(s"time: ${formatter.format(end - begin)} milli sec")
    result
  }

  def thunk(cond: Boolean): Int = {
    println("\nThunk functional programming --------")

    def myIf[T](condition: Boolean, ifTrue: () => T, ifFalse: () => T): T = {
      if (condition) ifTrue() else ifFalse()
    }

    myIf(cond, () => {
      println("hoge");
      1
    }, () => {
      println("fuga");
      2
    })
  }

  def streamOrg() = {
    println("\nOriginal Stream --------")
    println("val sevens: StreamOrg[Int] = StreamOrg.cons(7, sevens)")
    lazy val sevens: StreamOrg[Int] = StreamOrg.cons(7, sevens)
    val head = sevens.head
    println(s"sevens.head = $head")
    val tailHead = sevens.tail.head
    println(s"sevens.tail.head = $tailHead")
  }
}

trait StreamOrg[+A] {
  def head: Option[A] = this match {
    case EmptyStream => None
    case Cons(h, _) => Some(h())
  }

  def tail: StreamOrg[A] = this match {
    case EmptyStream => throw new NoSuchMethodError()
    case Cons(_, t) => t()
  }
}

case object EmptyStream extends StreamOrg[Nothing]

case class Cons[+A](h: () => A, t: () => StreamOrg[A]) extends StreamOrg[A]

object StreamOrg {
  def cons[A](h: => A, t: => StreamOrg[A]): StreamOrg[A] = {
    lazy val head = h
    lazy val tail = t
    Cons(() => head, () => tail)
  }

  def empty[A]: StreamOrg[A] = EmptyStream
}



