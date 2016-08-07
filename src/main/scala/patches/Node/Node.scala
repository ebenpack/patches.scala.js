package patches

import org.scalajs.dom._
import patches.Actor.DraggableActor
import patches.Messages._

import scalatags.JsDom.all._
import scalatags.JsDom.styles2._

class Node[T](var value: T) extends DraggableActor {
  var order = 0
  def template = div(
    value.toString,
    onmousedown := {
      (e: MouseEvent) =>
        onMouseDown(e)
        context.parent ! Reorder
    },
    `class` := "handle node",
    zIndex := order,
    transform := s"translate(${x}px, ${y}px)"
  )

  override def operative = domManagement orElse {
    case Move(x1, y1) =>
      x = x1
      y = y1
      self ! Update
    case Reorder(n) =>
      order = n
      self ! Update
  }
}

object Node {
  def apply[T](value: T): Node[T] = new Node[T](value)
}
