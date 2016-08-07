package patches.Actor

import org.scalajs.dom
import org.scalajs.dom._
import patches.Messages._

trait DraggableActor extends DomActor {
  var prevX: Option[Double] = None
  var prevY: Option[Double] = None
  var dragging = false
  var x = 100.0
  var y = 100.0

  def onMouseDown(e: MouseEvent) {
      prevX = Some(e.clientX)
      prevY = Some(e.clientY)
      dragging = true
    }

  dom.document.addEventListener("mouseup",
    (e: MouseEvent) => {
      prevX = None
      prevY = None
      dragging = false
    })
  dom.document.addEventListener("mousemove",
    (e: MouseEvent) => {
      if (dragging) {
        self ! Move(
          x + e.clientX - prevX.getOrElse(0.0),
          y + e.clientY - prevY.getOrElse(0.0)
        )
        prevX = Some(e.clientX)
        prevY = Some(e.clientY)
      }
    })
}