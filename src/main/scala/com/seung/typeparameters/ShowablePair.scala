package com.seung.typeparameters

abstract class Show {
  def show: String
}

class ShowablePair[T1 <: Show, T2 <: Show](val t1: T1, val t2: T2) extends Show {
  override def show: String =  s"(${t1.show},${t2.show})"
}
