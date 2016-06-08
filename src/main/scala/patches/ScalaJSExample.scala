package patches

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import scala.scalajs.js

import patches.Draw.{AppCircuit, Canvas}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {

    val p = AppCircuit.connect(a=>a)(p=>Canvas(p))
    ReactDOM.render(
      p,
      dom.document.getElementById("playground")
    )
  }
}