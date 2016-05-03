package patches.Node.Math

class Product extends Binary(_*_, "Product") {
  def value = "" // s"∏ ($left, $right) = $result"
}

object Product {
  def apply(): Product = new Product()
}