package com.seung.packages

import com.seung.packages.board.{CellState, Empty, Maru => MaruState, Batsu => BatsuState}

package object game {

  def toWinner(cellState: CellState): Winner = cellState match {
    case MaruState => Maru
    case BatsuState => Batsu
    case Empty => NoWinner
  }
}
