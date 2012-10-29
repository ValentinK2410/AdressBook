package com.example
/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/29/12
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.FileItem
import ru.circumflex._ ,core._, freemarker._, web._


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
    sendFile(new File(uploadsRoot,"main.css"))
    /*val filesHere = (new java.io.File(uploadsRoot,".")).listFiles
    def Files = {
      for {file <- filesHere
      } yield file.getName
    }
    'Filess := Files
    */
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

    get("/?") = {
      val path = new File(uploadsRoot
          + "/"
          + currentUser.id.get.get.toString
          + "/"
          + contact.id.get.get.toString)
      if (path.isDirectory){
        val cdf = new CreateDirFile(path)
        'listFiles :=cdf.readDateXmlFile(
          new File(path + "/" + "file.xml"))
      }

      ftl("/addressbook/view.ftl")
    }

    get("/edit") = {
      val path = new File(uploadsRoot + "/"
          + currentUser.id.get.get.toString + "/"
          + contact.id.get.get.toString)
      if (path.isDirectory){
        val cdf = new CreateDirFile(path)
        'listFiles :=cdf.readDateXmlFile(
          new File(path + "/" + "file.xml"))
      }
      ftl("/addressbook/edit.ftl")
    }

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
      val path = new File(uploadsRoot + "/"
          + currentUser.id.get.get.toString + "/"
          + contact.id.get.get.toString)
      val pathXmlFile = new File(path
          + "/" + "file.xml")
      val cdf = new CreateDirFile(path)
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
      // add data xml file
        val index = fi.getName.indexOf(".")
        cdf.createPath
        cdf.addDataXmlFile(
          fi.getName
              .drop(index),
          fi.getName,
          pathXmlFile)
        fi.write(new File(cdf.nameFile)) // save user file

      }
      ftl("/uploads/add.ftl")
    }
    post("/download") = {
      response.contentType("text/plain")
      val path = new File(uploadsRoot + "/"
          + currentUser.id.get.get.toString + "/"
          + contact.id.get.get.toString)
      sendFile(new File(path, param("uuid")),param("name"))
      ftl("/uploads/list.ftl")
    }
    post("/delete") = {
      val path = new File(uploadsRoot + "/"
          + currentUser.id.get.get.toString + "/"
          + contact.id.get.get.toString)
      val pathXmlFile = new File(path
          + "/" + "file.xml")

        val cdf = new CreateDirFile(path)
       val del = {param("name") == ""}
      val pathh = new File(path + "/" + param("uuid"))
       cdf.delDataXmlFile(param("ext"), param("name"), param("uuid"), new File(path + "/" + param("uuid")),pathXmlFile,del)
      'title1 := param("ext") + "\n" + param("name") + "\n" + param("uuid") + "\n" + del.toString + "\n" + pathh
      ftl("/addressbook/edit.ftl")
    }

  }
}
