package com
import java.io.File
import ru.circumflex._, core._, web._

package object example {
  val log = new Logger("com.example")

  val uploadsRoot = new File(
    cx.getString("uploads.root")
        .getOrElse("src/main/webapp/uploads")
  )
  def principal:Option[User] = session.getAs[User]("principal")

  def currentUser = principal
      .getOrElse(throw new IllegalStateException("User is not found."))
}