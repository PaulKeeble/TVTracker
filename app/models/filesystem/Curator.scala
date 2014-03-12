package models.filesystem

import util.diff.Diff
import models.LibraryDiff._
import models._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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
  
  def curateDatabase : Future[Option[Library]] = Future {
    filesystemLibrary map { fsLib =>
      val dbLib = Library.load
    
      val difference = Diff.diffSingle(fsLib,dbLib)
      LibraryDatabase.updateLibrary(difference)
      Library.load
    }
  }
}