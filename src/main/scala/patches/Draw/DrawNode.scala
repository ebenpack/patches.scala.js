package patches.Draw

import patches.Node.TestNode
import org.scalajs.dom
import org.scalajs.dom.html.Div

class DrawNode(n: TestNode) {
  def draw() = {
    val d = dom.document.createElement("div")
      .asInstanceOf[Div]
    d.style.position = "absolute"
    d.style.width = "100px"
    d.style.height = "100px"
    d.style.top = n.y + "px"
    d.style.left = n.x + "px"
    d.style.border = "1px solid black"
    def drawHelper(x: Int, y: Int) = {
      val n = dom.document.createElement("div")
        .asInstanceOf[Div]
      n.style.backgroundColor = "red"
      n.style.position = "absolute"
      n.style.top = y + "px"
      n.style.left = x + "px"
      n.style.width = "10px"
      n.style.height = "10px"
      n.style.border = "1px solid red"
      n
    }
    n.inputs.zipWithIndex.foreach(i => {
      val y = (n.height / (n.inputs.length + 1)) * (i._2 + 1)
      d.appendChild(drawHelper(0, y))
    })
    n.outputs.zipWithIndex.foreach(i => {
      val y = (n.height / (n.inputs.length + 1)) * (i._2 + 1)
      d.appendChild(drawHelper(n.width-10, y))
    })
    d
  }
}