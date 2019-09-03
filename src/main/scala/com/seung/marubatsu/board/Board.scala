package com.seung.marubatsu.board

sealed abstract class CellState

case object Empty extends CellState

case object Maru extends CellState

case object Batsu extends CellState

class Board(val cells: Map[(Int, Int), CellState], val cell: CellState) {
  def put(row: Int, column: Int): Board = {
    new Board(cells + ((row, column) -> cell), getNext(cell))
  }

  private[this] def getNext(current: CellState): CellState = {
    current match {
      case Empty => Empty
      case Maru => Batsu
      case Batsu => Maru
    }
  }

  def canPut(row: Int, column: Int): Boolean = cells((row, column)) == Empty

  override def toString = s"Board($cells, $cell)"
}

object Board {
  def apply(): Board = {
    val keyValues = for (row <- 0 to 2; column <- 0 to 2) yield (row, column) -> Empty
    new Board(keyValues.toMap, Maru)
  }
}

