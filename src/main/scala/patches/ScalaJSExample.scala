package patches


import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom

import scala.scalajs.js
import jsactor.logging.impl.JsNullActorLoggerFactory
import jsactor.{JsActor, JsActorSystem, JsProps}
import org.scalajs.dom
import patches.Draw.{AppCircuit, Canvas => DrawCanvas}
import patches.Canvas.{Canvas => C}
import patches.IO.Messages._
import patches.Node.Math.Sum

import scala.scalajs.js

object ScalaJSExample extends js.JSApp {

  case class Mess(int: String)

  class Listener() extends JsActor {
    def receive = {
      case n: AddNode => println("listnmess", n)
      case n: MoveNode => println("listndouble", n)
    }
  }

  object Listener {
    def apply(): Listener = new Listener()
  }

  def main(): Unit = {
    val d = dom.document.getElementById("playground")
    val c = new C
    val g = Sum.props()

    val p = AppCircuit.connect(a => a)(p => DrawCanvas(p))
    ReactDOM.render(
      p,
      dom.document.getElementById("playground")
    )
  }
}
