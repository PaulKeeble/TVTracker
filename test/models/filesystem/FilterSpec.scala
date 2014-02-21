package models.filesystem

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.SpecificationWithJUnit

class FilterSpec  extends SpecificationWithJUnit{
  "An ExtensionFilter" should {
    "reject extensions not in whitelist" in {
      val f = new ExtensionFilter(List())
      
      f("file.rdm") === false
    }
    
    "accept extension in whitelist" in {
      val f = new ExtensionFilter(List("mkv"))
      f("file.mkv") === true
    }
    
    "reject partial extensions" in {
       val f = new ExtensionFilter(List("mkv"))
      f("file.amkv") === false
    }
    
    "accept multiple extensions in Whitelist" in {
      val f = new ExtensionFilter(List("mkv","avi"))
      f("file.avi") and f("file.mkv") === true
    }
  }
}