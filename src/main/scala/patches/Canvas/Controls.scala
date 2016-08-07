package patches.Canvas

import org.scalajs.dom.MouseEvent
import patches.Messages.AddNode
import patches.Actor.DomActor
import patches.Node.Node
import patches.Node.Node.Node

import scalatags.JsDom.all._


class Controls extends DomActor {
  def template = div(
    `class` := "controls",
    button(
      "Add",
      onclick := {
        (e: MouseEvent) =>
          this.context.parent ! AddNode(Node.props[Int](1, "Foo"))
      }
    )
  )
}

object Controls {
  def apply(): Controls = new Controls()
}
