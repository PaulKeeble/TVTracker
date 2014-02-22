package models.filesystem

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import models.Library

class LibrarySpec extends SpecificationWithJUnit {
  "A library" should {
    "when empty diff empty will have no changes" in new libraries {
      val diff = empty.diff(empty)
      
      diff should be empty
    }
    
    "When empty diff containing values will show addition" in new libraries {
      pending
    }
  }
}

 trait libraries extends Scope {
   val empty = Library(List())
 }