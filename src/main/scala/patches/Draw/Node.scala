package patches.Draw

import patches.Node.{Node => N}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.EventListener
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount
import org.scalajs.dom
import dom.MouseEvent
import monix.reactive.MulticastStrategy
import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject

object Node {

  case class Props(node: N)

  case class State(x: Int, y: Int, v: String)

  class Backend($: BackendScope[Props, State]) extends OnUnmount {

    class Position(val x: Double, val y: Double)

    class Mouse(
                 val clientX: Double,
                 val clientY: Double,
                 val eventType: String
               )

    val mouseDownChannel = ConcurrentSubject[Mouse](MulticastStrategy.publish, DropOld(100))
    val mouseMoveChannel = ConcurrentSubject[Mouse](MulticastStrategy.publish, DropOld(100))

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
      .foreach(m => {
        val s = $.state.runNow()
        $.setState(
          new State(m.clientX.toInt, m.clientY.toInt, s.v)
        ).runNow()
      })

    def handleMouseDown(e: ReactMouseEventH) =
      Callback {
        mouseDownChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.eventType)
        )
      }

    def handleMouseMove(e: dom.MouseEvent) = {
      Callback {
        mouseMoveChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }
    }

    def init:Callback = {
      Callback {
        val p = $.props.runNow()
        p.node.value.foreach(str=>{
          val s = $.state.runNow()
          $.setState(new State(s.x, s.y, str)).runNow()
        })
      }
    }

    def render(props: Props, state: State) = {
      <.div(
        ^.className := "draggable",
        ^.onMouseDown ==> handleMouseDown,
        ^.left := state.x + "px",
        ^.top := state.y + "px",
        ^.position := "absolute",
        state.v
      )
    }
  }

  val component = ReactComponentB[Props]("DrawNode")
    .initialState[State](State(0, 0, ""))
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
    .componentWillMount(_.backend.init)
    .build

  def apply(P: Props) =
    component.withProps(P)
}
