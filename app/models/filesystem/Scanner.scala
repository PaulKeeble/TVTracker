package models.filesystem

import java.util.Date
import java.io.{File => JFile}

case class Folder(name:String,location:String,children:Seq[File]) {
  def filterChildren(f:Filter) : Folder = {
    val filteredChildren = children.filter(node => f(node.name))
    copy(children = filteredChildren)
  }
}

case class File(name:String,modified:Date)

object FileSystemScanner {
  def scanFrom(base:JFile) : List[Folder] = {
    val shows = base.listFiles()
    val showObjs = shows.map { showFolder =>  //par
      val episodeFiles = showFolder.listFiles()
      val episodes = episodeFiles.map { episodeFile =>  //par
        new File(episodeFile.getName,new Date(episodeFile.lastModified))
      }.seq
      
      new Folder(showFolder.getName,showFolder.getPath, episodes)
    }
    showObjs.toList
  }
  
  /*
   * TODO add recursive any file structure approach
   * def recursiveFrom(file:JFile) : List[Node] = {
      if(file.isFile)
        List(new File(file.getName,new Date(file.lastModified)))
      else
        for(l <- Option(file.listFiles);
            f <- l) {
          val children = recursiveFrom(f)
          new Folder(file.getName,file.getPath,children)
          if(f.isDirectory)
            f.delete
        }
   }*/
}