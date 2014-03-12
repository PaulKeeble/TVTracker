package util.diff

import scala.annotation.tailrec

sealed trait Difference[T]
case class Add[T](val value:T) extends Difference[T]
case class Subtract[T](val value:T) extends Difference[T]
case class Update[T](val value:T) extends Difference[T]
case class Same[T]() extends Difference[T]

trait Diffable[T,U] {
  def diff(l:T,r:T) : Difference[U]
  
  def map(t:T) : U
  
  def lt: (T,T) => Boolean
  
  def gt = (l:T,r:T) => !lt(l,r) && l!=r
  
  def diff(lhs:List[T],rhs:List[T]) : List[Difference[U]] = {
    val lhsSorted = lhs.sortWith(lt)
    val rhsSorted = rhs.sortWith(lt)
    rdiff(lhsSorted,rhsSorted,List())
  }
  
  def diff[U,V](left:List[U],right:List[U])(implicit diffable: Diffable[U,V]) : List[Difference[V]] = Diff.diff(left,right)
  
  @tailrec
  private def rdiff(lhs:List[T],rhs:List[T],acc:List[Difference[U]]) : List[Difference[U]] = {
    (lhs,rhs) match {
      case (Nil,Nil) => acc.reverse
      case (l::lrest,Nil) => rdiff(lrest,rhs,Add(map(l))::acc)
      case (Nil,r::rrest) => rdiff(lhs,rrest,Subtract(map(r))::acc)
      case (l::lrest,r::rrest) => {
        diff(l,r) match {
          case Same() => rdiff(lrest,rrest,acc)
          case dif@Add(a) => rdiff(lrest,r::rrest,dif::acc)
          case dif@Subtract(a) => rdiff(l::lrest,rrest,dif::acc)
          case dif@Update(a) => rdiff(lrest,rrest,dif::acc)
        }
      }
    }
  }
}