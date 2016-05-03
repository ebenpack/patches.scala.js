package patches

import monifu.reactive.Observable
import monifu.reactive.channels.PublishChannel
import org.scalajs.dom
import org.scalajs.dom.{Event, MouseEvent, html}
import org.scalajs.dom.html.{Button, Div}
import patches.Draw.DrawNode
import patches.IO._
import patches.Node.Math.{Product, Sum}

import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    import monifu.concurrent.Implicits.globalScheduler
    val p = dom.document.getElementById("playground").asInstanceOf[Div]
    val c = dom.document.createElement("div").asInstanceOf[Div]
    val outp = dom.document.createElement("div").asInstanceOf[Div]
    outp.style.position = "relative"
    p.appendChild(c)
    p.appendChild(outp)


    val n = new Sum()
    n.inputs(0).update(DoubleMessage(0))
    n.inputs(1).update(DoubleMessage(0))
    n.outputs(0).out.foreach(println(_))
    n.outputs(0).out.foreach({
      case m: DoubleMessage => outp.textContent = m.value.toString
    })

    val inp1 = dom.document.createElement("input")
      .asInstanceOf[dom.html.Input]
    val inp2 = dom.document.createElement("input")
      .asInstanceOf[dom.html.Input]
    inp1.value = "0"
    inp2.value = "0"

    inp1.onkeyup = (e: Event) =>
      n.inputs(0).update(DoubleMessage(inp1.value.toDouble))
    
    inp2.onkeyup = (e: Event) =>
      n.inputs(1).update(DoubleMessage(inp2.value.toDouble))

    c.appendChild(inp1)
    c.appendChild(inp2)

//
//    n.on("update", i=>{
//      outp.innerHTML = ""
//      outp.appendChild(DrawNode(n, 0, 0))
//      outp.appendChild(DrawNode(prod, 30, 40))
//    })
//    prod.on("update", i=>{
//      outp.innerHTML = ""
//      outp.appendChild(DrawNode(n, 0, 0))
//      outp.appendChild(DrawNode(prod, 30, 40))
//    })
//    outp.appendChild(DrawNode(n, 0, 0))
//    outp.appendChild(DrawNode(prod, 30, 40))
//

  }
}