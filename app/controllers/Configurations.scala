package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Configuration
import models.User
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Json._
import models.Directory

object Configurations extends Controller {

  def show = Action { implicit request =>
    Ok(views.html.config.show())
  }
  
  def users = Action { implicit request =>
    val users = Configuration.users
    val jsonOfUsers = JsArray(
      users.map(u => Json.obj("name"->u.name))
    )
    
    Ok(Json.obj("users"->jsonOfUsers))
  }
  
  def addUser = Action(parse.json) { implicit request =>
    implicit val rds = (__ \ 'name).read[String]
    
    request.body.validate.asOpt match {
      case Some(name) => {
        Configuration.add(User(name))
        Ok("ok") 
      }
      case None => Status(503)
    }
  }
  
  def deleteUser(name:String) = Action { implicit request =>
    Configuration.delete(User(name))
    Ok("ok") 
  }
  
  def getDirectory = Action { implicit request =>
    val dir = Configuration.directory
    val path = dir.map(_.path).getOrElse("")
    val valid = dir.isDefined
    
    Ok(Json.obj("path"->path,"valid"->valid))
  }
  
  def changeDirectory = Action(parse.json) { implicit request =>
    implicit val rds = (__ \ 'path).read[String]
    
    request.body.validate.asOpt match {
      case Some(path) => {
        val success = Configuration.changeDirectory(Directory(path))
        Ok(Json.obj("path"->path,"valid"->success)) 
      }
      case None => Status(503)
    }
  }
}