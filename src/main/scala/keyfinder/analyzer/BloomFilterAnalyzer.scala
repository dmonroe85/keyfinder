package keyfinder.analyzer

import java.nio.charset.Charset

import com.google.common.hash.{BloomFilter, Funnels}
import keyfinder.Stats

final case class BloomFilterAnalyzer(fieldIndices: List[Int],
                                     insertionCapacity: Int,
                                     targetFalsePositiveRate: Double,
                                     charset: String = "UTF-8",
                                     delimiter: String = "|") extends KeyAnalyzer {

  private val bf = BloomFilter.create[String](
    Funnels.stringFunnel(Charset.forName(charset)),
    insertionCapacity,
    targetFalsePositiveRate
  )

  override def contains(entry: String): Boolean = bf.mightContain(entry)

  override def put(entry: String): Unit = bf.put(entry)

}
