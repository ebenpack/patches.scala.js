package patches

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import org.scalajs.dom.{Event, MouseEvent}
import patches.Draw.Node.{Props, State}
import patches.IO._
import patches.Node.Math.{Product, Sum}

import scala.scalajs.js
import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.{BehaviorSubject, ConcurrentSubject}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {

    val n = Sum()
    val prod = Product()

    n.inputs(1).update(DoubleMessage(3))
    prod.connect(n.inputs(0), prod.outputs(0))
    prod.inputs(1).update(DoubleMessage(5))

    var g = 6
    dom.window.setTimeout(
      () => dom.window.setInterval(() => {
        prod.inputs(0).update(DoubleMessage(g))
        g = g + 1
      }, 2000)
      , 1000)

    var h = 8
    dom.window.setInterval(() => {
      prod.inputs(1).update(DoubleMessage(h))
      h = h + 1
    }, 2000)


    val node1 = patches.Draw.Canvas(
      patches.Draw.Canvas.Props(
        List(
          prod,
          n
        )
      )
    )
    ReactDOM.render(
      node1(),
      dom.document.getElementById("playground")
    )


  }
}