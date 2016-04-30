package patches

import org.scalajs.dom
import org.scalajs.dom.{MouseEvent, html}
import org.scalajs.dom.html.{Button, Div}
import org.scalajs.dom.raw.AudioContext
import patches.Draw.DrawNode
import patches.Node.{AudioDestinationNode, Oscillator}

import scala.reflect.ClassTag
import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val container = dom.document.getElementById("playground").asInstanceOf[Div]
    container.style.position = "relative"
    container.style.boxSizing = "border-box"
    val ctx = new AudioContext()
    val osc = Oscillator(ctx)
    val dest = AudioDestinationNode(ctx)
    osc.outputs(0).connect(dest.inputs(0))


    def draw(): Unit = {
//      container.innerHTML = ""
//      for (n <- nodes) {
//        val child = new DrawNode(n).draw()
//        child.appendChild(
//          dom.document.createTextNode(
//            n.intValue.toString + " | " + n.strValue
//          )
//        )
//        container.appendChild(child)
//      }
//      dom.setTimeout(() => draw(), 8000)
    }
    draw()
    var but = dom.document.createElement("button").asInstanceOf[Button]
    but.textContent = "START"
    but.style.position = "absolute"
    but.style.left = "0px"
    but.style.top = "300px"
    but.onclick = (e:MouseEvent)=>osc.outputs(3)

    container.parentElement.appendChild(but)
    var but1 = dom.document.createElement("button").asInstanceOf[Button]
    but1.textContent = "STOP"
    but1.style.position = "absolute"
    but1.style.left = "0px"
    but1.style.top = "350px"

    container.parentElement.appendChild(but1)
  }
}