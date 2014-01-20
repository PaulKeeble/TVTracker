package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Configuration

object Application extends Controller {
  def index = Action {
    if (!Configuration.isValid) {
      Redirect(routes.Configurations.show())
    } else {
      Ok(views.html.index("Your new application is ready."))
    }
  }
}