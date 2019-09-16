package com.seung.others

object TypeAlias extends App {
  type Entry = (Int, String)
  type EntryList = List[Entry]
  val eList: EntryList = List(1 -> "one", 2 -> "two", 3 -> "three")
  println(s"EntryList: $eList")
  val cell = new Cell("Type")
  println(s"typeA from String: ${List(cell.list("***"))}")
}

class Cell[A](val a: A) {
  type typeA = A
  def list(b: A): List[typeA] = {
    val list = List(b, b, b)
    list
  }
}
