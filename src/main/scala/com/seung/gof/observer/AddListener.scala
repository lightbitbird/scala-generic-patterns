package com.seung.gof.observer

object AddListener {
  def main(args: Array[String]): Unit = {
    ListenerObservable.addListener(new Listener {
      override def changed(newValue: Int): Unit = println(s"Changed to '$newValue'")
    })
    /** another expression */
        ListenerObservable.addListener((newValue: Int) => println(s"Changed to {$newValue}"))

    ListenerObservable.increment()
    ListenerObservable.increment()
    ListenerObservable.increment()
  }
}

trait Listener {
  def changed(newValue: Int): Unit
}

object ListenerObservable {
  private var num = 0
  private var listeners = Seq[Listener]()

  def increment(): Unit = {
    num = num + 1
    listeners.foreach(l => l.changed(num))
  }

  def addListener(listener: Listener) = listeners = listeners :+ listener
}

