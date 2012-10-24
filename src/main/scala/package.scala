package com

import ru.circumflex._, core._, web._

package object example {
  val log = new Logger("com.example")

  def principal:Option[User1] = session.getAs[User1]("principal")

  def currentUser = principal
      .getOrElse(throw new IllegalStateException("User is not found."))
}