package com.seung.others

import scala.util.Try

class User(private val name: String, private val age: Int)

object User {
  def unapply(obj: Any): Option[(String, Int)] = {
    if (obj.isInstanceOf[User]) {
      val user = obj.asInstanceOf[User]
      Some((user.name, user.age))
    } else if (obj.isInstanceOf[String]) {
      val strs = obj.asInstanceOf[String].split("@")
      val name = strs.headOption
      val age = Try {
        strs.tail.headOption.map(_.toInt)
      }.toOption.flatten
      (name, age) match {
        case (Some(n), Some(a)) => Some((n, a))
        case _ => None
      }
    } else {
      None
    }
  }
}

object Unapply extends App {
  def printPatternMatched(obj: AnyRef): Unit = {
    obj match {
      case User(name, age) => println(s"Name: $name, Age: $age")
      case _ => println("can't extract")
    }
  }

  printPatternMatched(new User("Taro", 17))
  printPatternMatched(new User("Jiro", 16))
}

