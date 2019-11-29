package keyfinder.analyzer

import keyfinder.Stats

trait KeyAnalyzer {

  val fieldIndices: List[Int]
  val delimiter: String

  def contains(entry: String): Boolean
  def put(entry: String): Unit

  var checked: Long = 0
  var present: Long = 0

  def checkAndAdd(data: Vector[Int]): Unit = {
    checked += 1

    val entry = fieldIndices.map(i => data(i)).mkString(delimiter)

    if (contains(entry))
      present += 1

    put(entry)
  }

  def stats(): Stats =
    Stats(
      fieldIndices,
      present,
      checked,
      present.toDouble / checked.toDouble
    )

}
