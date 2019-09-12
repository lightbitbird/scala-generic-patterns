package com.seung.error

object TryAndEither extends App {
  LoginService.login(name = "taro", password = "password1") match {
    case Right(user) => println(s"id: ${user.id}")
    case Left(InvalidPassword) => println(s"Invalid Password!")
    case Left(UserNotFound) => println(s"User Not Found!")
    case Left(PasswordLocked) => println(s"Password Locked!")
  }
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
}