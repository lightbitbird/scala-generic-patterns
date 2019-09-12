package com.seung.error

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object TryAndEither extends App {
  LoginService.login(name = "taro", password = "password1") match {
    case Right(user) => println(s"id: ${user.id}")
    case Left(InvalidPassword) => println(s"Invalid Password!")
    case Left(UserNotFound) => println(s"User Not Found!")
    case Left(PasswordLocked) => println(s"Password Locked!")
  }
  LoginService.testEitherFor(2, 6, 4)
  LoginService.testTryFor(5, 6, 9)
  LoginService.nonFatal()
}

sealed trait LoginError

case object InvalidPassword extends LoginError

case object UserNotFound extends LoginError

case object PasswordLocked extends LoginError

case class User(id: Long, name: String, password: String)

object LoginService {
  def login(name: String, password: String): Either[LoginError, User] = {
    (name, password) match {
      case ("taro", "password1") => Right(User(1, name, password))
      case ("taro", _) => Left(InvalidPassword)
      case (_, _) => Left(UserNotFound)
    }
  }

  def testEitherFor(a: Int, b: Int, c: Int) = {
    val v1 = Right(a)
    val v2 = Right(b)
    val v3 = Right(c)
    println(x = for {
      i1 <- v1
      i2 <- v2
      i3 <- v3
    } yield i1 * i2 * i3)
  }

  def testTryFor(a: Int, b: Int, c: Int) = {
    val v1 = Success(a)
//    val v2 = Success(b)
    val v2 = Failure(new RuntimeException("error")).recover { case e: Throwable => 0 }
    val v3 = Success(c)
    println(x = for {
      i1 <- v1
      i2 <- v2
      i3 <- v3
    } yield i1 * i2 * i3)
  }

  def nonFatal() = {
    try {
      throw new RuntimeException("runtime error!")
    } catch {
      case NonFatal(e) => println(s"Non fatal: $e")
      case t => println(s"Fatal: $t")
    }
  }
}
