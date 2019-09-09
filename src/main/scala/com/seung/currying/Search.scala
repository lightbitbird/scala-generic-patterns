package com.seung.currying

object SearchMain extends App {
  println("Search with type parameter and standard search -------------")
  println(Search.search(Seq(2, 4, 6, 7), { x: Int => x % 2 == 1 }))
  println(Search.search(Seq("a", "b", "c", "d"), { x: String => x == "c" }))
  println("Search wth currying -------------")
  println(
    """
      |x: String のような型注釈が不要。
      |無名関数を () なしで {} 式として渡せ無名関数の x => x == "a の部分が、search(Seq("a", "b", "c", "d")) で
      |作られた {} のスコープのように見え、可読性が向上。
      |""".stripMargin)
  println(SearchCurrying.search(Seq(2, 4, 6, 7)) { x: Int => x % 2 == 1 })
  println(
    SearchCurrying.search(Seq("a", "b", "c", "d")) {
      x: String => x == "b"
    })
}


object Search {
  def search[A](seq: Seq[A], f: A => Boolean): Boolean = {
    def searchRec(i: Int): Boolean = {
      if (seq.length == i) false
      else if (f(seq(i))) true
      else searchRec(i + 1)
    }

    searchRec(0)
  }
}

object SearchCurrying {
  def search[A](seq: Seq[A])(f: A => Boolean): Boolean = {
    def searchRec(i: Int): Boolean = {
      if (seq.length == i) false
      else if (f(seq(i))) true
      else searchRec(i + 1)
    }

    searchRec(0)
  }
}

