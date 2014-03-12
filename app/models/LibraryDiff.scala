package models

import util.diff._

case class LibraryDiff(shows:List[Difference[ShowDiff]])

case class ShowDiff(name:String,location:String,seasons:List[Difference[SeasonDiff]])

case class SeasonDiff(number:Int,episodes:List[Difference[Episode]])

object LibraryDiff {
  implicit val EpisodeDiffable = new Diffable[Episode,Episode] {
    def lt = (l,r) => l.number < r.number
    def map(v:Episode) = v
    def diff(l:Episode,r:Episode) = if(lt(l,r)) Add(l)
      else if(gt(l,r)) Subtract(r)
      else if(l==r) Same()
      else Update(l)
  }
  
  implicit val SeasonDiffable = new Diffable[Season,SeasonDiff] {
    def lt = (l,r) => l.number < r.number
    def map(v:Season) = SeasonDiff(v.number,diff(v.episodes,List()))
    def diff(l:Season,r:Season) = if(lt(l,r)) Add(map(l))
      else if(gt(l,r)) Subtract(map(r))
      else if(l==r) Same()
      else Update(SeasonDiff(l.number,diff(l.episodes,r.episodes)))
  }
  
  implicit val ShowDiffable = new Diffable[Show,ShowDiff] {
    def lt = (l,r) => l.name < r.name
    def map(v:Show) = ShowDiff(v.name,v.location,diff(v.seasons,List()))
    def diff(l:Show,r:Show) = if(lt(l,r)) Add(map(l))
      else if(gt(l,r)) Subtract(map(r))
      else if(l==r) Same()
      else Update(ShowDiff(l.name,l.location,diff(l.seasons,r.seasons)))
  }
  
  implicit val LibraryDiffable = new Diffable[Library,LibraryDiff] {
    def lt = (l,r) => false
    def map(v:Library) : LibraryDiff = LibraryDiff(List())
    def diff(l:Library,r:Library) = {
      val showDiffs = diff(l.shows,r.shows)
      if(showDiffs.length==0)
        Same()
      else
        Update(LibraryDiff(showDiffs))
    }
  }
}