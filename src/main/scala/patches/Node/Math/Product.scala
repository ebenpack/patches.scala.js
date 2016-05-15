package patches.Node.Math

import monix.execution.Scheduler.Implicits.global
import patches.IO.DoubleMessage

class Product extends Binary(_ * _, "Product") {
  val value = hotLeft.collect({
    case m: DoubleMessage => m
  }).combineLatest(
    hotRight.collect({
    case m: DoubleMessage => m
  })
  ).combineLatest(hotResult.collect({
    case m: DoubleMessage => m
  })).map(v => {
    val l = v._1._1.value
    val r = v._1._2.value
    val p = v._2.value
    s"∏($l, $r = $p)"
  })
}

object Product {
  def apply(): Product = new Product()
}