package patches.Node.Math

import patches.IO._
import patches.Node.Node

class Add extends Node("Add") {
  val leftInput = DoubleInput("A")
  val rightInput = DoubleInput("B")
  val sumOutput = DoubleOutput("∑")

  var left = 0.0
  var right = 0.0
  var sum = 0.0

  def value = s"∑ ($left, $right) = $sum"

  def updateLeft(l: Double) = {
    left = l
    updateSum()
  }

  def updateRight(r: Double) = {
    right = r
    updateSum()
  }

  def updateSum() = {
    sum = left + right
    sumOutput.emit("update", DoubleMessage("update", sum))
    this.emit("update", {})
  }

  leftInput.on("update", i => i match {
    case in: DoubleMessage => updateLeft(in.value)
    case in: IntMessage => updateLeft(in.value)
    case _ => ;
  })
  rightInput.on("update", i => i match {
    case in: DoubleMessage => updateRight(in.value)
    case in: IntMessage => updateRight(in.value)
    case _ => ;
  })

  val inputs = List[Input](
    leftInput,
    rightInput
  )
  val outputs = List[Output](
    sumOutput
  )
}

object Add {
  def apply(): Add = new Add()
}