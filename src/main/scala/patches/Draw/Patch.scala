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
import patches.IO.{Input, Message}


object Patch {
  case class State(x1: Int, y1: Int, x2: Int, y2: Int)

  class Backend($: BackendScope[Unit, State]) extends OnUnmount {

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
        val startPosX = s.x1.toDouble
        val startPosY = s.y1.toDouble
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
        $.modState(
          _.copy(x1 = m.clientX.toInt, y1 = m.clientY.toInt)
        ).runNow()
      )

    def handleMouseDown(e: ReactMouseEventH) = {
      Callback {
        mouseDownChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.eventType)
        )
      }
    }

    def handleMouseMove(e: dom.MouseEvent) = {
      Callback {
        mouseMoveChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }
    }

    def drawDiagonal(x1: Int, y1: Int, x2: Int, y2: Int) = {
      "rotate(10deg)"
    }

    def render(s: State) = {
      <.hr(
        ^.onMouseDown ==> handleMouseDown,
        ^.transform := drawDiagonal(s.x1,s.y1,s.x2,s.y2)
      )
    }
  }

  val component = ReactComponentB[Unit]("DrawPatch")
    .initialState[State](State(0, 0, 0,0))
    .renderBackend[Backend]
    .build

  def apply() =
    component
}
