package controllers

import play.api._
import play.api.mvc._
import play.api.data._

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import models.EpisodeId
import models.Watching
import models.User

object Episodes extends Controller {
  implicit val userReads: Reads[User] = (JsPath \ "name").read[String](minLength[String](1)) map(User(_))
  
  def watch(episodeId:EpisodeId) = Action(BodyParsers.parse.json) { request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => BadRequest(Json.obj("status"->"Failed","message"->JsError.toFlatJson(errors))),
      user => {
        Watching.watch(user,episodeId)
        val response = Json.obj("status"->"Success")
        Ok(response)
      }
    )
  }
  
  def unwatch(episodeId:EpisodeId) = Action(BodyParsers.parse.json) { request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => BadRequest(Json.obj("status"->"Failed","message"->JsError.toFlatJson(errors))),
      user => {
        Watching.unwatch(user,episodeId)
        val response = Json.obj("status"->"Success")
        Ok(response)
      }
    )
  }
}