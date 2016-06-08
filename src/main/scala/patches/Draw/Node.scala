package patches.Draw

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.EventListener
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount
import org.scalajs.dom
import dom.MouseEvent
import monix.execution.Scheduler.Implicits.global


object Node {

  case class Props(
                    dispatch: AnyRef => Callback,
                    node: NodeModel
                  )

  class Backend($: BackendScope[Props, Unit]) extends OnUnmount with Draggable {

    def getXY() = {
      val p = $.props.runNow()
      (p.node.x, p.node.y)
    }

    def onMove(x: Double, y: Double) = {
      val p = $.props.runNow()
      p.dispatch(MoveNode(p.node, x.toInt, y.toInt)).runNow()
    }

    def handleMouseDown(e: ReactMouseEventH) = {
      val p = $.props.runNow()
      mouseDownChannel.onNext(
        new Mouse(e.clientX, e.clientY, e.eventType)
      )
      p.dispatch(ReorderNode(p.node))
    }

    def handleMouseMove(e: dom.MouseEvent) = {
      Callback {
        mouseMoveChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }
    }

    def handleClose(e: dom.MouseEvent) = {
      val p = $.props.runNow()
      p.dispatch(RemoveNode(p.node))
    }

    def init: Callback = {
      Callback {
        val p = $.props.runNow()
        p.node.node.value.foreach(str => {
          p.dispatch(UpdateNodeValue(p.node, str)).runNow()
        }
        )
      }
    }

    def render(p: Props) = {
      val node = p.node.node
      val x = p.node.x
      val y = p.node.y
      val v = p.node.v
      val uuid = p.node.uuid
      val dispatch = p.dispatch
      <.div(
        ^.className := "node draggable",
        ^.left := x + "px",
        ^.top := y + "px",
        <.div(
          ^.className := "handle title",
          ^.onMouseDown ==> handleMouseDown,
          <.div(
            ^.onMouseDown ==> handleClose,
            ^.position := "absolute",
            ^.right := "0",
            "✕"
          ),
          node.name
        ),
        <.div(
          ^.className := "body",
          v,
          node.inputs.zipWithIndex.map {
            case (input, index) =>
              val increment = 100.0 / (node.inputs.length + 1)
              patches.Draw.InputOutput(
                dispatch,
                Left(input),
                input.name,
                increment,
                index,
                uuid
              )
          },
          node.outputs.zipWithIndex.map {
            case (output, index) =>
              val increment = 100.0 / (node.outputs.length + 1)
              patches.Draw.InputOutput(
                dispatch,
                Right(output),
                output.name,
                increment,
                index,
                uuid
              )
          }
        )
      )
    }
  }

  val node = ReactComponentB[Props]("Node")
    .renderBackend[Backend]
    .configure(
      // Listen to window mousemove/mouseup events within the component
      EventListener[MouseEvent].install(
        "mouseup",
        _.backend.handleMouseMove,
        _ => dom.window
      ),
      EventListener[MouseEvent].install(
        "mousemove",
        _.backend.handleMouseMove,
        _ => dom.window
      )
    )
    .componentWillMount(_.backend.init)
    .build

  def apply(dispatch: AnyRef => Callback, n: NodeModel) =
    node.withKey(n.uuid.toString)(Props(dispatch, n))
}
