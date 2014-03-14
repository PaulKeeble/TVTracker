package controllers

import play.api.libs.json._
import models._

object LibraryWrites {
  implicit val dateWrites = Writes.dateWrites("yyyy-MM-dd")
  
  implicit val episodeWrites = new Writes[Episode] {
    def writes(e:Episode) : JsValue = {
      Json.obj(
        "number" -> e.number,
        "title" -> e.title,
        "filename" -> e.filename,
        "watched" -> false,
        "created" -> e.created
      )
    }
  }
  
  implicit val seasonWrites = new Writes[Season] {
    def writes(s:Season) : JsValue = {
      Json.obj(
        "number" -> s.number,
        "episodes" -> s.episodes
      )
    }
  }
  
  implicit val showWrites = new Writes[Show] {
    def writes(s:Show) : JsValue = {
      Json.obj(
        "name"->s.name,
        "location"->s.location,
        "seasons"->s.seasons
      )
    }
  }
  
  implicit val libraryWrites = new Writes[Library] {
    def writes(l:Library) : JsValue = {
      Json.obj(
        "shows"->l.shows    
      )
    }
  }
  
  implicit val episodeSummaryWrites = new Writes[EpisodesSummary] {
    def writes(es:EpisodesSummary) : JsValue = {
      Json.obj(
        "watched"->es.watched,
        "total"->es.total
      )
    }
  }
  
  implicit val showSummaryWrites = new Writes[ShowSummary] {
    def writes(s:ShowSummary) : JsValue = {
      Json.obj(
        "id"->s.id,
        "name"->s.name,
        "lastCreated"->s.lastCreated,
        "episodes"->s.episodes
      )
    }
  }
  
  implicit val librarySummaryWrites = new Writes[LibrarySummary] {
    def writes(l:LibrarySummary) : JsValue = {
      Json.obj(
        "shows"->l.shows    
      )
    }
  }
}