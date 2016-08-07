package patches.Canvas

import akka.actor.{ActorRef, Props}
import org.scalajs.dom.document.getElementById
import patches.Actor.DomMsgs.Remove
import patches.Messages.{Reorder, _}
import patches.Actor.DomActor

import scalatags.JsDom.all._

class Canvas(hook: String) extends DomActor {
  var nodes = List[ActorRef]()
  context.actorOf(Props(Controls()))
  override val domElement = Some(getElementById(hook))

  def template = div(
    `class` := "canvas"
  )

  def removeNode: Receive = {
    case Remove(child) =>
      thisNode.removeChild(child)
      nodes = nodes.filterNot(_ == sender())
  }

  override def operative = removeNode orElse domManagement orElse {
    case Reorder =>
      val (l, r) = nodes.partition(_.equals(sender()))
      nodes = r ++ l
      nodes.zipWithIndex.foreach({
        case (child, index) => child ! Reorder(index)
      })
    case AddNode(n) =>
      nodes = context.actorOf(n) :: nodes
      self ! Reorder
  }


}

object Canvas {
  def apply(hook: String): Canvas = new Canvas(hook)
}