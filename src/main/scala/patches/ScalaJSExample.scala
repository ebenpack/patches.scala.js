package patches

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.{Event, MouseEvent, html}
import org.scalajs.dom.html.{Button, Div}
import org.scalajs.dom.raw.AudioContext
import patches.Draw.DrawNode
import patches.Node._
import patches.IO._
import patches.Node.Math.Add

import scala.reflect.ClassTag
import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {

    val p = dom.document.getElementById("playground").asInstanceOf[Div]
    val c = dom.document.createElement("div").asInstanceOf[Div]
    val outp = dom.document.createElement("div").asInstanceOf[Div]
    p.appendChild(c)
    p.appendChild(outp)
    val a1 = dom.document.createElement("div").asInstanceOf[Div]
    c.appendChild(a1)

    val n = Add()
    val right = IntOutput("one")
    val left = DoubleOutput("one")
    val sum = DoubleInput("one")

    n.connect(n.leftInput, left, "update")
    n.connect(n.rightInput, right, "update")
    n.connect(sum, n.outputs(0), "update")
    n.on("update", i=>{
      outp.innerHTML = ""
      outp.appendChild(DrawNode(n, 0, 0))
    })


    sum.on("update", m => m match {
      case msg: DoubleMessage => a1.textContent = msg.value.toString
      case msg: IntMessage => a1.textContent = msg.value.toString
      case _ => ;
    })

    val inp1 = dom.document.createElement("input").asInstanceOf[dom.html.Input]
    val inp2 = dom.document.createElement("input").asInstanceOf[dom.html.Input]

    inp1.onkeyup = (e: Event) => {
      left.send(IntMessage("update", inp1.value.toInt))
    }

    inp2.onkeyup = (e: Event) => {
      right.send(IntMessage("update", inp2.value.toInt))
    }
    c.appendChild(inp1)
    c.appendChild(inp2)
  }
}