package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

import java.util.Date

object Watching {
  def watched(user:User): Set[EpisodeId] = DB.withConnection { implicit c =>
    SQL("select episode from Watched where user={user}").on('user->user.name).as(long("episode").*).toSet
  }
  
  def watch(user:User,id:EpisodeId) = DB.withConnection { implicit c => 
    SQL("insert into Watched(episode,user) values({episode},{user})").on('episode->id,'user->user.name).executeInsert()
  }
  
  def unwatch(user:User,id:EpisodeId) = DB.withConnection { implicit c => 
    SQL("delete from Watched where user={user} and episode={episode}").on('episode->id,'user->user.name).executeUpdate()
  }
  
  def library(user:User) = {
    val lib = Library.current
    val watchSet = watched(user)
    lib.mapEpisodes{ (show,season,episode) =>
      if(watchSet.contains(episode.id))
        episode.copy(watched=true)
      else episode
    }
  }
}