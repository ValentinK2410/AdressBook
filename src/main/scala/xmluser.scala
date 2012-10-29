package com.example
import java.io.File
 import org.apache.commons.io.FileUtils
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

class XmlFiles(val fileName: File)
    extends ListHolder[XmlDescriptionFile]
    with XmlFile { files =>

   def elemName = "files"

  def descriptorFile = fileName

  def read = {
    case "file" => new XmlDescriptionFile(files)

  }
}

class CreateDirFile(f: File){
  val _ud = randomUUID.toString
  def ud = _ud
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

  def delDataXmlFile(ext: String, name: String, uuid: String, nameFile: File, xmlNameFile: File, del: Boolean){
    val files = new XmlFiles(xmlNameFile)
    files.load()
    val file = new XmlDescriptionFile(files)
    file._uuid := uuid
    file._ext := ext
    file._name := name
    files.delete(file)
    files.save()
   if (del){
    FileUtils.deleteQuietly(nameFile)
      FileUtils.deleteDirectory(f)
    }

  }

  def addDataXmlFile(ext: String, name: String, nameFile: File){
    val files = new XmlFiles(nameFile)
    files.load()
    val file = new XmlDescriptionFile(files)
    file._uuid := ud
    file._ext := ext
    file._name := name
    files.add(file)
    files.saveTo(nameFile)
  }


  def readDateXmlFile(nameFile: File)={
    val files = new XmlFiles(nameFile)
    files.load()
    files.children
    //val uuid = files.children.map(_.uuid)
    //val ext = files.children.map(_.ext)
   // val name = files.children.map(_.name)
   }

}



