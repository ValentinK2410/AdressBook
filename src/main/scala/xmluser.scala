package com.example
import java.io.File

import ru.circumflex._, xml._ ,core._
import core._
import web._

/**
 * Created with IntelliJ IDEA.
 * User: velen2
 * Date: 10/26/12
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */


class XmlDescriptionFile(@transient val files: XmlFiles)
    extends StructHolder {
  def elemName = "file"

  val _uuid = attr("uuid")
  def uuid = _uuid.getOrElse("")

  val _ext = attr("ext")
  def ext = _ext.getOrElse("")

  val _name = attr("name")
  def name = _name.getOrElse("")

  def ud = randomUUID
}

class XmlFiles
    extends ListHolder[XmlDescriptionFile]
    with XmlFile { files =>

  def elemName = "files"

  def descriptorFile = new File(uploadsRoot, "file.xml")

  def read = {
    case "file" => new XmlDescriptionFile(files)

  }
}

class CreateUserFile(pathFile: String){

  def createPath = {
  val f = new File(pathFile)
  try{
   f.mkdirs()
  }catch{
    case er: Exception  =>
      flash.update("msg", msg.fmt(er.getMessage))
      sendRedirect("/book/error")
  }
  }

  def deletePath = {
    val f = new File(pathFile)
    try{
     f.delete()
    }catch{
      case er: Exception  =>
        flash.update("msg", msg.fmt(er.getMessage))
        sendRedirect("/book/error")
    }
  }

}



