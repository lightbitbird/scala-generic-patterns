package com.seung.collections

import scala.collection.mutable.Stack
import scala.collection.immutable.{Queue, TreeMap, TreeSet}
import scala.collection.mutable

object Collections extends App {
  list
  array
  range
  stringOperation
  set
  map
  stack
  queue
  priorityQueue

  // List: default immutable collection
  def list() = {
    println("List: immutable --------")
    println(Nil)
    val ls1 = 3 :: Nil
    println(s"3 :: Nil = $ls1")
    val ls2 = 2 :: ls1
    println(s"2::ls1 = $ls2")
    val ls3 = 1 :: ls2
    println(s"1 :: ls2 = $ls3")
    val ls4 = 1 :: 2 :: 3 :: Nil
    println(s"1 :: 2 :: 3 :: Nil = $ls4")
  }

  // Array: mutable collection
  def array() = {
    println("\nArray: mutable --------")
    val ary1 = Array(1, 2, 3)
    ary1(1) = 4
    println(s"array = Array(${ary1(0)}, ${ary1(1)}, ${ary1(2)})")
    val ls = ary1.toList
    println(s"array.toList = $ls")
    val ary2 = ls.toArray
    println(s"list.toArray = Array(${ary2(0)}, ${ary2(1)}, ${ary2(2)})")
  }

  // Range: default immutable
  def range() = {
    println("\nRange: immutable --------")
    val rng1 = 1 to 5
    println(s"range(1 to 5) = $rng1")
    val ls = rng1.toList
    println(s"(1 to 5).toList = $ls")
    val untl = 1 until 5
    println(s"1 until 5 = $untl")
    val ls2 = untl.toList
    println(s"(1 until 5).toList = $ls2")
  }

  def stringOperation() = {
    println("\nString operation to collections --------")
    val s = "abcdefg"
    println(s""""abcdefg"(1) = ${s(1).toString}""")
    val map = s.map(c => c.toUpper)
    println(s"s.map(c => c.toUpper) = $map")
    val fil = s.filter(c => c != 'd')
    println(s"filter(c => c != 'd') = $fil")
  }

  // Set: immutable
  def set() = {
    println("\nSet: immutable --------")
    val s = Set(1, 1, 2, 2, 3, 3)
    println(s"Set(1, 1, 2, 2, 3, 3) => $s")
    val s1 = Set(1, 2, 3)
    val s2 = Set(3, 4, 5)
    val add = s1 ++ s2
    println(s"Set(1, 2, 3) ++ Set(3, 4, 5)  = $add")
    val diff = s1 -- s2
    println(s"Set(1, 2, 3) -- Set(3, 4, 5  = $diff")
    val intsct = s1.intersect(s2)
    println(s"s1.intersect(s2) = $intsct")
    println("TreeSet - Set in order")
    val tree = TreeSet(3, 4, 5, 1, 2)
    println(s"TreeSet(3, 4, 5, 1, 2) = $tree")
    val fil2 = tree.filter(i => i != 2)
    println(s"tre.filter(i => i != 2) = $fil2")
  }

  // Map: immutable
  def map() = {
    println("\nMap: immutable --------")
    val m1 = Map("a" -> 1, "b" -> 2, "c" -> 3)
    println(s"""m1: Map("a" -> 1,"b" -> 2, "c" -> 3) = $m1""")
    val m2 = Map(("a", 1), ("b", 2), ("c", 3))
    println(s"""m2: Map(("a", 1), ("b", 2), ("c", 3)) = $m2""")
    println(s"m1 == m2 => " + (m1 == m2))
    println(s"""m1.get("a") = ${m1.get("a")}""")
    println(s"""m1.get("d") = ${m1.get("d")}""")
    val toList = m1.toList
    println(s"m1.toList = $toList")
    val toMap = toList.toMap
    println(s"List.toList = $toMap")
    println("TreeMap - Map in order")
    val tree1 = TreeMap("a" -> 1, "b" -> 2, "c" -> 3)
    println(s"""m1: TreeMap("a" -> 1,"b" -> 2, "c" -> 3) = $tree1""")
    val add = tree1 + ("d" -> 4)
    println(s"""tree1 ++ "b" = $add""")
    val diff = tree1 - "b"
    println(s"""tree1 - "b" = $diff""")
  }

  // Stack:
  def stack() = {
    println("\nStack: immutable --------")
    val stck = Stack[Int]()
    println(s"Stack() = $stck")
    val stck2 = stck.push(1)
    println(s"stck.push(1) = $stck2")
    val stck3 = stck2.push(2)
    println(s"stck2.push(2) = $stck3")
    val pop = stck3.pop
    println(s"stck3.pop2 = $pop")
    val pop2 = stck3.pop
    println(s"pop.pop2 = $pop2")
  }

  // Queue
  def queue() = {
    println("\nQueue: immutable --------")
    val queue = Queue()
    println(s"Queue = $queue")
    val enq1 = queue.enqueue(1)
    println(s"Queue.enqueue() = $enq1")
    val enq2 = enq1.enqueue(2)
    println(s"enq1.enqueue(2) = $enq2")
    val deq1 = enq2.dequeue
    println(s"enq2.enqueue() = $deq1")
    val deq2 = deq1._2.dequeue
    println(s"deq1.enqueue(2) = $deq2")
  }

  // PriorityQueue
  def priorityQueue() = {
    println("\nPriorityQueue: mutable --------")
    val q = new mutable.PriorityQueue[Int]
    println(s"mutable.PriorityQueue = $q")
    q.enqueue(5)
    println("q.enqueue(5)")
    q.enqueue(1)
    println("q.enqueue(1)")
    q.enqueue(7)
    println("q.enqueue(7)")
    q.enqueue(3)
    println("q.enqueue(3)")
    q.enqueue(2)
    println("q.enqueue(2)")
    q.enqueue(6)
    println("q.enqueue(6)")
    println("q.dequeue() * 6")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"q.dequeue() = ${q.dequeue()}")
    println(s"queue = $q")
  }
}

