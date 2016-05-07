package patches.Node.Math

class Product extends Binary(_*_, "Product") {
  def value = s"∏($leftVal, $rightVal = $resultVal)"
}

object Product {
  def apply(): Product = new Product()
}