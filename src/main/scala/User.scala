package com.example
/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/15/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */

import ru.circumflex._, core._, orm._, web._

class User1
    extends Record[Long, User1]
    with IdentityGenerator[Long, User1] {
  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val login = "login".TEXT
  val password = "password".TEXT
  val mail = "mail".TEXT


  def PRIMARY_KEY = id
  def relation = User1

}

object User1
    extends User1
    with Table[Long, User1] {
  val us = User1 AS "us"

  val loginUnique = UNIQUE(login)
  val mailUnique = UNIQUE(mail)

  validation
      .unique(_.login)
      .unique(_.mail)
      .pattern(_.mail,"^[a-zA-Z0-9_.-]{2,}@[a-zA-Z_]{2,}.[a-zA-Z]{2,6}$".r.pattern)
      .pattern(_.login,"^([a-zA-Z0-9_.-]){4,}$".r.pattern)

  def findById(id: Long) = {
    SELECT(us.*)
        .FROM(us)
        .add(us.id EQ id)
        .unique()
  }

  def findByLogin(login: String, password: String) = {
    SELECT(us.*)
        .FROM(us)
        .add(us.login EQ login)
        .add(us.password EQ sha256(password))
        .unique()
  }

  def createFromParams() = {
    val u = new User1
    u.login := param("log")
    // TODO Check password
    if (param("pass") != param("old-pass"))
      throw new ValidationException("User1.password.incorrect")
    u.password := sha256(param("pass"))
    u.mail := param("email")
    u
  }


  def userDelete_!(id:Long){
    us.id:=id
    us.DELETE_!()
  }
}
