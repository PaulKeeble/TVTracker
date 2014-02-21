package models.filesystem

import models.Configuration

class Curator {
  def filesystemLibrary:Option[Library] = {
    Configuration.directory match {
      case Some(dir) => {
        val folders = FileSystemScanner.scanFrom(dir.toFile)
        Option(FilesystemLibrarySource.createLibrary(folders))
      }
      case None => None
    }
  }
}