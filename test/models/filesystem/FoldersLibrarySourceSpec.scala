package models.filesystem

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import java.util.Date
import models.Library
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FilesystemLibrarySourceSpec extends Specification with LibraryBuilder { 
  val extensionFilter = new DefaultExtensionFilter

  "The Folders library source" should {
    "make an empty show with no files" in {
      val folders = List(Folder("show1", "c:\\show1", List()))
      
      val library = FilesystemLibrarySource.createFrom(folders)
      
      library must beEqualTo(Library(show("show1","c:\\show1")(List()) ))
    }
    
    "make a season with 1 episode" in {
      val modifiedDate = new Date
      val fileName = "show1 - S01E01 - title.mkv"
      val file1 = File(fileName,modifiedDate)
      val folders = List(Folder("show1", "c:\\show1", List(file1)))
      
      val library = FilesystemLibrarySource.createFrom(folders)
      
      val expected =Library(
        show("show1","c:\\show1") {
          season(1) { 
            episode(1,"title",fileName,modifiedDate) 
          }
        }
      )
      library must beEqualTo(expected)
    }
    
    "make a season with multiple episodes" in {
      val modifiedDate = new Date
      val fileName1 = "show1 - S01E01 - title.mkv"
      val file1 = File(fileName1,modifiedDate)
      val fileName2 = "show1 - S01E02 - title2.mkv"
      val file2 = File(fileName2,modifiedDate)
      val folders = List(Folder("show1", "c:\\show1", List(file1,file2)))
      
      val library = FilesystemLibrarySource.createFrom(folders)
      
      val expected =Library(
        show("show1","c:\\show1") {
          season(1) { 
            episode(1,"title",fileName1,modifiedDate) ++
            episode(2,"title2",fileName2,modifiedDate)
          }
        }
      )
      library must beEqualTo(expected)
    }
    
    "make multiple seasons" in {
      val modifiedDate = new Date
      val fileName1 = "show1 - S01E01 - title.mkv"
      val file1 = File(fileName1,modifiedDate)
      val fileName2 = "show2 - S01E01 - title.mkv"
      val file2 = File(fileName2,modifiedDate)
      val folders = List(
          Folder("show1", "c:\\show1", List(file1)),
          Folder("show2", "c:\\show2", List(file2))
      )
      
      val library = FilesystemLibrarySource.createFrom(folders)
      
      val expected =Library(
        show("show1","c:\\show1") {
          season(1) { 
            episode(1,"title",fileName1,modifiedDate)
          }
        } ++
        show("show2","c:\\show2") {
          season(1) {
            episode(1,"title",fileName2,modifiedDate)
          }
        }
      )
      library must beEqualTo(expected)
    }
    
    "filter out non movie files" in {
      val file = File("show - S01E01 - title.nfo",new Date)
      val folders = List(
          Folder("show","c:\\show",List(file))
          )
          
      val library = FilesystemLibrarySource.createFrom(folders)
      
      val expected =Library(
        show("show","c:\\show")(List())
      )
      
      library must beEqualTo(expected)
    }
  }
}