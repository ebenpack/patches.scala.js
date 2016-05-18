package patches.Node.Math

import patches.IO.DoubleMessage

class Sum extends Binary(_ + _, "Sum") {
  val value = left.collect({
    case m: DoubleMessage => m
  }).combineLatest(
    right.collect({
      case m: DoubleMessage => m
    })
  ).combineLatest(result.collect({
    case m: DoubleMessage => m
  })).map(v => {
    val l = v._1._1.value
    val r = v._1._2.value
    val s = v._2.value
    s"∑($l, $r = $s)"
  })
}

object Sum {
  def apply(): Sum = new Sum()
}