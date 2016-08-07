package patches

import org.scalajs.dom._
import patches.Actor.DraggableActor
import patches.Messages._

import scalatags.JsDom.all._
import scalatags.JsDom.styles2.transform

class Node[T](var value: T, title: String) extends DraggableActor {
  var order = 0

  def template = div(
    div(
      div(
        "✕",
        `class` := "close",
        onmousedown := {
          (e: MouseEvent) =>
            context.stop(self)
        }
      ),
      title,
      `class` := "handle title",
      onmousedown := {
        (e: MouseEvent) =>
          onMouseDown(e)
          context.parent ! Reorder
      }
    ),
    `class` := "node",
    zIndex := order,
    transform := s"translate(${x}px, ${y}px)",
    div(
      value.toString,
      `class` := "body"
    )

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
  def apply[T](value: T, title: String): Node[T] = new Node[T](value, title)
}
