package patches.Node.Math

import monifu.concurrent.Implicits.globalScheduler
import patches.IO._
import patches.Node.Node

abstract class Binary(
                       op: (Double, Double) => Double,
                       name: String,
                       default: DoubleMessage = DoubleMessage(0)
                     ) extends Node(name) {

  val leftInput = Input[Message]("A", default)
  val rightInput = Input[Message]("B", default)
  val resultOutput = Output[Message]("C", default)

  protected var leftVal, rightVal, resultVal: Double = default.value
  protected val left = leftInput.in
    .collect({
      case m: DoubleMessage => m
      case m: IntMessage => m.toDoubleMessage
    })

  protected val right = rightInput.in
    .collect({
      case m: DoubleMessage => m
      case m: IntMessage => m.toDoubleMessage
    })

  protected val result = left.combineLatest(right)
    .map({
      case (l, r) =>
        leftVal = l.value
        rightVal = r.value
        resultVal = op(l.value, r.value)
        DoubleMessage(resultVal)
    })
  result.foreach(resultOutput.update(_))

  val inputs = List[Input[Message]](
    leftInput,
    rightInput
  )
  val outputs = List[Output[Message]](
    resultOutput
  )
}