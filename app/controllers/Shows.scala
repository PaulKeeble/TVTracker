package controllers

import play.api._
import play.api.mvc._
import play.api.data._

object Shows extends Controller {
  def list = Action {
    Ok("Index")
  }
}