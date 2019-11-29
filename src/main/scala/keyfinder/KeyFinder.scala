package keyfinder

import keyfinder.analyzer.BloomFilterAnalyzer

final case class KeyFinder(candidateIndices: List[Int],
                           maxColumns: Int,
                           analyzerFunction: Int => ,
                insertionCapacity: Int,
                targetFalsePositiveRate: Double,
                charset: String = "UTF-8") {

  val analyzers: List[BloomFilterAnalyzer] =
    KeyFinder.buildColumnSubsets(candidateIndices.toSet, maxColumns)
      .map(indices =>
        BloomFilterAnalyzer(
          indices,
          insertionCapacity,
          targetFalsePositiveRate,
          charset
        )
      )

  def checkRow(row: List[String]): Unit = {
    analyzers.foreach(a => a.checkAndAdd(row))
  }

  def getStats(): List[Stats] = {
    analyzers.map( analyzer => analyzer.stats() )
  }

}

object KeyFinder {
  def buildColumnSubsets(set: Set[Int], maxSize: Int): List[List[Int]] = {
    (1 to maxSize).flatMap(size => {
      set
        .subsets(size)
        .map(_.toList.sorted)
        .toList
    }).toList
  }
}
