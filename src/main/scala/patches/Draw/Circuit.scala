package patches.Draw

import java.util.UUID

import diode.{ActionHandler, Circuit}
import diode.react.ReactConnector
import patches.Node.Math.{Product, Sum}
import patches.Node.{Node => N}

object AppCircuit extends Circuit[CanvasModel] with ReactConnector[CanvasModel] {

  def getControls() = ControlsModel(
    Map(
      "Math" -> Map[String, () => N](
        "Product" -> (() => Product()),
        "Sum" -> (() => Sum())
      )
    )
  )

  override def initialModel =
    CanvasModel(
      List[NodeModel](),
      getControls()
    )

  val canvasHandler = new ActionHandler(
    zoomRW(_.nodes)((m, v) => m.copy(nodes = v))
  ) {
    override def handle = {
      case AddNode(n) => updated(value :+ NodeModel(n, 0, 0, "", UUID.randomUUID()))
      case RemoveNode(n) => updated(value.filter(_ != n))
      case ReorderNode(n) => updated(value.span(_ != n) match {
        case (as, h :: bs) => as ++ bs :+ h
        case _ => value
      })
      case MoveNode(n, x, y) => {
        // TODO: This feels ugly
        val i = value.indexWhere(_ == n)
        updated(value.updated(i, value(i).copy(x = x, y = y)))
      }
      case UpdateNodeValue(n, str) => {
        // TODO: This feels ugly
        val i = value.indexWhere(_ == n)
        updated(value.updated(i, value(i).copy(v = str)))
      }
    }
  }

  val actionHandler = composeHandlers(canvasHandler)
}