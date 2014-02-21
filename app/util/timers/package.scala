package util

package object timers {
  def time[T](name: String)(f: => T): T = {
    val start = System.currentTimeMillis
    val res = f
    val end = System.currentTimeMillis
    println(name + ":" + (end - start) + "ms")
    res
  }
}