package patches.Node

import patches.Actor.Props
import patches.IO.{Input, Output}


class Sum extends Node {
  val left = Props(Input[Double](0))
  val right = Props(Input[Double](0))
  val out = Output[Double](0)
  val inputs = List[Input[Double]](left, right)
  val outputs = List[Output[Double]](out)

  def receive = {
    case _ => ;
  }

  def update =
    out ! inputs.foldRight(0.0)(_.value+_)

  left.become({
    case _: Int =>
      leftVal = a
  })
  right.become({
    case a: Int =>
      rightVal = a
  })


}
