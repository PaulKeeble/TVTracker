package models.filesystem

import scala.util.parsing.combinator.RegexParsers
import scala.language.postfixOps

case class Extract(showname:String,season:Int,episodes:List[Int],title:String)

object FilenameParser extends RegexParsers {
  
  def number: Parser[Int] = """\d+""".r ^^ { _.toInt }
  
  def minus: Parser[String] = "-"
    
  //def seasonMarker: Parser[String] = "S" | "s"
  
  def season: Parser[Int] = number
  
  def episodeMarker: Parser[String] = "x" | "X"
  
  def episode: Parser[Int] = episodeMarker ~> number
  
  def episodes: Parser[List[Int]] = episode +

  def showname: Parser[String] = """[^-]*""".r ^^ { case str => str.trim}
  
  def title = ".*".r
  
  def filename: Parser[Extract] = (showname <~ minus) ~ season ~ (episodes <~ minus) ~ title ^^ {
    case showname ~ season ~ episodes ~ title => Extract(showname,season,episodes,title)
  }
  
  def parse(str:String) : Option[Extract] = {
    val result = parseAll(filename, removeExtension(str))
    if(result.successful)
      Some(result.get)
    else
      None
  }
  
  private def removeExtension(filename:String) = {
    val beginningExtension = filename.lastIndexOf(".")
    if(beginningExtension <1) filename
    else filename.substring(0, beginningExtension)
  }
}