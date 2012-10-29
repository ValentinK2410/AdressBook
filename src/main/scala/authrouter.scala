package com.example
/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/29/12
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
import ru.circumflex.core._
import ru.circumflex.freemarker._
import ru.circumflex.web._


class AuthRouter extends Router {

  get("/login") = ftl("/auth/login.ftl")

  post("/login") = {
    val login = param("log")
    val pass = param("pass")
    val user = User.findByLogin(login, pass)
    if (user.isEmpty) {
      flash.update("msg", msg.fmt("user.login.failed"))
      sendRedirect("/auth/login")
    } else {
      flash.update("msg", msg.fmt("user.login.success"))
      request.session.update("principal", user.get)
      sendRedirect("/")
    }
  }
  // sub "/auth"
  get("/logout") = {
    session.remove("principal")
    sendRedirect("/")
  }
  // sub "/auth"
  get("/signup") = ftl("/auth/signup.ftl")
  // sub "/auth"
  post("/signup") = {
    try {
      val u = User.createFromParams()
      u.save()
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect("/auth/signup")
    }
    flash.update("msg", msg.fmt("user.new.added"))

    sendRedirect("/")

  }
}
