package models.filesystem

import java.io.{File => jFile}
import models.Show
import models.Season
import models.Library
import models.Episode

object FilesystemLibrarySource {
  type FE = (File,Extract)
  
  def createFrom(folders:List[Folder]) : Library = {
    val filtered= filterFiles(folders)
    createLibrary(filtered)
  }
  
  def filterFiles(folders:List[Folder]) = {
    val filter = new DefaultExtensionFilter
    folders.map( folder =>folder.filterChildren(filter) )
  }
  
  def createLibrary(folders:List[Folder]) : Library = {
    val shows = folders.map { folder =>
      val extractsBySeason = validExtractsBySeason(folder.children)
      
      val seasons = seasonObjects(extractsBySeason)
      
      new Show(0L,folder.name,folder.location,seasons)
    }
    
    new Library(shows)
  }
  
  //TODO: Make global logging
  def logBad(failedExtracts:Seq[File]) = {
    failedExtracts.foreach { f=>
      println(f.name)
    }
  }
  
  def validExtractsBySeason(files: Seq[File])= {
      val extracts = files.map { file => FilenameParser.parse(file.name)}
      
      val fileAndExtracts = files.zip(extracts)
      
      val validFilesAndExtracts = fileAndExtracts
        .filter(p=>p._2.isDefined)
        .map(pair => (pair._1,pair._2.get))
      
      val invalidExtractFiles = fileAndExtracts.filterNot(p=>p._2.isDefined).map(_._1)
      logBad(invalidExtractFiles)

      val extractsBySeason = validFilesAndExtracts.groupBy(_._2.season).toList
      extractsBySeason
  }

  def seasonObjects(bySeason: List[(Int, Seq[FE])]) = {
    bySeason.map { case (seasonNumber, extracts) =>

      val episodes = extracts.flatMap { case (file, extract) =>
        extract.episodes.map { e => new Episode(e, extract.title, file.name, file.modified) }
      }

      new Season(seasonNumber, episodes.toList)
    }
  }
}