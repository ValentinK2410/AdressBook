package com.example


import java.util.Date
import java.io.File
import ru.circumflex._, core._, web._, freemarker._, orm._, xml._
import org.apache.commons.fileupload._
import disk.DiskFileItemFactory



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
    flash.update("msg", msg.fmt("user.book.added"))
    sendRedirect("/book")
  }

  get("/files") = {

    //sendFile(new File(uploadsRoot,"main.css"))
    val filesHere = (new java.io.File(uploadsRoot,".")).listFiles
    def Files = {
      for {file <- filesHere
      } yield file.getName
    }
    'Filess := Files
    ftl("/uploads/list.ftl")
  }

  get("/~new") = ftl("/addressbook/new.ftl")

  get("/error") = ftl("/err/error.ftl")

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

    get("/?") = ftl("/addressbook/view.ftl")

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

    get("/uploads") = ftl("/uploads/add.ftl")

    post("/uploads").and(request.body.isMultipart) = {
      val files = new XmlFiles
      files.load()
      val file = new XmlDescriptionFile(files)

      val items = request.body.parseFileItems(
        new DiskFileItemFactory(10240, new File(uploadsRoot, "tmp"))
      )
      // Process the uploaded items
      items.map { fi =>
        if (fi.isFormField) ctx.update(fi.getFieldName, fi.getString("utf-8"))
        else ctx.update(fi.getFieldName, fi)
      }

      // process File
      ctx.getAs[FileItem]("file").map { fi =>
        // upload file
        fi.write(new File(uploadsRoot,param("date") + fi.getName))

      }
      ftl("/uploads/add.ftl")
    }
  }
}
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

