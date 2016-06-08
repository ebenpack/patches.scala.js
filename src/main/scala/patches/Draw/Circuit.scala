package patches.Draw

import java.util.UUID

import diode.{ActionHandler, Circuit}
import diode.react.ReactConnector
import patches.Node.Math.{Product, Sum}
import patches.Node.Generators.Interval
import patches.Node.{Node => N}
import patches.Patch.{Patch => P}

object AppCircuit extends Circuit[CanvasModel] with ReactConnector[CanvasModel] {

  def getControls() = ControlsModel(
    Map(
      "Math" -> Map[String, () => N](
        "Product" -> (() => Product()),
        "Sum" -> (() => Sum())
      ),
      "Generators" -> Map[String, () => N](
        "Interval" -> (() => Interval())
      )
    )
  )

  override def initialModel =
    CanvasModel(
      List[NodeModel](),
      PatchesModel(
        None,
        List[PatchModel]()
      ),
      getControls()
    )

  val nodeHandler = new ActionHandler(
    zoomRW(_.nodes)((m, v) => m.copy(nodes = v))
  ) {
    override def handle = {
      case AddNode(n) => updated(value :+ NodeModel(n, 0, 0, "", UUID.randomUUID()))
      case RemoveNode(n) =>
        n.node.destroy
        updated(value.filter(_ != n))
      case ReorderNode(n) => updated(value.span(_ != n) match {
        case (as, h :: bs) => as ++ bs :+ h
        case _ => value
      })
      case MoveNode(n, x, y) =>
        // TODO: This feels ugly
        value.find(_ == n).foreach(a => {
          a.x = x
          a.y = y
        })
        // TODO: This feels ugly too
        // (but otherwise there are issues with the stream)
        updated(value)
      case UpdateNodeValue(n, str) =>
        // TODO: This feels ugly
        value.find(_ == n).foreach(_.v = str)
        // TODO: This feels ugly too
        // (but otherwise there are issues with the stream)
        updated(value)
    }
  }

  val patchHandler = new ActionHandler(
    zoomRW(_.patches)((m, v) => m.copy(patches = v))
      .zoomRW(_.patches)((m, v) => m.copy(patches = v))
  ) {
    override def handle = {
      case MovePatch(p, x, y) =>
        value.find(_ == p).foreach(a => {
          a.x2 = x
          a.y2 = y
        })
        updated(value)
    }

  }


  val FOOBAR = new ActionHandler(
    zoomRW(_.patches)((m, v) => m.copy(patches = v))
  ) {
    override def handle = {
      case AddTempPatchFOOBAR(b) =>
        var patches = value.patches
        value.dragging.foreach(a => {
          a match {
            case PatchModel(_, Some(l), None, _, _, _, _, _) =>
              b match {
                case Right(r) =>
                  a.out = Some(r)
                  a.p.connect(l, r)
                  patches = a :: patches
                case _ => ;
              }
            case PatchModel(_, None, Some(r), _, _, _, _, _) =>
              b match {
                case Left(l) =>
                  a.in = Some(l)
                  a.p.connect(l, r)
                  patches = a :: patches
                case _ => ;
              }
          }
        })
        updated(PatchesModel(None, patches))
    }
  }


  val tempPatchHandler = new ActionHandler(
    zoomRW(_.patches)((m, v) => m.copy(patches = v))
      .zoomRW(_.dragging)((m, v) => m.copy(dragging = v))
  ) {
    override def handle = {
      case AddTempPatch(a, x, y) => a match {
        case Left(l) => updated(
          Some(
            PatchModel(P(), Some(l), None, x, y, x, y, UUID.randomUUID())
          )
        )
        case Right(r) => updated(
          Some(
            PatchModel(P(), None, Some(r), x, y, x, y, UUID.randomUUID())
          )
        )
      }
      case RemoveTempPatch() =>
        updated(None)
      case MoveTempPatch(x, y) =>
        value.foreach(p => {
          p.x2 = x
          p.y2 = y
        })
        updated(value)
    }
  }

  val actionHandler = composeHandlers(
    nodeHandler,
    FOOBAR,
    tempPatchHandler,
    patchHandler

  )
}