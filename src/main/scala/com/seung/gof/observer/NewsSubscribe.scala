package com.seung.gof.observer

import java.time.ZonedDateTime

import scala.collection.mutable.{ArrayBuffer, Map}

object NewsSubscribe extends App {
  val subscribers = Seq[String]("Mark", "Peter", "Alex")
  val generator = GenerateNews()
  subscribers.foreach(name => {
    generator.addSubscriber(new Subscriber(name, generator))
  })
  generator.create("This is sent from News Generator.")
}

abstract class Job(val name: String, content: String)

case class NewsJob(override val name: String, content: String) extends Job(name, content)

abstract class Task[A <: Observer[B], B <: Any](observer: A, content: String)

case class NewsTask(subscriber: Subscriber, content: String) extends Task[Subscriber, GenerateNews](subscriber, content)

trait Observable[A <: Task[B, C], B <: Observer[C], C <: Any] {
  val subs: Map[String, B]
  var changed: Option[Boolean]

  def addSubscriber(sub: B): Unit = {
    subs += ((sub.getName(), sub))
  }

  def notifyChanges(tasks: Seq[A]): Unit

  def notifyMessage(tasks: A): Unit

  def notifyObservers(tasks: Seq[A]): Unit

  def deleteObserver(sub: Subscriber): Unit

  def setChanged(_changed: Boolean): Unit = {
    this.changed = Option(_changed)
  }

  def currentTime(): ZonedDateTime = {
    ZonedDateTime.now()
  }
}

trait Observer[T] {
  def update(o: T): Unit

  def getName(): String
}

object GenerateNews {
  def apply(): GenerateNews = {
    new GenerateNews()
  }
}

class GenerateNews() extends Observable[NewsTask, Subscriber, GenerateNews] {
  val subs: Map[String, Subscriber] = Map()
  var changed: Option[Boolean] = Option(false)

  def create(_content: String): Unit = {
    val tasks = new ArrayBuffer[NewsTask]
    for (sub <- subs) {
      tasks += NewsTask(sub._2, _content)
    }
    notifyChanges(tasks.toSeq)
  }

  def notifyChanges(tasks: Seq[NewsTask]): Unit = {
    setChanged(true)
    notifyObservers(tasks)
  }

  def notifyMessage(task: NewsTask): Unit = {
    task.subscriber.setNews(
      NewsJob(task.subscriber.getName(),
        s"""
           |Dear ${task.subscriber.getName()}
           |
           |${task.content}
           |
       """.stripMargin))
    task.subscriber.update(this)
  }

  def notifyObservers(tasks: Seq[NewsTask]): Unit = {
    if (this.changed.isDefined && this.changed.get)
      tasks.foreach(notifyMessage)
  }

  def deleteObserver(sub: Subscriber): Unit = {
    subs -= sub.getName()
  }

}

class Subscriber(private val name: String, generator: GenerateNews) extends Observer[GenerateNews] {
  var news: NewsJob = NewsJob("", "")

  def update(o: GenerateNews): Unit = {
    o match {
      case n: GenerateNews => {
        println(n.currentTime())
        println(news.content)
      }
      case _ => println("Unexpected error.")
    }
  }

  def setNews(_news: NewsJob): Unit = {
    news = _news
  }

  def getName(): String = {
    name
  }
}

