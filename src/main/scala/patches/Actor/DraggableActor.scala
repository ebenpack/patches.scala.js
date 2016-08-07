package patches.Actor

import org.scalajs.dom
import org.scalajs.dom._
import patches.Messages._

import scala.scalajs.js

trait DraggableActor extends DomActor {
  var prevX: Option[Double] = None
  var prevY: Option[Double] = None
  var dragging = false
  var x = 0.0
  var y = 0.0

  protected def onMouseDown(e: MouseEvent) {
    prevX = Some(e.clientX)
    prevY = Some(e.clientY)
    dragging = true
  }

  private val onMouseUp: js.Function1[MouseEvent, Unit] =
    (e: MouseEvent) => {
      println("UPS!")
      prevX = None
      prevY = None
      dragging = false
    }

  private val onMouseMove: js.Function1[MouseEvent, Unit] =
    (e: MouseEvent) => {
      if (dragging) {
        self ! Move(
          x + e.clientX - prevX.getOrElse(0.0),
          y + e.clientY - prevY.getOrElse(0.0)
        )
        prevX = Some(e.clientX)
        prevY = Some(e.clientY)
      }
    }

  dom.document.addEventListener("mouseup", onMouseUp)
  dom.document.addEventListener("mousemove", onMouseMove)

  override def postStop() = {
    super.postStop()
    dom.document.removeEventListener("mouseup", onMouseUp)
    dom.document.removeEventListener("mousemove", onMouseMove)
  }
}