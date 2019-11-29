package keyfinder.analyzer

import org.scalatest.{FreeSpec, Matchers}

class SetAnalyzerTest extends FreeSpec with Matchers {
  val rows = List(
    Vector("0", "1", "2"),
    Vector("2", "2", "1"),
    Vector("0", "3", "1"),
  )

  val analyzer = SetAnalyzer(List(0, 1))

  rows.foreach(row => analyzer.checkAndAdd(row))

  "SetAnalyzer" - {
    "should detect a previously seen candidate" in {
      val entry = analyzer.createStringEntry(Vector("0", "1", "1"))
      analyzer.contains(entry) shouldBe true
    }

    "should detect a new key candidate" in {
      val entry = analyzer.createStringEntry(Vector("2", "1", "1"))
      analyzer.contains(entry) shouldBe false
    }
  }

}
