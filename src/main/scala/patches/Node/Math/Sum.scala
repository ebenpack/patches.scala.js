package patches.Node.Math

class Sum extends Binary(_+_, "Sum") {
  def value = "" // s"∑ ($left, $right) = $result"
}

object Sum {
  def apply(): Sum = new Sum()
}