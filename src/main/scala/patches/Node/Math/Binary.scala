package patches.Node.Math

import monix.reactive.subjects.BehaviorSubject
import monix.execution.Scheduler.Implicits.global
import patches.IO._
import patches.Node.Node

abstract class Binary(
                       op: (Double, Double) => Double,
                       name: String,
                       default: Message = DoubleMessage(0)
                     ) extends Node(name) {

  val leftInput = Input[Message]("A", default)
  val rightInput = Input[Message]("B", default)
  val resultOutput = Output[Message]("C", default)

  protected val left = BehaviorSubject(default)
  private val cancelLeft = leftInput.in.subscribe(left)

  protected val right = BehaviorSubject(default)
  private val cancelRight = rightInput.in.subscribe(right)

  protected val result = BehaviorSubject(default)
  private val cancelResult = resultOutput.out.subscribe(result)

  def destroy = {
    cancelLeft.cancel()
    cancelRight.cancel()
    cancelResult.cancel()
    leftInput.destroy
    rightInput.destroy
    resultOutput.destroy
  }

    left.collect({
    case m: DoubleMessage => m
    case m: IntMessage => m.toDoubleMessage
  }).combineLatest(right.collect({
    case m: DoubleMessage => m
    case m: IntMessage => m.toDoubleMessage
  })).map({
    case (l, r) =>
      DoubleMessage(op(l.value, r.value))
  }).foreach(resultOutput.update(_))

  val inputs = List[Input[Message]](
    leftInput,
    rightInput
  )
  val outputs = List[Output[Message]](
    resultOutput
  )
}