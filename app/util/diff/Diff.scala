package util.diff

object Diff {
  implicit val intDiffable = new Diffable[Int,Int] {
    def diff(l:Int,r:Int) = if(l<r) Add(l)
      else if(l>r) Subtract(r)
      else Same()
    
    def map(i:Int) = i
    
    def lt = (l:Int,r:Int) => l < r
  }
  
  def diff[T,U](left:List[T],right:List[T])(implicit diffable: Diffable[T,U]) = 
    diffable.diff(left, right)
  
  def diffSingle[T,U](left:T,right:T)(implicit diffable: Diffable[T,U]) : Difference[U] = 
    diffable.diff(left, right)

}