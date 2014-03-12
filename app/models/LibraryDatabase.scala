package models

import anorm._
import play.api.db.DB
import util.diff._
import play.api.Play.current
import anorm.SqlParser._

object LibraryDatabase {
  def updateLibrary(library: Difference[LibraryDiff]) {
    def updateShows(lib: LibraryDiff) = lib.shows.foreach(updateShow(_))

    library match {
      case Update(lib) => updateShows(lib)
      case Add(lib) => updateShows(lib)
      case Subtract(lib) => updateShows(lib)
      case Same() =>
    }
  }

  def updateShow(show: Difference[ShowDiff]) {
    show match {
      case Update(s) => {
        val showId = DB.withConnection { implicit c =>
          SQL("update into Shows(location) values({location}) where name={name}").on('name -> s.name, 'location -> s.location).executeUpdate()
          val showId = SQL("select id from Shows where name={name}").on('name -> s.name).as(scalar[Long].single)
          showId
        }
        s.seasons.foreach(updateSeason(showId, _))
      }
      case Add(s) => {
        val showId = DB.withConnection { implicit c =>
          SQL("insert into Shows(name,location) values({name},{location})").on('name->s.name,'location->s.location).executeInsert()
        }
        s.seasons.foreach(updateSeason(showId.get, _))
      }
      case Subtract(s) =>
      case Same() =>
    }
  }

  def updateSeason(showId: Long, season: Difference[SeasonDiff]) {
    
    season match {
      case Update(s) => {
        val seasonId = s.number
        s.episodes.foreach( updateEpisode(showId,seasonId,_))
      }
      case Add(s) => {
        val seasonId = s.number
        s.episodes.foreach( updateEpisode(showId,seasonId,_))
      }
      case Subtract(s) =>
      case Same() =>
    }
  }
  
  def updateEpisode(showId:Long, seasonNumber:Int, episode:Difference[Episode]) {
    episode match {
      case Add(e) => {
        DB.withConnection { implicit c=>
          SQL("insert into Episodes(show,season,number,title,filename,created) values({show},{season},{number},{title},{filename},{created})")
          .on('show->showId,'season->seasonNumber,'number->e.number,'title->e.title,'filename->e.filename,'created->e.created).executeInsert()
        }
      }
      
      case Update(e) => {
        DB.withConnection { implicit c =>
          SQL("update into Episodes(title,filename,created) values({title},{filename},{created}) where show={show} and season={season} and number={episodeNumber}")
          .on('show->showId,'season->seasonNumber,'number->e.number,'title->e.title,'filename->e.filename,'created->e.created).executeUpdate()
        }
      }
      
      case Subtract(e) =>
      case Same() =>
    }
  }
}