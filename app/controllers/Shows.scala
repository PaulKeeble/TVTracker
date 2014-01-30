package controllers

import play.api._
import play.api.mvc._
import play.api.data._

object Shows extends Controller {
  def show = Action {
    Ok(views.html.shows.shows())
  }
  
  def showListPartial = Action {
    Ok(views.html.shows.showListPartial())
  }
  
  def showDetailPartial = Action {
    Ok(views.html.shows.showDetailPartial())
  }
}