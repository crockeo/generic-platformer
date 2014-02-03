package org.crockeo.genericplatformer

trait Parser[T] {
  // The default value
  protected val default: T
  
  // Parsing a single line
  protected def parseLine(last: T, line: String): T
  
  // Parsing every single line in a file
  private def parseLines(lines: Iterable[String]): T = lines.foldLeft(default)(parseLine)
  
  // Loading the file to pass to parseLines
  def parse(path: String): T =
    IO.readFileCareful(path) match {
      case Some(lines) => parseLines(lines)
      case None     => {
        IO.writeFile(path, default.toString)
        parse(path)
      }
    }
  
  // Synonym for the parse function
  def apply(path: String): T = parse(path)
}