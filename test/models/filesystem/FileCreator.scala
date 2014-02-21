package models.filesystem

import java.io.{File=> JFile}

/**
 * Used for creating folders and files in tests for testing th
 */
trait FileCreator {
  trait Folder {
    val folder: JFile
    
    def file(fName:String) : Folder = {
      new JFile(folder,fName).createNewFile()
      this
    }
    
    def folder(fName:String) : Folder = {
      new FolderCreator(folder,fName)
    }
    
    def path = folder.getPath
  }
  
  private class FolderCreator(parent:JFile,name:String) extends Folder{
    val folder = new JFile(parent,name)
    folder.mkdir()
  }
  
  private class BaseFolder extends Folder {
    val folder = base
    if(!folder.exists)
      folder.mkdir
  }
  
  lazy val root : Folder = new BaseFolder()
  
  val base: JFile
  
  def cleanup() {
    def cleanup(file:JFile) {
      if(file.isFile)
        file.delete
      else
        
        for(l <- Option(file.listFiles);
            f <- l) {
          cleanup(f)
          if(f.isDirectory)
            f.delete
        }
      
      file.delete
    }
    
    cleanup(base)
  }
}