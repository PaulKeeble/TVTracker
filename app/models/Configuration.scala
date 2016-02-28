package models

import scala.collection.mutable.ArrayBuffer
import java.io.File

import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.db.DB

case class User(name: String)

case class Directory(path: String) {
  def toFile = new File(path)
}

object Configuration {
  private val userParser = str("name") map { name => User(name) }
  private val dirParser = str("path") map { path => Directory(path) }

  def users: List[User] = DB.withConnection { implicit c =>
    SQL("Select name from Users").as(userParser.*)
  }

  def add(u: User) = DB.withConnection { implicit c =>
    SQL("insert into Users(name) values ({name})")
      .on("name" -> u.name).executeInsert()
  }

  def delete(u: User) = DB.withConnection { implicit c =>
    SQL("delete from Users where name={name}")
      .on("name" -> u.name).executeUpdate()
  }
  
  def validScanDirectory(path: String): Boolean = {
    val candidateFile = new File(path)
    candidateFile.canRead() && candidateFile.isDirectory()
  }

  def directory: Option[Directory] = DB.withConnection { implicit c =>
    SQL("Select path from Directories").singleOpt(dirParser)
  }

  def changeDirectory(dir: Directory): Boolean = DB.withConnection { implicit c =>
    if (validScanDirectory(dir.path)) {
      SQL("delete from Directories").executeUpdate
      SQL("insert into Directories(path) values ({path})")
        .on("path" -> dir.path).executeInsert()
      true
    } else
      false
  }

  def isValid = {
    directory.isDefined && users.length>=1
  }
}