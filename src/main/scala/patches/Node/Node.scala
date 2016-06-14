package patches.Node

import patches.IO.{Input, Output}
import jsactor.{JsActor, JsActorRef}

trait Node extends JsActor {
  val name: String
  val inputs: List[Input]
  val outputs: List[Output]
  var x: Int
  var y: Int

  def value: String
}