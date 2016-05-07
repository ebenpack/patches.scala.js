package patches.Draw

import patches.Node.{Node => N}

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.EventListener
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount

import org.scalajs.dom, dom.MouseEvent

import monifu.reactive.OverflowStrategy
import monifu.reactive.channels.PublishChannel
import monifu.concurrent.Implicits.globalScheduler

object Node {

  case class Props(node: N)

  case class State(x: Int, y: Int)

  class Backend($: BackendScope[Props, State]) extends OnUnmount {

    class Position(val x: Double, val y: Double)

    class Mouse(
                 val clientX: Double,
                 val clientY: Double,
                 val eventType: String
               )

    val mouseDownChannel = PublishChannel[Mouse](OverflowStrategy.DropOld(100))
    val mouseMoveChannel = PublishChannel[Mouse](OverflowStrategy.DropOld(100))

    mouseDownChannel
      .flatMap(downEvent => {
        val s = $.state.runNow()
        val startMouseX = downEvent.clientX
        val startMouseY = downEvent.clientY
        val startPosX = s.x
        val startPosY = s.y
        mouseMoveChannel
          .takeWhile(
            _.eventType == "mousemove"
          )
          .map(m => {
            val s = $.state.runNow()
            val deltaX = m.clientX - startMouseX
            val deltaY = m.clientY - startMouseY
            new Mouse(startPosX + deltaX, startPosY + deltaY, m.eventType)
          })
      })
      .foreach(m =>
        $.setState(
          new State(m.clientX.toInt, m.clientY.toInt)
        ).runNow()
      )

    def handleMouseDown(e: ReactMouseEventH) =
      Callback {
        mouseDownChannel.pushNext(
          new Mouse(e.clientX, e.clientY, e.eventType)
        )
      }

    def handleMouseMove(e: dom.MouseEvent) = {
      Callback {
        mouseMoveChannel.pushNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }
    }

    def render(props: Props, state: State) = {
      <.div(
        ^.className := "draggable",
        ^.onMouseDown ==> handleMouseDown,
        ^.left := state.x + "px",
        ^.top := state.y + "px",
        ^.position := "absolute",
        props.node.name + " | " + props.node.value
      )
    }
  }

  val component = ReactComponentB[Props]("DrawNode")
    .initialState[State](State(0, 0))
    .renderBackend[Backend]
    .configure(
      // Listen to window mousemove/mouseup events within the component
      EventListener[dom.MouseEvent].install(
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
    .build

  def apply(P: Props) =
    component.withProps(P)
}
