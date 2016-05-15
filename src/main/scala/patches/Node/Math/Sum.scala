package patches.Node.Math

import monix.execution.Scheduler.Implicits.global
import patches.IO.{DoubleMessage, IntMessage}

class Sum extends Binary(_ + _, "Sum") {
  val value = hotLeft.collect({
    case m: DoubleMessage => m
    case m: IntMessage => m.toDoubleMessage
  }).combineLatest(
    hotRight.collect({
      case m: DoubleMessage => m
      case m: IntMessage => m.toDoubleMessage
    })
  ).combineLatest(hotResult.collect({
    case m: DoubleMessage => m
    case m: IntMessage => m.toDoubleMessage
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