package models.filesystem

import java.util.Date

case class Episode(number:Int,title:String,filename:String,created:Date)

case class Season(number:Int,episodes:List[Episode])

case class Show(name:String,location:String,seasons:List[Season])

case class Library(shows:List[Show]) {
  def diff(that:Library) = {
    List()
  }
}