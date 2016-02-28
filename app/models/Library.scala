package models

import play.api.db.DB
import anorm.SqlParser._
import anorm._

import java.util.Date
import util.diff.Same
import util.diff.Subtract
import util.diff.Diffable
import util.diff.Add

case class Episode(number: Int, title: String, filename: String, created: Date,id:Long=0L,watched:Boolean = false) {
  def <(that: Episode): Boolean = number < that.number
  def >(that: Episode): Boolean = number > that.number
}

case class Season(number: Int, episodes: List[Episode])

case class Show(id: Long, name: String, location: String, seasons: List[Season]) {
  def allEpisodes = seasons.foldRight(List[Episode]()) {
    (season, result) => result ++ season.episodes
  }

  def numberOfEpisodes = allEpisodes.length
}

case class Library(shows: List[Show]) {
  def findShow(id:Long) = shows.find(_.id == id)
  
  def mapEpisodes(f:(Show,Season,Episode)=>Episode ) = {
    val newShows = shows.map{ show => 
      val newSeasons = show.seasons.map { season => 
        val newEpisodes = season.episodes.map { episode => f(show,season,episode)}
        season.copy(episodes = newEpisodes)
      }
      show.copy(seasons=newSeasons)
    }
    copy(shows=newShows)
  }
}

object Library {
  def load: Library = DB.withConnection { implicit connection =>
    val rows = SQL("""select Shows.id,Shows.name,Shows.location,Episodes.id,Episodes.season,Episodes.number,Episodes.title,Episodes.filename,Episodes.created 
        from Shows join Episodes on Shows.id=Episodes.show""")
      .as(long("Shows.id") ~ str("Shows.name") ~ str("Shows.location") ~ long("Episodes.id") ~ int("Episodes.season") ~ 
          int("Episodes.number") ~ str("Episodes.title") ~ str("Episodes.filename") ~ date("Episodes.created") map (flatten) *)

    val byShow = rows.groupBy(r => (r._1, r._2, r._3))
    val shows = byShow.map {case ((showId, showName, showLocation), rows) => 
      val bySeason = rows.groupBy(_._5)

      val seasons = bySeason.map { case (season, rows) =>
        val episodes = rows.map(r => Episode( r._6, r._7, r._8, r._9,r._4))
        Season(season, episodes)
      }

      Show(showId, showName, showLocation, seasons.toList)
    }
    
    Library(shows.toList)
  }(play.api.Play.current)
  
  def current: Library = load
}