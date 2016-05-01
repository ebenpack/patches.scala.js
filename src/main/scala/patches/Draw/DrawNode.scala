package patches.Draw

import org.scalajs.dom
import org.scalajs.dom.html.Div
import patches.Node.Node

object DrawNode {
  def apply(node: Node, x: Int, y: Int) = {
    val d = dom.document.createElement("div")
      .asInstanceOf[Div]
    d.classList.add("node")
    val text = dom.document.createTextNode(node.name)
    val value = dom.document.createTextNode(node.value)
    d.appendChild(text)
    d.appendChild(value)
    d
  }
}