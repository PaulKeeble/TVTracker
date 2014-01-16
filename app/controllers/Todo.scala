package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Todo  extends Controller {
  def todo = Action {
    Ok(views.html.todo.todo())
  }
  
  def hello = Action {
    Ok(views.html.todo.hello())
  }
}