package org.crockeo.genericplatformer

import java.io._

object IO {
  // Checking if a file exists
  def exists(path: String): Boolean = (new java.io.File(path)).exists
  
  // Writing a file
  def writeFile(path: String, content: String): Unit = {
    val bw = new BufferedWriter(new FileWriter(new File(path)))
    
    bw.write(content)
    
    bw.close
  }
  
  // Reading a file
  def readFile(path: String): List[String] = {
    val br = new BufferedReader(new FileReader(new File(path)))
    var lines = List.empty[String]
    var line = br.readLine
    
    while (line != null) {
      lines = lines :+ line
      line = br.readLine
    }
    
    br.close
    
    lines
  }
  
  // Reading a file and returning a None if it doesn't exist
  def readFileCareful(path: String): Option[List[String]] =
    if (exists(path)) Some(readFile(path))
    else              None
}