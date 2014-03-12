package models.filesystem
import org.junit.runner.RunWith
import java.util.Date
import models.Show
import models.Season
import models.Episode

trait LibraryBuilder {
  
  def show(name:String,location:String)(seasons: => List[Season]) : List[Show] = {
    List( Show(0L,name,location,seasons) )
  }
  
  def season(number:Int)(episodes: => List[Episode]): List[Season] = {
    List(
      Season(number,episodes)
    )
  }
  
  def episode(number:Int,title:String,fileName:String,created:Date) : List[Episode] = {
    List(
      Episode(number,title,fileName,created)
    )
  }
}
