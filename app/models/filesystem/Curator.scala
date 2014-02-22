package models.filesystem

import models.Configuration
import models.Library

object Curator {
  def filesystemLibrary:Option[Library] = {
    Configuration.directory match {
      case Some(dir) => {
        val folders = FileSystemScanner.scanFrom(dir.toFile)
        Option(FilesystemLibrarySource.createFrom(folders))
      }
      case None => None
    }
  }
}