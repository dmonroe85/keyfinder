package keyfinder

object MainTest extends App {

  var index: Int = 0

  def getRow(): List[String] = {
    val constant = "asdf"
    val mod10 = index % 10
    val mod100 = index % 100
    val mod1000 = index % 1000

    val key1 = if (mod100 == 0) index else index - 1
    val key2 = if (mod100 == 0) index - 1 else index

    index += 1

    List(
      mod100,
      constant,
      key1,
      mod10,
      key2,
      mod1000,
    ).map(_.toString)
  }

  val sampleRow = getRow()

  val numberOfRows = 1000000

  val keyColumnCandidates = (0 to 5).toList
  val maxKeySetSize = 3

  val keyFinder = KeyFinder(
    keyColumnCandidates,
    maxKeySetSize,
    numberOfRows*10,
    targetFalsePositiveRate = 0.001
  )

  (0 until numberOfRows).foreach(i =>{
    val newRow = getRow()
    keyFinder.checkRow(newRow)

    if ((i % (numberOfRows/10)) == 0) {
      println(keyFinder.getStats().minBy(_.presentRate))
      println()
    }
  })

}
