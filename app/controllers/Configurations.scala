package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Configuration
import models.User

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Json._

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
}