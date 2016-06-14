package patches.Draw

import patches.Canvas.Canvas
import patches.IO.{Input, Output}
import patches.Node.{Node => N}
import patches.Patch.{Patch => P}

abstract class Model

case class ControlsModel(
                          controls: Map[String, Map[String, ()=>N]]
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
                      ) extends Model {

}

case class AddNode(n: N)

case class RemoveNode(n: NodeModel)

case class ReorderNode(n: NodeModel)

/*
  Node
 */
case class NodeModel(
                      node: N,
                      name: String,
                      var x: Int,
                      var y: Int,
                      var v: String
                    ) extends Model

case class MoveNode(node: NodeModel, x: Int, y: Int)

case class UpdateNodeValue(node: NodeModel, v: String)

/*
  Patch
 */
case class PatchModel(
                       p: P,
                       var in: Option[Input],
                       var out: Option[Output],
                       var x1: Int,
                       var y1: Int,
                       var x2: Int,
                       var y2: Int
                     ) extends Model

case class AddPatch (p: PatchModel)

case class AddTempPatchFOOBAR(
                               io: Either[Input, Output]
                             )

case class AddTempPatch(
                         io: Either[Input, Output],
                         x: Int,
                         y: Int
                       )

case class RemoveTempPatch()

case class MoveTempPatch(x: Int, y: Int)

case class MovePatch(patch: PatchModel, x: Int, y: Int)
