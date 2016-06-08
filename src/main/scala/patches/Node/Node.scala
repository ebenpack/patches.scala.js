package patches.Node

import monix.reactive.Observable
import patches.IO.{Input, Message, Output}

abstract class Node(val name: String) {
  val inputs: List[Input[Message]]
  val outputs: List[Output[Message]]

  def value: Observable[String]
  def destroy: Unit
}