package patches.Node

import patches.IO.{Input, Output}
import patches.util.EventEmitter

abstract class Node(val name: String) extends EventEmitter[Unit] {
  val inputs: List[Input]
  val outputs: List[Output]

  def value: String

  def connect(i: Input, o: Output, topic: String) = (i, o) match {
    case (in, out) if in.canConnect(out) =>
      out.on(topic, m => in.receive(m))
    case _ => ;
  }
  // TODO: Disconnect
}