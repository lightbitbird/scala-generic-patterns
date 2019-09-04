package com.seung.packages.board

private[packages] sealed abstract class CellState

private[packages] case object Empty extends CellState

private[packages] case object Maru extends CellState

private[packages] case object Batsu extends CellState

private[packages] class Board(private[packages] val cells: Map[(Int, Int), CellState],
                              private[packages] val cell: CellState) {
  private[packages] def put(row: Int, column: Int): Board = {
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

