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
      val b = new Book
      b.updateFromParams()
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
      'listFiles := currentUser.files.children
      ftl("/addressbook/view.ftl")
    }

    get("/edit") = {
      'listFiles := currentUser.files.children
      ftl("/addressbook/edit.ftl")
    }

    post("/edit") = {
      try {
        contact.updateFromParams()
        contact.save()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect("/book/~new")
      }
      flash.update("msg", msg.fmt("user.book.new.added"))
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
      val cdf = new CreateDirFile(contact)
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
        val index = fi.getName.lastIndexOf(".")
        cdf.createPath()

        fi.write(new File(cdf.nameFile)) // save user file

      }
      ftl("/uploads/add.ftl")
    }
    post("/download/:uuid") = {
      response.contentType("application/octet-stream")

      val file = currentUser.files.findByUuid(param("uuid"))
          .getOrElse(sendError(404))
      sendFile(file.file, file.name)
      ftl("/uploads/list.ftl")
    }
    post("/delete") = {
      val cdf = new CreateDirFile(contact)
      cdf.delDataXmlFile(contact)
      sendRedirect("/book/" + contact.id() + "/edit")
    }

  }
}
