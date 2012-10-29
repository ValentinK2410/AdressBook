package com.example
/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/29/12
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
import java.util.Date
import ru.circumflex._, core._, web._

class Main extends Router {
  'currentDate := new Date


  try {
    get("/")= sendRedirect("/book")
    sub("/book") = new BookRouter
    sub("/auth") = new AuthRouter
  } catch {
    case e: IllegalStateException  =>
      flash.update("msg", msg.fmt("user.not.found"))
      sendRedirect("/auth/login")
    case er: Exception  =>
      flash.update("msg", msg.fmt(er.getMessage))
      sendRedirect("/book/error")
  }
}

