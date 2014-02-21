package models.filesystem

import org.specs2.mutable
import java.io.{File => jioFile}
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.execute.Result
import org.specs2.execute.AsResult
import java.util.Date

import util.timers._

@RunWith(classOf[JUnitRunner])
class FilesystemScannerSpec extends mutable.Specification {
  sequential
  
  "The FilesystemScanner" should {
    "find folder" in new temporaryFiles {
      val createdFolder = root.folder("abc")
      val found = FileSystemScanner.scanFrom(base)

      found must contain(Folder("abc",createdFolder.path,List()))
    }
    
    "find file" in new temporaryFiles {
      root.file("abc")
      val found = FileSystemScanner.scanFrom(base)
      
      found.map(_.name) must contain("abc")
    }.pendingUntilFixed("Root files not currently supported")
    
    "find multiple folders" in new temporaryFiles {
      val f1 = root.folder("f1")
      val f2 = root.folder("f2")
      val found = FileSystemScanner.scanFrom(base)

      found must contain(allOf( Folder("f1",f1.path,List()), Folder("f2",f2.path,List()) ))
    }
    
    "finds files within folder" in new temporaryFiles {
       val f1 = root.folder("f1")
       f1.file("a")
       f1.file("b")
       val found = FileSystemScanner.scanFrom(base)
       
       found(0).children.map(_.name) must contain(allOf("a","b"))
    }
  }
}

trait temporaryFiles extends mutable.After with FileCreator{
  val base = new jioFile("target/scannertest")
  def after = cleanup()
}