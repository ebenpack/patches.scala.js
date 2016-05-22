package patches

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import org.scalajs.dom.{Event, MouseEvent}
import patches.Draw.Node.{Props}
import patches.IO._
import patches.Node.Math.{Product, Sum}

import scala.scalajs.js
import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.{BehaviorSubject, ConcurrentSubject}
import patches.Draw.{AppCircuit, Canvas, CanvasModel}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {

    val p = AppCircuit.connect(a=>a)(p=>Canvas(p))
    ReactDOM.render(
      p,
      dom.document.getElementById("playground")
    )


  }
}