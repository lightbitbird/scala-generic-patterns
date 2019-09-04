package com.seung.packages

import com.seung.packages.game.Game
import com.seung.packages.board.Board

object PackageMain extends App {
  val b = Board()
  b.put(0, 0).put(0, 1)
  println(b)
  println("-------------------------")
  val game = Game()
  game.play(0, 0).play(1, 1).play(0, 2).play(1, 0).play(0, 1)
}
