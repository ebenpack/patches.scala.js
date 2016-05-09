package patches

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import org.scalajs.dom.{Event, MouseEvent}
import patches.Draw.Node.{Props, State}
import patches.IO._
import patches.Node.Math.{Product, Sum}

import scala.scalajs.js
import scala.util.Try

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    import monifu.concurrent.Implicits.globalScheduler

    val n = Sum()
    val prod = Product()

    n.inputs(0).update(DoubleMessage(3))
    prod.connect(prod.inputs(0), n.outputs(0))
    prod.inputs(1).update(DoubleMessage(5))
    prod.outputs(0).out.foreach({
      case m: DoubleMessage => {}
      case _ => {}
    })

    val inp1 = dom.document.createElement("input")
      .asInstanceOf[dom.html.Input]
    val inp2 = dom.document.createElement("input")
      .asInstanceOf[dom.html.Input]
    inp1.value = "0"
    inp2.value = "0"

    inp1.onkeyup = (e: Event) =>
      Try(DoubleMessage(inp1.value.toDouble))
        .map(n.inputs(0).update(_))

    inp2.onkeyup = (e: Event) =>
      Try(DoubleMessage(inp1.value.toDouble))
        .map(n.inputs(0).update(_))

    val node1 = patches.Draw.Node(Props(prod))
    ReactDOM.render(
      node1(),
      dom.document.getElementById("playground")
    )

  }
}