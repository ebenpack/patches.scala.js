package patches.Draw

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import dom.MouseEvent

object Canvas {


  class Backend($: BackendScope[ModelProxy[CanvasModel], Unit]) extends OnUnmount with Draggable {

    def getXY() = {
      val p = $.props.runNow()
      val dragging = p().patches.dragging
      if (dragging.isDefined) (dragging.get.x2, dragging.get.y2)
      else (0.0, 0.0)
    }

    def onMove(x: Double, y: Double) = {
      val p = $.props.runNow()
      val dragging = p().patches.dragging
      dragging.foreach(d => {
        p.dispatch(MoveTempPatch(x.toInt, y.toInt)).runNow()
      })
    }

    def handleMouseDown(e: ReactMouseEventH) = {
      val p = $.props.runNow()
      Callback {
        mouseDownChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.eventType)
        )
      }
    }

    def handleMouseMove(e: dom.MouseEvent) =
      Callback {
        mouseMoveChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }

    def handleMouseUp(e: dom.MouseEvent) = {
      val p = $.props.runNow()
      Callback {
        p.dispatch(RemoveTempPatch()).runNow()
        mouseMoveChannel.onNext(
          new Mouse(e.clientX, e.clientY, e.`type`)
        )
      }
    }

    def handleMouseU(e: dom.MouseEvent) = {
      val p = $.props.runNow()
      Callback {}
    }

    def render(proxy: ModelProxy[CanvasModel]) = {
      val dispatch = proxy.dispatch
      <.div(
        ^.onMouseDown ==> handleMouseDown,
        ^.onMouseUp ==> handleMouseU,
        ^.className := "canvas",
        proxy().nodes.map(n => patches.Draw.Node(
          dispatch,
          n
        )),
        (proxy().patches.dragging ++ proxy().patches.patches).map(p => patches.Draw.Patch(
          dispatch,
          p
        )),
        Controls(dispatch, proxy().controls)
      )
    }
  }


  val canvas = ReactComponentB[ModelProxy[CanvasModel]]("Canvas")
    .renderBackend[Backend]
    .configure(
      // Listen to window mousemove/mouseup events within the component
      EventListener[dom.MouseEvent].install(
        "mouseup",
        _.backend.handleMouseUp,
        _ => dom.window
      ),
      EventListener[MouseEvent].install(
        "mousemove",
        _.backend.handleMouseMove,
        _ => dom.window
      )
    )
    .build

  def apply(p: ModelProxy[CanvasModel]) =
    canvas(p)
}
