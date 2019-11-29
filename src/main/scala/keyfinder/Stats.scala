package keyfinder

final case class Stats(fieldIndices: List[Int],
                       alreadyPresent: Long,
                       checked: Long,
                       duplicationRate: Double)