package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models.filesystem.Curator
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import models.LibrarySummary
import models.Watching
import models.User
 
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
  
  def refresh(user:String) = Action.async {
    import LibraryWrites._
    val libraryFuture = Curator.curateDatabase
    libraryFuture map { library =>
      val summary = library.map(LibrarySummary(_))
      val json = summary.map(s => Json.toJson(s)).getOrElse(JsNull)
      Ok(json)
    }
  }
  
  def librarySummary(user:String) = Action {
    import LibraryWrites._
    val library = Watching.library(User(user))
    val summary = LibrarySummary(library)
    val json = Json.toJson(summary)
    Ok(json)
  }
  
  def detail(user:String,showId:Long) = Action {
    val library = Watching.library(User(user))
    library.findShow(showId) match {
      case Some(show) => {
        import LibraryWrites._
        Ok(Json.toJson(show))
      }
      case None => Ok(JsNull)
    }
  }
}