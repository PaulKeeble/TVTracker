package models.filesystem

import org.specs2.matcher.Matchers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.specification.Scope
import org.specs2.mutable.Specification

@RunWith(classOf[JUnitRunner])
class FilenameParserSpec extends Specification{
  "FilenameExtractor" should {
    "find elements for simple name" in {
      check("Showname - 1x01 - a title.mkv", Extract("Showname",1,List(1),"a title"))
    }
    
    "find elements for bracket showname" in {
      check("Brackets - 1x01 - title (1).mkv", Extract("Brackets",1,List(1),"title (1)"))
    }
    
    "find elements for lower case season and episode" in {
      check("lowercase - 1x01 - title.mkv",Extract("lowercase",1,List(1),"title"))
    }
    
    "find elements for high numbered season and episode" in {
      check("numbered - 22x037 - title.mkv",Extract("numbered",22,List(37),"title"))
    }
    
    "find elements for punction title" in {
      check("Showname - 1x01 - I,s...R\u00F4;ti'!-&.mkv", Extract("Showname",1,List(1),"I,s...R\u00F4;ti'!-&"))
    }
    
    "should accept files without extensions" in {
      
      check("Showname - 1x01 - title",Extract("Showname",1,List(1),"title"))
    }
    
    "should find multiple episode markers" in {
      check("Showname - 1x01x02 - title",Extract("Showname",1,List(1,2),"title"))
    }
    
    "should fail when no episodes at all" in {
      FilenameParser.parse("Showname - 1 - title") must beNone
    }
  }
  
  def check(str:String,expected:Extract) = {
    FilenameParser.parse(str) must beSome(expected)
  }
}