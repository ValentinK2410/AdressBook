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
      'listFiles := contact.files.children
      ftl("/addressbook/view.ftl")
    }

    get("/edit") = {
      'listFiles := contact.files.children
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
        contact.files.root.mkdirs()// create are dir and file users
        val file = contact.files.read("file")
        val ud = contact.files.ud // randomUUID
        file._uuid := ud // add data in file.xml
        file._name := fi.getName // add data in file.xml
        contact.files.add(file)
        contact.files.save()

        fi.write(new File(contact.files.root, ud)) // save user file

      }
      sendRedirect(prefix + "/uploads")
    }
    post("/download/:uuid") = {
      response.contentType("application/octet-stream")

      val file = contact.files.findByUuid(param("uuid"))
          .getOrElse(sendError(404))
      sendFile(file.file, file.name)
      ftl("/uploads/list.ftl")
    }
    post("/delete") = {
     val del = request.params.list("uuid")
      'size :=del
     del.foreach(contact.files.deleteByUuid(_))
      contact.files.save()
      sendRedirect(prefix + "/edit")
    }

  }
}
