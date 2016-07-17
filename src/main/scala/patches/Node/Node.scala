package patches.Node

import patches.Actor.Actor
import patches.IO.{Input, Output}

abstract class Node extends Actor {
  val inputs: List[Input]
  val outputs: List[Output]
}
