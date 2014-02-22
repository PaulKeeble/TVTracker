package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models.filesystem.Curator
import play.api.libs.json._

import models.LibrarySummary
 
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
  
  def librarySummary = Action {
    import LibraryWrites._
    
    val library = Curator.filesystemLibrary
    val summary = library.map(LibrarySummary(_))
    val json = summary.map(s => Json.toJson(s)).getOrElse(JsNull)
    Ok(json)
  }
}