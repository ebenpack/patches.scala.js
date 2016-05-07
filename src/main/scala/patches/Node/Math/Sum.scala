package patches.Node.Math

class Sum extends Binary(_+_, "Sum") {
  def value = s"∑($leftVal, $rightVal = $resultVal)"
}

object Sum {
  def apply(): Sum = new Sum()
}