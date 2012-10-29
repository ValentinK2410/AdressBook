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

class CreateDirFile(f: File){
  def ud = randomUUID
  def nameFile = f + "/" + ud

  def createPath = {
   try{
    if (f.isDirectory)
    f.mkdirs()
  }catch{
    case er: Exception  =>
      flash.update("msg", msg.fmt(er.getMessage))
      sendRedirect("/book/error")
  }
  }

  def deletePath = {
    try{
      if (!f.isFile)
     f.delete()
    }catch{
      case er: Exception  =>
        flash.update("msg", msg.fmt(er.getMessage))
        sendRedirect("/book/error")
    }
  }

  def addDateXmlFile(ext: String, name: String, nameFile: File){
    val files = new XmlFiles
    files.load()
    val file = new XmlDescriptionFile(files)
    file._uuid := ud
    file._ext := ext
    file._name := name
    files.add(file)
    files.saveTo(nameFile)
  }
}



