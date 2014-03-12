package util.diff

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import Diff._
import org.specs2.specification.Scope

@RunWith(classOf[JUnitRunner])
class DiffSpec extends Specification {
  "Diff" should {
    "add left elements" in {
      val left = List(1,2,3)
      val right = List()
      
      diff(left, right) should be equalTo(List(Add(1),Add(2),Add(3)))
    }
    
    "subtract right elements" in {
      val left = List()
      val right = List(1,2,3)
      
      diff(left, right) should be equalTo(List(Subtract(1),Subtract(2),Subtract(3)))
    }
    
    "no difference with no elements" in {
      val l1 = List()
      
      diff(l1,l1) should be equalTo(List[Difference[Int]]())
    }
    
    "no difference with same elements" in {
      val l2 = List(1,2,3)
      diff(l2,l2) should be equalTo(List[Difference[Int]]())
    }
    
    "mixed differences of add and subtract" in {
      val l1 = List(1,3)
      val l2 = List(0,3)
      
      diff(l1,l2) should be equalTo(List(Subtract(0),Add(1)))
    }
    
    "Updates on id based tuples" in {
      implicit val diffable = new Diffable[(Int,Int),(Int,Int)] {
        def lt = (l,r) => l._1 < r._1
        def map(v:(Int,Int)) = v
        def diff(l:(Int,Int),r:(Int,Int)) = if(lt(l,r)) Add(l)
          else if(!lt(l,r) && l._1!=r._1) Subtract(r)
          else if(l._2 != r._2) Update(l)
          else Same()
      }
      
      val l1 = List((1,1))
      val l2 = List((1,0))
      
      diff(l1,l2) should be equalTo(List(Update((1,1))))
    }
    
    "map to different types" in {
      case class I(i:Int)
      implicit val diffable = new Diffable[Int,I] {
        def lt = (l,r) => l < r
        
        def map(v:Int) = I(v)
        
        def diff(l:Int,r:Int) = if(lt(l,r)) Add(map(l))
          else if(!lt(l,r) && l!=r) Subtract(map(r))
          else if(l != r) Update(map(l))
          else Same()
      }
      
      val l1 = List(1,2)
      val l2 = List(1,0)
      
      diff[Int,I](l1,l2) should be equalTo(List(Subtract(I(0)),Add(I(2))))
    }
    
    "subobject update diff mapping" in new ParentChildCaseClasses {
      val p1 = Parent(1,List(Child(1,1),Child(2,1)))
      val p2 = Parent(1,List(           Child(2,2),Child(3,2)))
      
      diff(List(p1),List(p2))(diffableParent) should be equalTo(List(
          Update(ParentDiff(1,List(Add(Child(1,1)), Update(Child(2,1)), Subtract(Child(3,2)))))
      ))
    }
    
    "subobject add and subtract diff mapping" in new ParentChildCaseClasses {
      val p1 = Parent(1,List(Child(1,1)))
      val p2 = Parent(2,List(Child(2,2)))
      
      diff(List(p1),List(p2))(diffableParent) should be equalTo(List(
          Add(ParentDiff(1,List( Add(Child(1,1)) ))),
          Subtract(ParentDiff(2,List( Add(Child(2,2)) )))
      ))
    }
  }
}

trait ParentChildCaseClasses extends Scope {
  case class Child(id:Int,s:Int)
  case class Parent(id:Int,cs:List[Child])
  case class ParentDiff(id:Int,cs:List[Difference[Child]])
      
  implicit val diffableChild = new Diffable[Child,Child] {
    def lt = (l,r) => l.id < r.id
        
    def map(v:Child) = v
        
    def diff(l:Child,r:Child) = if(lt(l,r)) Add(map(l))
      else if(!lt(l,r) && l.id!=r.id) Subtract(map(r))
      else if(l != r) Update(map(l))
      else Same()
  }
      
  val diffableParent = new Diffable[Parent,ParentDiff] {
    def lt = (l,r) => l.id < r.id
    def map(v:Parent) = new ParentDiff(v.id,diff(v.cs,List()))
    def diff(l:Parent,r:Parent) : Difference[ParentDiff] = {
      if(lt(l,r)) Add(map(l))
      else if(!lt(l,r) && l.id!=r.id) Subtract(map(r))
      else if(l== r) Same()
      else {
        Update(ParentDiff(l.id,diff(l.cs,r.cs)))
      }
    }
  }
}