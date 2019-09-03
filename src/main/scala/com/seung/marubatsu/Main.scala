package com.seung.marubatsu

import com.seung.marubatsu.game.Game
import com.seung.marubatsu.board.Board

object Main extends App {
  val b = Board()
  b.put(0, 0).put(0, 1)
  println(b)
  println("-------------------------")
  val game = Game()
  game.play(0, 0).play(1, 1).play(0, 2).play(1, 0).play(0, 1)
}
