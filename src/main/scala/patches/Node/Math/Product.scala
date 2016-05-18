package patches.Node.Math

import patches.IO.DoubleMessage

class Product extends Binary(_ * _, "Product") {
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
    val p = v._2.value
    s"∏($l, $r = $p)"
  })
}

object Product {
  def apply(): Product = new Product()
}