package keyfinder

final case class Stats(fieldIndices: List[Int],
                       present: Long,
                       checked: Long,
                       presentRate: Double,
                       otherStats: Map[String, Any] = Map.empty)