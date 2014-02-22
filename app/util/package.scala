import java.util.{Date => JDate}

package object util {
  def epoch = new JDate(0L)
  
  implicit class DateWrapper(val d:JDate) extends Ordered[DateWrapper] {
    def compare(that:DateWrapper) = d.compareTo(that.d)
  }
}