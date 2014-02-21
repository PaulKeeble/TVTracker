package models.filesystem

import scala.collection.immutable

trait Filter extends (String => Boolean)

class ExtensionFilter(extensions: Seq[String]) extends Filter {
  private val efficientExtensions = immutable.HashSet(extensions: _*)

  def apply(filename: String): Boolean = {
    val startExtension = filename.lastIndexOf(".")
    val extension = filename.substring(startExtension + 1, filename.length)

    efficientExtensions.contains(extension)
  }
}

class DefaultExtensionFilter extends ExtensionFilter(Seq("avi", "mkv", "mp4", "m4v", "rmvb"))