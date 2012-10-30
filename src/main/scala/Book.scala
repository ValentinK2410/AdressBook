package com.example
/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/15/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */

import ru.circumflex._, core._, orm._, web._, markeven._

class Book
    extends Record[Long, Book]
    with IdentityGenerator[Long, Book] {
  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val telephone = "telephone".TEXT.NOT_NULL
  val address = "address".TEXT.NOT_NULL
  val comment = "comment".TEXT
  val firstName = "name".TEXT.NOT_NULL
  val lastName = "lastname".TEXT
  val mail = "mail".TEXT
  val user = "user_id".BIGINT.NOT_NULL
      .REFERENCES(User)
      .ON_DELETE(CASCADE)
      .ON_UPDATE(CASCADE)

  def title = lastName() + " " + firstName()
  def marcComment = toHtml(comment())

  def PRIMARY_KEY = id
  def relation = Book

  def updateFromParams() {
    b.firstName := param("bfirstname")
    b.lastName := param("blastname")
    b.telephone := param("btelephone")
    b.address := param("baddress")
    b.comment := param("bcomment")
    b.mail := param("bemail")
  }
}

object Book
    extends Book
    with Table[Long, Book] {
  val bk = Book AS "bk"

  val mailUnique = UNIQUE(mail)

  validation
      .unique(_.mail)
      .pattern(_.mail,"^[a-zA-Z0-9_.-]{2,}@[a-zA-Z_]{2,}.[a-zA-Z]{2,6}$".r.pattern)
      .pattern(_.firstName,"^([a-zA-Z0-9_.-]){2,}|[А-Я][а-я]{2,}$".r.pattern)
      .pattern(_.lastName,"^([a-zA-Z0-9_.-]){2,}|[А-Я][а-я]{2,}$".r.pattern)

  def findByUser(user: User) = {
    SELECT(bk.*)
        .FROM(bk)
        .add(bk.user IS user)
        .list()
  }

}
