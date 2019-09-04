package com.seung.packages.game

import com.seung.packages.board.{Board, Empty, Maru => MaruState, Batsu => BatsuState}


sealed abstract class Winner

case object NoWinner extends Winner

case object Maru extends Winner

case object Batsu extends Winner

class Game(private[this] val winner: Winner, private[this] val board: Board) {
  def play(row: Int, column: Int): Game = {
    if (board.canPut(row, column)) {
      val nextBoard = board.put(row, column)
      val g = new Game(judgeWinner(nextBoard), nextBoard)
      println(g)
      g
    } else {
      println(this)
      this
    }
  }

  private[this] def judgeWinner(board: Board): Winner = {
    val winPattern =
      Seq(((0, 0), (0, 1), (0, 2)), ((1, 0), (1, 1), (1, 2)), ((2, 0), (2, 1), (2, 2)),
        ((0, 0), (1, 0), (2, 0)), ((0, 1), (1, 1), (2, 1)), ((0, 2), (1, 2), (2, 2)),
        ((0, 0), (1, 1), (2, 2)), ((2, 0), (1, 1), (0, 2)))

    val cells = board.cells

    val winners = winPattern.map {
      // Set to a winner if values are all [Maru(or Batsu)]
      case (c1, c2, c3) if cells(c1) == cells(c2) && cells(c2) == cells(c3) => toWinner(cells(c1))
      case _ => NoWinner
    }

    if (winners.contains(Maru)) {
      Maru
    } else if (winners.contains(Batsu)) {
      Batsu
    } else {
      NoWinner
    }
  }

  private[this] def sign(row: Int, column: Int) = board.cells((row, column)) match {
    case Empty => " "
    case MaruState => "○"
    case BatsuState => "×"
  }

  override def toString =
    s"""Winner: ${winner}
       || ${sign(0, 0)}| ${sign(0, 1)}| ${sign(0, 2)}|
       || ${sign(1, 0)}| ${sign(1, 1)}| ${sign(1, 2)}|
       || ${sign(2, 0)}| ${sign(2, 1)}| ${sign(2, 2)}|
      """.stripMargin
}

object Game {
  def apply(): Game = new Game(NoWinner, Board())
}
