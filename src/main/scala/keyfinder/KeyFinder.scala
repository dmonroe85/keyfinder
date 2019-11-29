package keyfinder

import keyfinder.analyzer.{BloomFilterAnalyzer, KeyAnalyzer}

final case class KeyFinder(candidateIndices: Set[Int],
                           maxColumns: Int,
                           subsetAnalyzerFunction: List[Int] => KeyAnalyzer) {

  val analyzers: List[KeyAnalyzer] =
    KeyFinder.buildColumnSubsets(candidateIndices, maxColumns)
      .map(subsetAnalyzerFunction)

  def analyzeRow(row: Vector[String]): Unit =
    analyzers.foreach(a => a.checkAndAdd(row))

  def getStats(): List[Stats] =
    analyzers.map( analyzer => analyzer.stats() )

}

object KeyFinder {
  def buildColumnSubsets(set: Set[Int], maxSize: Int): List[List[Int]] =
    (1 to maxSize).flatMap(size => {
      set
        .subsets(size)
        .map(_.toList.sorted)
        .toList
    }).toList
}
