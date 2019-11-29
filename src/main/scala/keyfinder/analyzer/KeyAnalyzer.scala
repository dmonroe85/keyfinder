package keyfinder.analyzer

import keyfinder.Stats

trait KeyAnalyzer {

  val fieldIndices: List[Int]
  val delimiter: String

  def contains(entry: String): Boolean
  def put(entry: String): Unit
  def additionalStats(): Map[String, Any] = Map.empty

  var checked: Long = 0
  var alreadyPresent: Long = 0

  def createStringEntry(data: Vector[String]): String =
    fieldIndices.map(i => data(i)).mkString(delimiter)

  def checkAndAdd(data: Vector[String]): Unit = {
    checked += 1

    val entry = createStringEntry(data)
    if (contains(entry))
      alreadyPresent += 1

    put(entry)
  }

  def stats(): Stats =
    Stats(
      fieldIndices,
      alreadyPresent,
      checked,
      alreadyPresent.toDouble / checked.toDouble
    )

}
