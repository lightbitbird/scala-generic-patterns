package com.seung.error

object ForEnumeratorMain extends App {
  println(ForEnumerator.getPostalCodeResult(1))
  println(ForEnumerator.getPostalCodeResult(2))
  println(ForEnumerator.getPostalCodeResult(3))
  println(ForEnumerator.getPostalCodeResult(4))
}

case class UserFor(id: Int, name: String, addressId: Option[Int])

case class Address(id: Int, name: String, postalCode: Option[String])

object ForEnumerator {
  val userDatabase: Map[Int, UserFor] = Map(
    (1 -> UserFor(1, "Tom", Some(1))),
    (2 -> UserFor(2, "Sarah", Some(2))),
    (3 -> UserFor(3, "Max", None))
  )

  val addressDatabase: Map[Int, Address] = Map(
    1 -> Address(1, "London", Some("000-0000")),
    2 -> Address(2, "Sydney", None)
  )

  sealed abstract class PostalCodeResult

  case class Success(postalCode: String) extends PostalCodeResult

  abstract class Failure extends PostalCodeResult

  case object UserNotFound extends Failure

  case object UserNotHasAddress extends Failure

  case object AddressNotFound extends Failure

  case object AddressNotHasPostalCode extends Failure

  def getPostalCodeResult(userId: Int): PostalCodeResult = {
    (for {
      user <- findUser(userId)
      address <- findAddress(user)
      postalCode <- findPostalCode(address)
    } yield Success(postalCode)).merge
  }

  def findUser(userId: Int): Either[Failure, UserFor] = {
    userDatabase.get(userId).toRight(UserNotFound)
  }

  def findAddress(user: UserFor): Either[Failure, Address] = {
    for {
      addressId <- user.addressId.toRight(UserNotHasAddress)
      address <- addressDatabase.get(addressId).toRight(AddressNotFound)
    } yield address
  }

  def findPostalCode(address: Address): Either[Failure, String] = {
    address.postalCode.toRight(AddressNotHasPostalCode)
  }

//  def getPostalCodeResult(userId: Int): PostalCodeResult = {
//    (for {
//      user <- findUser(userId)
//      address <- findAddress(user)
//      postalCode <- findPostalCode(address)
//    } yield postalCode) match {
//      case Some(s) => Success(s)
//      case None => AddressNotHasPostalCode
//    }
//  }
//
//  def findUser(userId: Int): Option[User] = {
//    userDatabase.get(userId)
//  }
//
//  def findAddress(user: User): Option[Address] = {
//    for {
//      addressId <- user.addressId
//      address <- addressDatabase.get(addressId)
//    } yield address
//  }
//
//  def findPostalCode(address: Address): Option[String] = {
//    address.postalCode
//  }
}

