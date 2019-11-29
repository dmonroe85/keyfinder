package keyfinder

import org.scalatest.{FreeSpec, Matchers}

class KeyFinderTest extends FreeSpec with Matchers {

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
