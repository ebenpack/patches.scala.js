package patches.Draw

import java.util.UUID

import patches.Node.{Node => N}

abstract class Model

case class ControlsModel(controls: Map[String, Map[String, ()=>N]]) extends Model

case class CanvasModel(nodes: List[NodeModel], controls: ControlsModel) extends Model
case class AddNode(n: N)
case class RemoveNode(n: NodeModel)
case class ReorderNode(n: NodeModel)

case class NodeModel(node: N, x: Int, y: Int, v: String, uuid: UUID) extends Model
case class MoveNode(node: NodeModel, x: Int, y: Int)
case class UpdateNodeValue(node: NodeModel, v: String)