package keyfinder.analyzer

import scala.collection.mutable

final case class SetAnalyzer(fieldIndices: List[Int],
                             delimiter: String = "|") extends KeyAnalyzer {

  private val set = mutable.Set.empty[String]

  override def contains(entry: String): Boolean = set.contains(entry)

  override def put(entry: String): Unit = set.add(entry)

}
