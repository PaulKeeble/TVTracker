package models

import scala.collection.mutable.ArrayBuffer

import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.db.DB

case class User(name:String)

object Configuration {
  //var users = ArrayBuffer[User](User("Jim"),User("John"))
  
  private val userParser = str("name") map { name => User(name) }
  
  def users: List[User] = DB.withConnection { implicit c =>
   SQL("Select name from Users").as(userParser.* )
  }  
  
  /*{
    Configuration(users.toList)
  }*/
  
  def add(u:User) = DB.withConnection { implicit c =>
   SQL("insert into Users(name) values ({name})")
     .on("name"->u.name).executeInsert()
  }  
  
  def delete(u:User) = DB.withConnection { implicit c =>
   SQL("delete from Users where name={name}")
     .on("name"->u.name).executeUpdate()
  }  
}