package keyfinder

import keyfinder.analyzer.{BloomFilterAnalyzer, SetAnalyzer}
import org.scalatest.{FreeSpec, Matchers}

import scala.util.Random

class KeyFinderTest extends FreeSpec with Matchers {

  var index: Int = 0

  def getRow(): Vector[String] = {
    index += 1

    val constant = "asdf"
    val mod2 = index % 2
    val randInt = Random.nextInt(100)
    val key1 = if (mod2 == 0) index else index - 1
    val key2 = if (mod2 == 0) index - 1 else index

    Vector(constant, key1, randInt, key2).map(_.toString)
  }

  "KeyFinder" - {
    "should be able to find the primary key from a data stream" in {
      val numberOfRows = 1000
      val keyColumnCandidates = (0 until getRow().length).toSet
      val maxKeySetSize = 3

      val keyFinderSet = KeyFinder(keyColumnCandidates, maxKeySetSize, (fieldIndices: List[Int]) => SetAnalyzer(fieldIndices))

      (0 until numberOfRows).foreach(i => keyFinderSet.analyzeRow(getRow()) )
      keyFinderSet.getStats().minBy(_.presentRate).fieldIndices shouldBe List(1, 3)
    }
  }

  "buildColumnSubsets" - {
    "should build all subsets of columns up to the specified max size" in {
      val inputSet = Set(1, 2, 3, 4)
      val maxSize = 2
      val expectedSubsets = List(
        List(1), List(2), List(3), List(4),
        List(1,2), List(1,3), List(1,4), List(2,3), List(2,4), List(3,4)
      )

      KeyFinder.buildColumnSubsets(inputSet, maxSize) shouldBe expectedSubsets
    }
  }
}
