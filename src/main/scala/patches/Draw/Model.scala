package patches.Draw

import java.util.UUID

import patches.IO.{Input, Message, Output}
import patches.Node.{Node => N}
import patches.Patch.{Patch => P}

abstract class Model

/*
  Controls
 */
case class ControlsModel(
                          controls: Map[String, Map[String, () => N]]
                        ) extends Model

case class PatchesModel(
                       dragging: Option[PatchModel],
                       patches: List[PatchModel]
                       )

/*
  Canvas
 */
case class CanvasModel(
                        nodes: List[NodeModel],
                        patches: PatchesModel,
                        controls: ControlsModel
                      ) extends Model

case class AddNode(n: N)

case class RemoveNode(n: NodeModel)

case class ReorderNode(n: NodeModel)

/*
  Node
 */
case class NodeModel(
                      node: N,
                      var x: Int,
                      var y: Int,
                      var v: String,
                      uuid: UUID
                    ) extends Model

case class MoveNode(node: NodeModel, x: Int, y: Int)

case class UpdateNodeValue(node: NodeModel, v: String)

/*
  Patch
 */
case class PatchModel(
                       p: P,
                       var in: Option[Input[Message]],
                       var out: Option[Output[Message]],
                       var x1: Int,
                       var y1: Int,
                       var x2: Int,
                       var y2: Int,
                       uuid: UUID
                     ) extends Model

case class AddPatch (p: PatchModel)

case class AddTempPatchFOOBAR(
                     io: Either[Input[Message], Output[Message]]
                   )

case class AddTempPatch(io: Either[Input[Message], Output[Message]], x: Int, y: Int)

case class RemoveTempPatch()

case class MoveTempPatch(x: Int, y: Int)

case class MovePatch(patch: PatchModel, x: Int, y: Int)