package com.example


import java.util.Date
import ru.circumflex._, core._, web._, freemarker._, orm._



class Main extends Router {
  'currentDate := new Date

  try {
    get("/")= sendRedirect("/book")
    sub("/book") = new BookRouter
    sub("/auth") = new AuthRouter
  } catch {
    case e: Exception  =>
      flash.update("msg", msg.fmt(e.getMessage))//("user.not.found"))
      sendRedirect("/auth/login")
  }
}

class BookRouter extends Router {

  get("/?") = {
    'contacts := Book.findByUser(currentUser)
    ftl("/addressbook/list.ftl")
  }

  post("/?") = {
    try {
      val b = Book.createFromBook()
      b.user := currentUser
      b.save()
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect("/book/~new")
    }
    flash.update("msg", msg.fmt("user.book.new.added"))
    sendRedirect("/book")
  }

  get("/~new") = ftl("/addressbook/new.ftl")

  sub("/:id") = {
    val contact = try {
      Book.get(param("id").toLong)
          .getOrElse(sendError(404))
    } catch {
      case e: Exception => sendError(404)
    }
    'contact := contact

    if (contact.user() != currentUser)
      sendError(404)

    get("/?") = ftl("/addressbook/review.ftl")

    get("/edit") = ftl("/addressbook/edit.ftl")

    post("/edit") = {
      try {
        val b = Book.createFromBook()
        b.user := currentUser
        b.save()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect("/book/~new")
      }
      flash.update("msg", msg.fmt("user.book.new.added"))
      sendRedirect("/book")
    }
    post("/update") = {
      try {
        val b = Book.createFromBook()
        b.id := param("id").toLong
        b.user := currentUser
        b.UPDATE()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect("/book")
      }
      flash.update("msg", msg.fmt("user.book.update"))
      sendRedirect("/book")
    }

    get("/~delete")
        .and(request.body.isXHR) = ftl("/addressbook/delete.p.ftl")
    delete("/?") = {
      contact.DELETE_!()
      flash.update("msg", msg.fmt("user.book.deleted"))
      sendRedirect("/book")
    }
  }
}
class AuthRouter extends Router {

  get("/login") = ftl("/auth/login.ftl")


  post("/login") = {
    val login = param("log")
    val pass = param("pass")
    val user = User1.findByLogin(login, pass)
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
      val u = User1.createFromParams()
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


