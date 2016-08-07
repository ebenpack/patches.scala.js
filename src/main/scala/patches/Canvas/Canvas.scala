package patches

import akka.actor.Props
import patches.Actor.DomActor
import org.scalajs.dom.document.getElementById
import patches.Messages._

import scalatags.JsDom.all._

class Canvas(hook: String) extends DomActor {
  var childs = List(
    context.actorOf(Props(Node(1, "Sum"))),
    context.actorOf(Props(Node(2, "Sum")))
  )
  context.actorOf(Props(Controls()))
  override val domElement = Some(getElementById(hook))

  def template = div(
    `class` := "canvas"
  )

  override def operative = domManagement orElse {
    case Reorder =>
      val (l, r) = childs.partition(_.equals(sender()))
      childs = r ++ l
      childs.zipWithIndex.foreach({
        case (child, index) => child ! Reorder(index)
      })
  }
}

object Canvas {
  def apply(hook: String): Canvas = new Canvas(hook)
}