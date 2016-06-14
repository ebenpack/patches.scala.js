package patches.IO.Messages

import patches.Node.Node

abstract class Message

case class AnyMessage(value: Any) extends Message

case class DoubleMessage(value: Double) extends Message

case class Move(x: Int, y: Int)

abstract class SystemMessage extends Message

case class AddNode(n: Node) extends SystemMessage

case class MoveNode(n: Node, x: Int, y: Int) extends SystemMessage

case class RemoveNode(n: Node) extends SystemMessage