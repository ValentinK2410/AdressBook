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

  def file = new File(files.root, uuid + "." + ext)

}

class XmlFiles(val book: Book)
    extends ListHolder[XmlDescriptionFile]
    with XmlFile { files =>

  def user = note.user()
  val root = new File(uploadsRoot, user.id() + "/" + book.id())

  def elemName = "files"

  def descriptorFile =  new File(root, "file.xml")

  def read = {
    case "file" => new XmlDescriptionFile(files)
  }

  def findByUuid(uuid: String) = children.find(_.uuid == uuid)
}

class CreateDirFile(book: Book){
  val ud = randomUUID
  def nameFile = f + "/" + ud

  val user = book.user()

  def createPath() {
    f.mkdirs()
  }

  def deletePath() {
    if (f.isDirectory)
      FileUtils.deleteDirectory(f)
  }

  def delDataXmlFile(uuid: String) {
    val fd = user.files.findByUuid(uuid)
    fd.foreach(user.files.delete(_))
    user.files.save()
  }

  def addDataXmlFile(ext: String, name: String) {
    val file = new XmlDescriptionFile(files)
    file._uuid := ud
    file._ext := ext
    file._name := name
    files.add(file)
    files.save()
  }

}



